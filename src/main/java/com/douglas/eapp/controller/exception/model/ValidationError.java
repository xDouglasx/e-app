package com.douglas.eapp.controller.exception.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationError extends StandardError {

  private List<FieldMessage> errors = new ArrayList<>();

  public ValidationError(Integer status, String message, Long timestamp) {
    this.status = status;
    this.message = message;
    this.timestamp = timestamp;
  }

  public void addError(String fieldName, String message) {
    errors.add(new FieldMessage(fieldName, message));
  }

}
