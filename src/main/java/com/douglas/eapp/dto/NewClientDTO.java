package com.douglas.eapp.dto;

import com.douglas.eapp.service.validation.NewClient;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NewClient
public class NewClientDTO {

  @NotEmpty(message = "Cannot be null")
  @Length(min = 5, max = 80, message = "Size must be between 5 and 80 characters")
  private String name;

  @NotEmpty(message = "Cannot be null")
  @Length(min = 5, max = 80, message = "Size must be between 5 and 80 characters")
  private String email;
  private String cpfOrCnpj;
  private Integer personType;

  @NotEmpty(message = "Cannot be null")
  private String addressName;

  @NotEmpty(message = "Cannot be null")
  private String number;
  private String complement;
  private String district;
  private String zipCode;

  private String phone1;
  private String phone2;
  private String phone3;

  private Integer cityId;

  @NotEmpty(message = "Cannot be null")
  private String password;
}
