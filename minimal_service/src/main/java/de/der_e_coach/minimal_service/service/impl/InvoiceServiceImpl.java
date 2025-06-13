package de.der_e_coach.minimal_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.der_e_coach.minimal_service.dto.InvoiceDto;
import de.der_e_coach.minimal_service.entity.Invoice;
import de.der_e_coach.minimal_service.repository.InvoiceRepository;
import de.der_e_coach.minimal_service.service.InvoiceService;
import de.der_e_coach.shared_lib.dto.result.ResultDto;

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
    return ResultDto.success(invoiceRepository.findAll().stream().map((Invoice i) -> new InvoiceDto(i)).collect(Collectors.toList()));
    
  }

  @Override
  public ResultDto<InvoiceDto> createInvoice(InvoiceDto invoice) {
    return ResultDto.success(new InvoiceDto(invoiceRepository.save(new Invoice(invoice))));
  }
  //#endregion
}
