package de.der_e_coach.minimal_service.dto;

import de.der_e_coach.minimal_service.entity.Invoice;

public class InvoiceDto {
  //#region Private fields ----------------------------------------------------
  private Long id;
  private String invoiceNumber;
  private String customerName;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public InvoiceDto() {
  }

  public InvoiceDto(Invoice invoice) {
    this.id = invoice.getId();
    this.invoiceNumber = invoice.getInvoiceNumber();
    this.customerName = invoice.getCustomerName();
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public Long getId() {
    return id;
  }

  public InvoiceDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public InvoiceDto setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
    return this;
  }

  public String getCustomerName() {
    return customerName;
  }

  public InvoiceDto setCustomerName(String customerName) {
    this.customerName = customerName;
    return this;
  }
  //#endregion
}
