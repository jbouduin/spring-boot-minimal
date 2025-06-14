param (
    [Alias("u")]
    [Parameter(Mandatory = $true)]
    [string]$Username,

    [Alias("p")]
    [Parameter(Mandatory = $true)]
    [string]$Password
)

###############################################################################
# Step 1: login
###############################################################################
$loginUri = "http://localhost:5401/api/login"
$loginBody = @{
    user = $Username
    password = $Password
} | ConvertTo-Json
$loginHeaders = @{
    "Content-Type" = "application/json"
}

# Make the API call
$loginResponse = Invoke-RestMethod -Uri $loginUri -Method Post -Headers $loginHeaders -Body $loginBody

# Extract the token
$token = $loginResponse.data.token

# Save the token to a file
$token | Out-File -FilePath "token.txt" -Encoding utf8


###############################################################################
# Step 2: Create items
###############################################################################
$itemUri = "http://localhost:5402/api/item"
$authHeaders = @{
    "Content-Type"  = "application/json"
    "accept"  = "application/json"
    "Authorization" = "Bearer $token"
}

$itemIds = @()
Get-ChildItem -Path ./items -Filter *.json | ForEach-Object {
    $file = $_.FullName
    Write-Host "Sending file: $file"

    $bodyContent = Get-Content -Path $file -Raw

    try {
        $itemResponse = Invoke-RestMethod -Uri $itemUri -Method Post -Headers $authHeaders -Body $bodyContent
        $itemId = $itemResponse.data.id        
        Write-Host "Success: created item with id $($itemId)"
        $itemIds += $itemId
    } catch {
        Write-Host "Error sending ${file}:`n$($_.Exception.Message)"
    }
}

###############################################################################
# Step 3: Create invoices
###############################################################################
$invoiceUri = "http://localhost:5402/api/invoice"
$invoiceIds = @()
Get-ChildItem -Path ./invoices -Filter *.json | ForEach-Object {
    $file = $_.FullName
    Write-Host "Sending file: $file"

    $bodyContent = Get-Content -Path $file -Raw

    try {
        $invoiceResponse = Invoke-RestMethod -Uri $invoiceUri -Method Post -Headers $authHeaders -Body $bodyContent
        $invoiceId = $invoiceResponse.data.id        
        Write-Host "Success: created invoice with id $($invoiceId)"
        $invoiceIds += $invoiceId
    } catch {
        Write-Host "Error sending ${file}:`n$($_.Exception.Message)"
    }
}

###############################################################################
# Step 4: create invoice lines
###############################################################################
foreach ($invoiceId in $invoiceIds) {
    foreach ($itemId in $itemIds) {
        $invoiceLineUri = "http://localhost:5402/api/invoice/$invoiceId/invoice-line"

        $body = @{
            id        = $null
            quantity  = 1
            invoiceId = $invoiceId
            itemId    = $itemId
        } | ConvertTo-Json -Depth 3

        try {
            $invoiceLineResponse = Invoke-RestMethod -Uri $invoiceLineUri -Method Post -Headers $authHeaders -Body $body
            Write-Host "Posted item $itemId to invoice ${invoiceId}: Success"
        } catch {
            Write-Host "Failed to post item $itemId to invoice ${invoiceId}:`n$($_.Exception.Message)"
        }
    }
}
