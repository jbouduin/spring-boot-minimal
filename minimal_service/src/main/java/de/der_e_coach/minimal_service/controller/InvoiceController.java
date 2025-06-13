package de.der_e_coach.minimal_service.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.der_e_coach.minimal_service.dto.InvoiceDto;
import de.der_e_coach.minimal_service.service.InvoiceService;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;

@RestController
@Tag(name = "Invoice")
@RequestMapping("invoice")
public class InvoiceController {
  //#region Private fields ----------------------------------------------------  
  private final InvoiceService invoiceService;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }
  //#endregion

  //#region Get ---------------------------------------------------------------
  @Operation(summary = "Get invoices", description = "Get all invoices.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
      }
  )
  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResultDto<List<InvoiceDto>>> getInvoices() {
    ResultDto<List<InvoiceDto>> result = invoiceService.getInvoices();
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion

  //#region Post - invoice ----------------------------------------------------
  @Operation(summary = "Create invoice", description = "Create an invoice.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
      }
  )
  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResultDto<InvoiceDto>> createInvoice(@RequestBody InvoiceDto invoice) {
    ResultDto<InvoiceDto> result = invoiceService.createInvoice(invoice);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion

  //#region Put - invoice -----------------------------------------------------
  @Operation(summary = "Update invoice", description = "Update an invoice.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "401", description = "Not authenticated"),
        @ApiResponse(responseCode = "404", description = "Invoice not found")
      }
  )
  @PutMapping(
      path = "{invoice-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ResultDto<InvoiceDto>> updateInvoice(
    @PathParam("invoice-id") Long invoiceId,
    @RequestBody InvoiceDto invoice
  ) {
    ResultDto<InvoiceDto> result = invoiceService.updateInvoice(invoiceId, invoice);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion
}
