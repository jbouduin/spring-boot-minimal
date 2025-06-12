package de.der_e_coach.minimal_service.dto;

import java.math.BigDecimal;
import java.util.List;

import de.der_e_coach.minimal_service.entity.Item;
import de.der_e_coach.shared_lib.dto.translation.TranslationRecord;

public class ItemDto {
  //#region Private fields ----------------------------------------------------
  private Long id;
  private String code;
  private BigDecimal price;
  private List<TranslationRecord> name;
  private List<TranslationRecord> description;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public ItemDto() {
  }

  public ItemDto(Item item) {
    this.id = item.getId();
    this.code = item.getCode();
    this.price = item.getPrice();
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public Long getId() {
    return id;
  }

  public ItemDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getCode() {
    return code;
  }

  public ItemDto setCode(String code) {
    this.code = code;
    return this;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public ItemDto setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }
  
  public List<TranslationRecord> getDescription() {
    return description;
  }
  
  public ItemDto setDescription(List<TranslationRecord> descriptions) {
    this.description = descriptions;
    return this;
  }
  
  public List<TranslationRecord> getName() {
    return name;
  }
  
  public ItemDto setName(List<TranslationRecord> name) {
    this.name = name;
    return this;
  }
  //#endregion
}
