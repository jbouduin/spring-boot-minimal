package de.der_e_coach.minimal_service.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import de.der_e_coach.minimal_service.dto.ItemDto;
import de.der_e_coach.minimal_service.entity.Item;
import de.der_e_coach.minimal_service.repository.ItemRepository;
import de.der_e_coach.minimal_service.service.ItemService;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.result.ValidationError;
import de.der_e_coach.shared_lib.dto.translation.TranslationDto;
import de.der_e_coach.shared_lib.dto.translation.TranslationMergeDto;
import de.der_e_coach.shared_lib.dto.translation.TranslationRecord;
import de.der_e_coach.shared_lib.service.feign.translation_service.TranslationServiceClient;
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
  public ResultDto<Object> deleteItem(Long itemId) {
    return itemRepository
      .findById(itemId)
      .map((Item i) -> {
        itemRepository.delete(i);
        return ResultDto.deleteSuccessResult();
      })
      .orElse(ResultDto.notFound("Item with id '" + itemId + "'not found."));
  }

  @Override
  public ResultDto<List<ItemDto>> getItems() {
    ResultDto<List<ItemDto>> result;
    ResultDto<List<TranslationDto>> translations = translationServiceClient
      .getTranslations(List.of(Item.DESCRIPTION_TRANSLATION_KEY.entityName()), null, null, null);
    if (translations.isSuccess()) {
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
    List<ValidationError> validationErrors = new ArrayList<ValidationError>();

    if (item.getId() != null) {
      validationErrors
        .add(new ValidationError(this.getClass(), "createItem", "001", "Item Id may not have a value."));
    }

    if (item.getCode() == null || item.getCode().isBlank()) {
      validationErrors
        .add(new ValidationError(this.getClass(), "createItem", "002", "Item code must have a value."));
    }

    if (item.getPrice() == null || item.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
      validationErrors
        .add(new ValidationError(this.getClass(), "createItem", "003", "Price must be greater than 0."));
    }

    validateTranslations(validationErrors, item.getDescription(), "createItem", "004", "item description");
    validateTranslations(validationErrors, item.getName(), "createItem", "005", "item name");

    if (!validationErrors.isEmpty()) {
      result = ResultDto.invalid(validationErrors);
    } else {
      ItemDto savedItem = new ItemDto(itemRepository.save(new Item(item)));

      ResultDto<List<TranslationDto>> savedTranslations = saveTranslations(item, savedItem);
      if (!savedTranslations.isSuccess()) {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        result = savedTranslations.convert(item);
      } else {
        processSavedTranslations(savedItem, savedTranslations.getData());
        result = ResultDto.success(savedItem);
      }
    }
    return result;
  }

  @Override
  @Transactional
  public ResultDto<ItemDto> updateItem(Long itemId, ItemDto item) {
    ResultDto<ItemDto> result;
    List<ValidationError> validationErrors = new ArrayList<ValidationError>();

    if (!itemId.equals(item.getId())) {
      validationErrors
        .add(new ValidationError(this.getClass(), "updateItem", "001", "Item Id in URL and body do not match"));
    }

    if (item.getCode() == null || item.getCode().isBlank()) {
      validationErrors
        .add(new ValidationError(this.getClass(), "updateItem", "002", "Item code must have a value."));
    }

    if (item.getPrice() == null || item.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
      validationErrors
        .add(new ValidationError(this.getClass(), "updateItem", "003", "Price must be greater than 0."));
    }

    validateTranslations(validationErrors, item.getDescription(), "updateItem", "004", "item description");
    validateTranslations(validationErrors, item.getName(), "updateItem", "005", "item name");

    if (!validationErrors.isEmpty()) {
      result = ResultDto.invalid(validationErrors);
    } else {
      result = itemRepository
        .findById(itemId)
        .map((Item i) -> {
          ItemDto savedItem = new ItemDto(itemRepository.save(i.setCode(item.getCode()).setPrice(item.getPrice())));
          ResultDto<List<TranslationDto>> savedTranslations = saveTranslations(item, savedItem);
          if (!savedTranslations.isSuccess()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return savedTranslations.convert(item);
          } else {
            processSavedTranslations(savedItem, savedTranslations.getData());
            return ResultDto.success(savedItem);
          }
        })
        .orElse(ResultDto.notFound("Item with id '" + itemId + "'not found."));
    }
    return result;
  }
  //#endregion

  //#region Auxiliary Methods -------------------------------------------------
  private void validateTranslations(
    List<ValidationError> validationErrors,
    List<TranslationRecord> translations,
    String methodName,
    String rule,
    String fieldName
  ) {
    translations.forEach((TranslationRecord t) -> {
      if (t.text() == null || t.text().isBlank()) {
        validationErrors
          .add(
            new ValidationError(
              this.getClass(), methodName, rule,
              "Translation for " + fieldName + " for language " + t.language() + " must have a value"
            )
          );
      }
    });
  }

  private ResultDto<List<TranslationDto>> saveTranslations(ItemDto item, ItemDto savedItem) {
    List<TranslationMergeDto> translationsToMerge = new ArrayList<TranslationMergeDto>(
      List
        .of(
          new TranslationMergeDto()
            .setEntityId(savedItem.getId())
            .setKey(Item.DESCRIPTION_TRANSLATION_KEY)
            .setTranslations(item.getDescription()),
          new TranslationMergeDto()
            .setEntityId(savedItem.getId())
            .setKey(Item.NAME_TRANSLATION_KEY)
            .setTranslations(item.getName())
        )
    );
    return translationServiceClient
      .mergeTranslations(translationsToMerge);
  }

  private void processSavedTranslations(ItemDto savedItem, List<TranslationDto> savedTranslations) {
    savedItem
      .setDescription(
        savedTranslations
          .stream()
          .filter((TranslationDto t) -> t.getKey().equals(Item.DESCRIPTION_TRANSLATION_KEY))
          .map((TranslationDto t) -> new TranslationRecord(t.getLanguageCode(), t.getText()))
          .collect(Collectors.toList())
      );
    savedItem
      .setName(
        savedTranslations
          .stream()
          .filter((TranslationDto t) -> t.getKey().equals(Item.NAME_TRANSLATION_KEY))
          .map((TranslationDto t) -> new TranslationRecord(t.getLanguageCode(), t.getText()))
          .collect(Collectors.toList())
      );
  }
  //#endregion  
}
