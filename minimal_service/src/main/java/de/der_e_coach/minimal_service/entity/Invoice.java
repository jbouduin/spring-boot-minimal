package de.der_e_coach.minimal_service.entity;

import java.util.Set;

import de.der_e_coach.minimal_service.dto.InvoiceDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Invoice {
  //#region Entity field - id -------------------------------------------------
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  //#endregion

  //#region Entity field - invoiceNumber --------------------------------------
  @NotNull(message = "Invoice number must have a value")
  @NotEmpty(message = "Invoice number must have a value")
  @Column(name = "invoice_number")
  private String invoiceNumber;
  //#endregion

  //#region Entity field - customerName ---------------------------------------
  @NotNull(message = "Customer name must have a value")
  @NotEmpty(message = "Customer name must have a value")
  @Column(name = "customer_name")
  private String customerName;
  //#endregion

  //#region Child entities - invoice lines ------------------------------------
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "invoice_id")
  private Set<InvoiceLine> invoiceLines;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public Invoice() {
  }

  public Invoice(InvoiceDto invoice) {
    this.id = invoice.getId();
    this.invoiceNumber = invoice.getInvoiceNumber();
    this.customerName = invoice.getCustomerName();
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public Long getId() {
    return id;
  }

  public Invoice setId(Long id) {
    this.id = id;
    return this;
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public Invoice setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
    return this;
  }

  public String getCustomerName() {
    return customerName;
  }

  public Invoice setCustomerName(String customerName) {
    this.customerName = customerName;
    return this;
  }

  public Set<InvoiceLine> getInvoiceLines() {
    return invoiceLines;
  }

  public Invoice setInvoiceLines(Set<InvoiceLine> invoiceLines) {
    this.invoiceLines = invoiceLines;
    return this;
  }
  //#endregion
}
