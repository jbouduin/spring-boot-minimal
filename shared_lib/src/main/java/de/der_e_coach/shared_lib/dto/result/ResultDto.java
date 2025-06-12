package de.der_e_coach.shared_lib.dto.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ResultDto<T> {
  //#region Static factory methods --------------------------------------------
  /**
   * @param <T>
   * @param value
   * @return a Result with Http Status 200
   */
  public static <T> ResultDto<T> success(T value) {
    return new ResultDto<T>(value);
  }

  /**
   * @param <T>
   * @param value
   * @param successMessage
   * @return a Result with Http Status 200
   */
  public static <T> ResultDto<T> success(T value, String successMessage) {
    return new ResultDto<T>(value, successMessage);
  }

  /**
   * @param <T>
   * @param errorMessages
   * @return a Result with http status 500
   */
  public static <T> ResultDto<T> error(List<String> errorMessages) {
    ResultDto<T> result = new ResultDto<T>(ResultStatus.ERROR);
    result.errors = errorMessages;
    return result;
  }

  public static <T> ResultDto<T> error(List<String> errorMessages, int resultStatusCode) {
    ResultStatus status = ResultStatus.valueOf(resultStatusCode);
    ResultDto<T> result = new ResultDto<T>(status);
    result.errors = errorMessages;
    return result;
  }

  /**
   * @param <T>
   * @param error
   * @return a Result with http status 500
   */
  public static <T> ResultDto<T> error(String error) {
    List<String> errors = new ArrayList<>();
    errors.add(error);
    return error(errors);
  }

  /**
   * @param <T>
   * @param validationErrors
   * @return a Result with http status 400
   */
  public static <T> ResultDto<T> invalid(List<ValidationError> validationErrors) {
    ResultDto<T> result = new ResultDto<T>(ResultStatus.INVALID);
    result.validationErrors = validationErrors;
    return result;
  }

  /**
   * @param <T>
   * @return a Result with http status 404
   */
  public static <T> ResultDto<T> notFound() {
    return new ResultDto<T>(ResultStatus.NOT_FOUND);
  }

  /**
   * @param <T>
   * @param errorMessage
   * @return a Result with http status 400
   */
  public static <T> ResultDto<T> notFound(String errorMessage) {
    ArrayList<String> errorMessages = new ArrayList<>();
    errorMessages.add(errorMessage);
    return notFound(errorMessages);
  }

  /**
   * @param <T>
   * @param entity
   * @param id
   * @return @return a Result with http status 404
   */
  public static <T> ResultDto<T> notFound(String entity, Long id) {
    ArrayList<String> errorMessages = new ArrayList<>();
    errorMessages.add(entity + " with Id '" + id + "' not found.");
    return notFound(errorMessages);
  }

  /**
   * @param <T>
   * @param errorMessages
   * @return a Result with http status 404
   */
  public static <T> ResultDto<T> notFound(List<String> errorMessages) {
    ResultDto<T> result = new ResultDto<T>(ResultStatus.NOT_FOUND);
    result.errors = errorMessages;
    return result;
  }

  /**
   * @param <T>
   * @return a Result with http status 403
   */
  public static <T> ResultDto<T> forbidden() {
    return new ResultDto<T>(ResultStatus.FORBIDDEN);
  }

  /**
   * @param <T>
   * @param errorMessage
   * @return a Result with http status 403
   */
  public static <T> ResultDto<T> forbidden(String errorMessage) {
    ArrayList<String> errorMessages = new ArrayList<>();
    errorMessages.add(errorMessage);
    ResultDto<T> result = new ResultDto<T>(ResultStatus.FORBIDDEN);
    result.errors = errorMessages;
    return result;
  }

  /**
   * @param <T>
   * @return a Result with http status 401
   */
  public static <T> ResultDto<T> unauthorized() {
    return new ResultDto<T>(ResultStatus.UNAUTHORIZED);
  }

  /**
   * @param <T>
   * @return a Result with http status 405
   */
  public static <T> ResultDto<T> notAllowed(String message) {
    ResultDto<T> result = new ResultDto<T>(ResultStatus.METHOD_NOT_ALLOWED);
    result.errors.add(message);
    return result;
  }

  /**
   * @param <T>
   * @return a Result with http status 409
   */
  public static <T> ResultDto<T> conflict(String message) {
    ResultDto<T> result = new ResultDto<T>(ResultStatus.CONFLICT);
    result.errors.add(message);
    return result;
  }

  public static ResultDto<Object> deleteSuccessResult() {
    return new ResultDto<Object>(ResultStatus.NO_CONTENT);
  }
  // #endregion

  //#region Private fields ----------------------------------------------------
  private T data;
  private ResultStatus status = ResultStatus.OK;
  private boolean success = true;
  private String successMessage = "";
  private List<String> errors = new ArrayList<String>();
  private List<ValidationError> validationErrors = new ArrayList<ValidationError>();
  // #endregion

  //#region Constructor -------------------------------------------------------
  private ResultDto(T value) {
    data = value;
    setMessage();
  }

  private ResultDto(T value, String successMessage) {
    this(value);
    this.successMessage = successMessage;
    this.setSuccess();
  }

  /**
   * Create a result with the given status. If the status is a sucecess status,
   * the succesMessage is set to the status description. Otherwise the status
   * description is added to the error messages.
   *
   * @param status {@link ResultStatus}
   */
  public ResultDto(ResultStatus status) {
    this.status = status;
    this.setSuccess();
  }

  public ResultDto() {
    
  }
  // #endregion

  //#region Getters -----------------------------------------------------------
  public String getSuccessMessage() {
    return successMessage;
  }

  public ResultStatus getStatus() {
    return status;
  }

  public List<String> getErrors() {
    return this.errors;
  }

  public void setErrors(String... errors) {
    this.errors = Arrays.asList(errors);
  }

  public List<ValidationError> getValidationErrors() {
    return this.validationErrors;
  }

  public boolean isSuccess() {
    return success;
  }

  public T getData() {
    return data;
  }
  // #endregion

  public <U> ResultDto<U> convert(U value) {
    ResultDto<U> converted = new ResultDto<U>(value);
    converted.errors = this.errors;
    converted.status = this.status;
    converted.success = this.success;
    converted.successMessage = this.successMessage;
    converted.validationErrors = this.validationErrors;
    return converted;
  } 

  //#region Auxiliary methods -------------------------------------------------
  private void setSuccess() {
    success = status.getValue() < ResultStatus.BAD_REQUEST.getValue();
  }

  private void setMessage() {
    if (isSuccess()) {
      successMessage = status.getDescription();
    } else {
      errors.add(status.getDescription());
    }
  }
  // #endregion
}
