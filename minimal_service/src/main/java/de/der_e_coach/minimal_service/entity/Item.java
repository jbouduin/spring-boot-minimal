package de.der_e_coach.minimal_service.entity;

import java.math.BigDecimal;

import de.der_e_coach.minimal_service.dto.ItemDto;
import de.der_e_coach.minimal_service.entity.listener.ItemListener;
import de.der_e_coach.shared_lib.dto.translation.TranslationKeyDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@EntityListeners(ItemListener.class)
public class Item {
  //#region Static ------------------------------------------------------------
  public final static TranslationKeyDto DESCRIPTION_TRANSLATION_KEY = new TranslationKeyDto("item", "description");
  public final static TranslationKeyDto NAME_TRANSLATION_KEY = new TranslationKeyDto("item", "name");
  //#endregion
  
  //#region Entity field - id -------------------------------------------------
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  //#endregion

  //#region Entity field - code -----------------------------------------------
  @NotNull(message = "Code must have a value")
  @NotEmpty(message = "Code must have a value")
  @Column(name = "code")
  private String code;
  //#endregion

  //#region Entity field - price ----------------------------------------------
  @Column(name = "price")
  @NotNull(message = "Price must have a value")
  @Min(value = 0, message = "Price must be greater than or equal to zero.")
  BigDecimal price;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public Item() {
  }

  public Item(ItemDto item) {
    this.id = item.getId();
    this.code = item.getCode();
    this.price = item.getPrice();
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public Long getId() {
    return id;
  }

  public Item setId(Long id) {
    this.id = id;
    return this;
  }

  public String getCode() {
    return code;
  }

  public Item setCode(String code) {
    this.code = code;
    return this;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Item setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }
  //#endregion
}
