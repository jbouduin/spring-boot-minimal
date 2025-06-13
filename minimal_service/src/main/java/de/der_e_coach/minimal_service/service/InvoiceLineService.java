package de.der_e_coach.minimal_service.service;

import java.util.List;

import de.der_e_coach.minimal_service.dto.InvoiceLineDto;
import de.der_e_coach.shared_lib.dto.result.ResultDto;

public interface InvoiceLineService {
  /**
   * Get invoice lines for the given invoice.
   * 
   * @param  invoiceId the invoice Id
   * @return           {@link ResultDto} containing list of {@link InvoiceLineDto}
   */
  ResultDto<List<InvoiceLineDto>> getInvoiceLines(Long invoiceId);
  /**
   * Create an invoice line for the given invoice.
   * 
   * @param  invoiceId   the invoice Id
   * @param  invoiceLine {@link InvoiceLineDto}
   * @return             {@link ResultDto} containing the created {@link InvoiceLineDto}
   */
  ResultDto<InvoiceLineDto> createInvoiceLine(Long invoiceId, InvoiceLineDto invoiceLine);
}
