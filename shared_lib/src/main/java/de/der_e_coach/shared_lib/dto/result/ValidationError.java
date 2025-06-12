package de.der_e_coach.shared_lib.dto.result;

public class ValidationError {
  //#region Private fields ----------------------------------------------------
  private String identifier;
  private String errorMessage;
  private String errorCode;
  private String severity;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public ValidationError() {
    // Default constructor
  }

  public ValidationError(Class<?> sourceClass, String methodName, String ruleNumber, String errorMessage) {
    this.identifier = String.format("%s.%s#V%s", sourceClass.getSimpleName(), methodName, ruleNumber);
    this.errorMessage = errorMessage;
    this.severity = ValidationSeverity.Error.name();
  }  
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    this.severity = severity;
  }
  //#endregion
}
