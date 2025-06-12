package de.der_e_coach.minimal_service.controller;

import org.springframework.web.bind.annotation.RestController;

import de.der_e_coach.minimal_service.service.InvoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Invoice")
public class InvoiceController {
  //#region Private fields ----------------------------------------------------
  @SuppressWarnings("unused")
  private final InvoiceService invoiceService;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }
  //#endregion
}
