package de.der_e_coach.shared_lib.dto.translation;

public enum LanguageCode { //implements TranslatableEnum {
    //#region Enum values -----------------------------------------------------
    DE("de", "flag_de.jpg", "de"),
    EN("en", "flag_en.jpg", "en"),
    NL("nl", "flag_nl.jpg", "nl"),
    FR("fr", "flag_fr.jpg", "fr");
  // #endregion

  //#region Private fields ----------------------------------------------------
  final private TranslationKeyDto translationKey;
  final private String image;
  final private String siteValue;
  //#endregion

  //#region Constructor -------------------------------------------------------
  private LanguageCode(String translationFieldName, String image, String siteValue) {
    this.translationKey = new TranslationKeyDto("lng", translationFieldName);
    this.siteValue = siteValue;
    this.image = image;
  }
  //#endregion

  //#region Getters -----------------------------------------------------------
  public String getImage() {
    return image;
  }  

  public String getSiteValue() {
    return siteValue;
  }
  //#endregion
  
  public TranslationKeyDto getTranslationKey() {
    return this.translationKey;
  }
}
