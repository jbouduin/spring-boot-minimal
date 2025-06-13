package de.der_e_coach.minimal_service.service;

import java.util.List;

import de.der_e_coach.minimal_service.dto.InvoiceDto;
import de.der_e_coach.shared_lib.dto.result.ResultDto;

public interface InvoiceService {
  /**
   * Get all invoices.
   * 
   * @return {@link ResultDto>} containing a list of {@link InvoiceDto}
   */
  ResultDto<List<InvoiceDto>> getInvoices();
  /**
   * Create an invoice.
   * 
   * @param  invoice {@link InvoiceDto}
   * @return         {@link ResultDto} containing the created {@link InvoiceDto}
   */
  ResultDto<InvoiceDto> createInvoice(InvoiceDto invoice);
  ResultDto<InvoiceDto> updateInvoice(Long invoiceId, InvoiceDto invoice);
}
