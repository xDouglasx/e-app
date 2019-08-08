package com.douglas.eapp.controller.exception.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldMessage implements Serializable {

  private String fieldName;
  private String message;

}
