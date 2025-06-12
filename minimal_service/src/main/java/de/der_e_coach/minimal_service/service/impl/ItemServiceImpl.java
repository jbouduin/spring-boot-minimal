package de.der_e_coach.minimal_service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import de.der_e_coach.minimal_service.dto.ItemDto;
import de.der_e_coach.minimal_service.entity.Item;
import de.der_e_coach.minimal_service.repository.ItemRepository;
import de.der_e_coach.minimal_service.service.ItemService;
import de.der_e_coach.minimal_service.service.feign.translation_service.TranslationServiceClient;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.translation.TranslationDto;
import de.der_e_coach.shared_lib.dto.translation.TranslationRecord;
import jakarta.transaction.Transactional;

@Service
public class ItemServiceImpl implements ItemService {
  //#region Private fields ----------------------------------------------------
  private final ItemRepository itemRepository;
  private final TranslationServiceClient translationServiceClient;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public ItemServiceImpl(ItemRepository itemRepository, TranslationServiceClient translationServiceClient) {
    this.itemRepository = itemRepository;
    this.translationServiceClient = translationServiceClient;
  }
  //#endregion

  //#region ItemService Members -----------------------------------------------
  @Override
  public ResultDto<List<ItemDto>> getItems() {
    ResultDto<List<ItemDto>> result;
    ResultDto<List<TranslationDto>> translations = translationServiceClient
      .getTranslations(List.of(Item.DESCRIPTION_TRANSLATION_KEY.entityName()), null, null, null);
    if (translations.isSuccess()) {
      translations.getData().forEach((TranslationDto t) -> System.out.println("Translation: " + t));
      List<ItemDto> items = itemRepository
        .findAll()
        .stream()
        .map(
          (Item item) -> new ItemDto(item)
            .setDescription(
              translations
                .getData()
                .stream()
                .filter(
                  (TranslationDto t) -> t.getKey().equals(Item.DESCRIPTION_TRANSLATION_KEY)
                      && t.getEntityId().equals(item.getId())
                )
                .map((TranslationDto t) -> new TranslationRecord(t.getLanguageCode(), t.getText()))
                .collect(Collectors.toList())
            )
            .setName(
              translations
                .getData()
                .stream()
                .filter(
                  (TranslationDto t) -> t.getKey().equals(Item.NAME_TRANSLATION_KEY)
                      && t.getEntityId().equals(item.getId())
                )
                .map((TranslationDto t) -> new TranslationRecord(t.getLanguageCode(), t.getText()))
                .collect(Collectors.toList())
            )
        )
        .collect(Collectors.toList());
      return ResultDto.success(items);
    } else {
      result = translations.convert(new ArrayList<ItemDto>());
    }
    return result;
  }

  @Override
  @Transactional
  public ResultDto<ItemDto> createItem(ItemDto item) {
    ResultDto<ItemDto> result;
    ItemDto savedItem = new ItemDto(itemRepository.save(new Item(item)));
    List<TranslationDto> translationsToSave = item
      .getDescription()
      .stream()
      .map(
        (
          TranslationRecord t
        ) -> new TranslationDto(Item.DESCRIPTION_TRANSLATION_KEY, savedItem.getId(), t.language(), t.text())
      )
      .collect(Collectors.toList());
    translationsToSave
      .addAll(
        item
          .getName()
          .stream()
          .map(
            (
              TranslationRecord t
            ) -> new TranslationDto(Item.NAME_TRANSLATION_KEY, savedItem.getId(), t.language(), t.text())
          )
          .collect(Collectors.toList())
      );
    ResultDto<List<TranslationDto>> savedTranslations = translationServiceClient.createTranslations(translationsToSave);
    if (!savedTranslations.isSuccess()) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      result = savedTranslations.convert(item);
    } else {
      savedItem
        .setDescription(
          savedTranslations
            .getData()
            .stream()
            .filter((TranslationDto t) -> t.getKey().equals(Item.DESCRIPTION_TRANSLATION_KEY))
            .map((TranslationDto t) -> new TranslationRecord(t.getLanguageCode(), t.getText()))
            .collect(Collectors.toList())
        );
      savedItem
        .setName(
          savedTranslations
            .getData()
            .stream()
            .filter((TranslationDto t) -> t.getKey().equals(Item.NAME_TRANSLATION_KEY))
            .map((TranslationDto t) -> new TranslationRecord(t.getLanguageCode(), t.getText()))
            .collect(Collectors.toList())
        );
      result = ResultDto.success(savedItem);
    }
    return result;
  }
  //#endregion
}
