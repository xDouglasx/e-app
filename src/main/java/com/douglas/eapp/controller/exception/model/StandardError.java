package com.douglas.eapp.controller.exception.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {

  Integer status;
  String message;
  @JsonFormat(pattern = "dd/MM/yyyy")
  Long timestamp;

}
