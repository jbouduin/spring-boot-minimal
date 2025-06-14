package de.der_e_coach.minimal_service.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.der_e_coach.minimal_service.dto.InvoiceLineDto;
import de.der_e_coach.minimal_service.service.InvoiceLineService;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Invoice Line")
@RequestMapping(path = "invoice/{invoice-id}/invoice-line")
public class InvoiceLineController {
  //#region Private fields ----------------------------------------------------  
  private final InvoiceLineService invoiceLineService;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public InvoiceLineController(InvoiceLineService invoiceLineService) {
    this.invoiceLineService = invoiceLineService;
  }
  //#endregion

  //#region Get ---------------------------------------------------------------
  @Operation(summary = "Get invoice lines", description = "Get all invoice lines for an invoice.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid data", useReturnTypeSchema = false),
        @ApiResponse(responseCode = "401", description = "Not authenticated"),
        @ApiResponse(responseCode = "404", description = "Invoice not found")
      }
  )
  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResultDto<List<InvoiceLineDto>>> getInvoiceLines(@PathVariable("invoice-id") Long invoiceId) {
    ResultDto<List<InvoiceLineDto>> result = invoiceLineService.getInvoiceLines(invoiceId);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion

  //#region Post --------------------------------------------------------------
  @Operation(summary = "Create an invoice line", description = "Create an invoice line for the given invoice.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid data", useReturnTypeSchema = false),
        @ApiResponse(responseCode = "401", description = "Not authenticated"),
        @ApiResponse(responseCode = "404", description = "Invoice not found")
      }
  )
  @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResultDto<InvoiceLineDto>>
    createInvoiceLine(@PathVariable("invoice-id") Long invoiceId, @RequestBody InvoiceLineDto item) {
    ResultDto<InvoiceLineDto> result = invoiceLineService.createInvoiceLine(invoiceId, item);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion
}
