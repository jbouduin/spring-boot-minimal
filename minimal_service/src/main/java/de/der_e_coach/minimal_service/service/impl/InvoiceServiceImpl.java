package de.der_e_coach.minimal_service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.der_e_coach.minimal_service.dto.InvoiceDto;
import de.der_e_coach.minimal_service.entity.Invoice;
import de.der_e_coach.minimal_service.repository.InvoiceRepository;
import de.der_e_coach.minimal_service.service.InvoiceService;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.result.ValidationError;
import jakarta.transaction.Transactional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
  //#region Private fields ----------------------------------------------------
  private final InvoiceRepository invoiceRepository;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }
  //#endregion

  //#region InvoiceService Members --------------------------------------------
  @Override
  public ResultDto<List<InvoiceDto>> getInvoices() {
    return ResultDto
      .success(invoiceRepository.findAll().stream().map((Invoice i) -> new InvoiceDto(i)).collect(Collectors.toList()));
  }

  @Override
  @Transactional
  public ResultDto<InvoiceDto> createInvoice(InvoiceDto invoice) {
    ResultDto<InvoiceDto> result;
    List<ValidationError> validationErrors = new ArrayList<ValidationError>();

    if (invoice.getId() != null) {
      validationErrors
        .add(new ValidationError(this.getClass(), "createInvoice", "001", "Invoice Id may not have a value"));
    }

    if (invoice.getCustomerName() == null || invoice.getCustomerName().isBlank()) {
      validationErrors
        .add(new ValidationError(this.getClass(), "createInvoice", "002", "Customer name must have a value"));
    }

    if (invoice.getInvoiceNumber() == null || invoice.getInvoiceNumber().isBlank()) {
      validationErrors
        .add(new ValidationError(this.getClass(), "createInvoice", "002", "Invoice number must have a value"));
    }

    if (!validationErrors.isEmpty()) {
      result = ResultDto.invalid(validationErrors);
    } else {
      result = ResultDto.success(new InvoiceDto(invoiceRepository.save(new Invoice(invoice))));
    }
    return result;
  }

  @Override
  public ResultDto<InvoiceDto> updateInvoice(Long invoiceId, InvoiceDto invoice) {
    ResultDto<InvoiceDto> result;
    List<ValidationError> validationErrors = new ArrayList<ValidationError>();

    if (!invoice.getId().equals(invoiceId)) {
      validationErrors
        .add(new ValidationError(this.getClass(), "updateInvoice", "001", "Invoice Id in URL and body do not match"));
    }

    if (invoice.getCustomerName() == null || invoice.getCustomerName().isBlank()) {
      validationErrors
        .add(new ValidationError(this.getClass(), "updateInvoice", "002", "Customer name must have a value"));
    }

    if (invoice.getInvoiceNumber() == null || invoice.getInvoiceNumber().isBlank()) {
      validationErrors
        .add(new ValidationError(this.getClass(), "updateInvoice", "003", "Invoice number must have a value"));
    }

    if (!validationErrors.isEmpty()) {
      result = ResultDto.invalid(validationErrors);
    } else {
      result = invoiceRepository
        .findById(invoiceId)
        .map(
          (Invoice i) -> ResultDto
            .success(
              new InvoiceDto(
                invoiceRepository
                  .save(
                    i
                      .setId(invoiceId)
                      .setCustomerName(invoice.getCustomerName())
                      .setInvoiceNumber(invoice.getInvoiceNumber())
                  )
              )
            )
        )
        .orElse(ResultDto.notFound("Invoice with Id '" + invoiceId + "' not found."));
    }
    return result;
  }
  //#endregion
}
