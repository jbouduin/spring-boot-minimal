package de.der_e_coach.minimal_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.der_e_coach.minimal_service.dto.InvoiceLineDto;
import de.der_e_coach.minimal_service.entity.Invoice;
import de.der_e_coach.minimal_service.entity.InvoiceLine;
import de.der_e_coach.minimal_service.entity.Item;
import de.der_e_coach.minimal_service.repository.InvoiceLineRepository;
import de.der_e_coach.minimal_service.repository.InvoiceRepository;
import de.der_e_coach.minimal_service.repository.ItemRepository;
import de.der_e_coach.minimal_service.service.InvoiceLineService;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.result.ValidationError;

@Service
public class InvoiceLineServiceImpl implements InvoiceLineService {
  //#region Private fields ----------------------------------------------------
  private final InvoiceRepository invoiceRepository;
  private final InvoiceLineRepository invoiceLineRepository;
  private final ItemRepository itemRepository;
  //#endregion

  //#region Constructor -------------------------------------------------------
  @Autowired
  public InvoiceLineServiceImpl(
    final InvoiceRepository invoiceRepository,
    final InvoiceLineRepository invoiceLineRepository,
    final ItemRepository itemRepository
  ) {
    this.invoiceRepository = invoiceRepository;
    this.invoiceLineRepository = invoiceLineRepository;
    this.itemRepository = itemRepository;
  }
  //#endregion

  //#region InvoiceLineService Members ----------------------------------------
  @Override
  public ResultDto<List<InvoiceLineDto>> getInvoiceLines(final Long invoiceId) {
    return invoiceRepository
      .findById(invoiceId)
      .map(
        (final Invoice i) -> ResultDto
          .success(
            i
              .getInvoiceLines()
              .stream()
              .map((final InvoiceLine il) -> new InvoiceLineDto(il))
              .collect(Collectors.toList())
          )
      )
      .orElse(ResultDto.notFound("Invoice with Id '" + invoiceId + "' not found."));
  }

  @Override
  public ResultDto<InvoiceLineDto> createInvoiceLine(final Long invoiceId, final InvoiceLineDto invoiceLine) {
    return invoiceRepository
      .findById(invoiceId)
      .map((final Invoice i) -> {
        return itemRepository
          .findById(invoiceLine.getItemId())
          .map((final Item it) -> {
            return ResultDto
              .success(
                new InvoiceLineDto(invoiceLineRepository.save(new InvoiceLine(invoiceLine).setInvoice(i).setItem(it)))
              );
          })
          .orElse(
            ResultDto
              .invalid(
                List
                  .of(
                    new ValidationError(
                      this.getClass(), "createInvoideLine", "001",
                      "Item with Id '" + invoiceLine.getItemId() + "' not found."
                    )
                  )
              )
          );
      })
      .orElse(ResultDto.notFound("Invoice with Id '" + invoiceId + "' not found."));
  }
  //#endregion
}
