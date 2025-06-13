package de.der_e_coach.minimal_service.dto;

import java.math.BigDecimal;

import de.der_e_coach.minimal_service.entity.InvoiceLine;

public class InvoiceLineDto {
  //#region Private fields ----------------------------------------------------
  private Long id;
  private BigDecimal quantity;
  private Long invoiceId;
  private Long itemId;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public InvoiceLineDto() {
  }

  public InvoiceLineDto(InvoiceLine invoiceLine) {
    this.id = invoiceLine.getId();
    this.quantity = invoiceLine.getQuantity();
    this.invoiceId = invoiceLine.getInvoice() != null ? invoiceLine.getInvoice().getId() : null;
    this.itemId = invoiceLine.getItem() != null ? invoiceLine.getItem().getId() : null;
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public Long getId() {
    return id;
  }

  public InvoiceLineDto setId(Long id) {
    this.id = id;
    return this;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public InvoiceLineDto setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }

  public Long getInvoiceId() {
    return invoiceId;
  }

  public InvoiceLineDto setInvoiceId(Long invoiceId) {
    this.invoiceId = invoiceId;
    return this;
  }

  public Long getItemId() {
    return itemId;
  }

  public InvoiceLineDto setItemId(Long itemId) {
    this.itemId = itemId;
    return this;
  }
  //#endregion
}
