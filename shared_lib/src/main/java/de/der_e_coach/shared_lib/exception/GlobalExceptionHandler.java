package de.der_e_coach.shared_lib.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.result.ValidationError;


@ControllerAdvice
public class GlobalExceptionHandler {
  //#region Exception Handlers ------------------------------------------------
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ResultDto<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    List<ValidationError> validationErrors = new ArrayList<ValidationError>();
    validationErrors.add(new ValidationError(getClass(), "handleHttpMessageNotReadable", "001", ex.getMessage()));
    ResultDto<Object> result = ResultDto.invalid(validationErrors);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ResultDto<Object>> hanldeHttpRequestMethodNotSupported(
    HttpRequestMethodNotSupportedException ex
  ) {
    ResultDto<Object> result = ResultDto.notAllowed(ex.getMessage());
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ResultDto<Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    Throwable rootCause = ex.getMostSpecificCause();
    String rawMessage = rootCause != null ? rootCause.getMessage() : ex.getMessage();
    String detailMessage = extractDetailMessage(rawMessage);
    ResultDto<Object> result = ResultDto.conflict(detailMessage != null ? detailMessage : rawMessage);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResultDto<Object>> catchAll(Exception ex) {
    ex.printStackTrace();
    ResultDto<Object> result = ResultDto.error(ex.getClass() + ": " + ex.getMessage());
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion

  //#region Auxiliary Methods -------------------------------------------------
  private String extractDetailMessage(String message) {
    String result = null;
    if (message != null) {
      // Look for the "Detail:" line and extract the part after it
      int detailIndex = message.indexOf("Detail:");
      if (detailIndex != -1) {
        String detailLine = message.substring(detailIndex + "Detail:".length()).trim();
        result = detailLine;
      }
    }
    return result;
  }
  //#endregion
}
