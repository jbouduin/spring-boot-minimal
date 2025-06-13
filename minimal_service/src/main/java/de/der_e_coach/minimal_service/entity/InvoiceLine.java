package de.der_e_coach.minimal_service.entity;

import java.math.BigDecimal;

import de.der_e_coach.minimal_service.dto.InvoiceLineDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class InvoiceLine {
  //#region Entity field - id -------------------------------------------------
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  //#endregion

  //#region Entity field - Quantity -------------------------------------------
  @Column(name = "quantity")
  @NotNull(message = "Quantity must have a value")
  @Min(value = 0, message = "Quantity must be greater than or equal to zero.")
  BigDecimal quantity;
  //#endregion

  //#region Foreign key field - invoice ---------------------------------------
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "invoice_id", nullable = false)
  private Invoice invoice;
  //#endregion

  //#region Foreign key field - item ------------------------------------------
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "item_id", nullable = false)
  private Item item;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public InvoiceLine() {
  }

  public InvoiceLine(InvoiceLineDto invoiceLine) {
    this.id = invoiceLine.getId();
    this.quantity = invoiceLine.getQuantity();
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public Long getId() {
    return id;
  }

  public InvoiceLine setId(Long id) {
    this.id = id;
    return this;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public InvoiceLine setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }

  public Invoice getInvoice() {
    return invoice;
  }

  public InvoiceLine setInvoice(Invoice invoice) {
    this.invoice = invoice;
    return this;
  }

  public Item getItem() {
    return item;
  }

  public InvoiceLine setItem(Item item) {
    this.item = item;
    return this;
  }
  //#endregion  
}
