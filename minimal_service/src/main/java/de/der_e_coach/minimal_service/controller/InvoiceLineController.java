package de.der_e_coach.minimal_service.controller;

import org.springframework.web.bind.annotation.RestController;

import de.der_e_coach.minimal_service.service.InvoiceLineService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Invoice Line")
public class InvoiceLineController {
  //#region Private fields ----------------------------------------------------
  @SuppressWarnings("unused")
  private final InvoiceLineService invoiceLineService;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public InvoiceLineController(InvoiceLineService invoiceLineService) {
    this.invoiceLineService = invoiceLineService;
  }
  //#endregion
}
