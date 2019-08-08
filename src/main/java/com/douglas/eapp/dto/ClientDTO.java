package com.douglas.eapp.dto;

import com.douglas.eapp.domain.Client;
import com.douglas.eapp.service.validation.ClientUpdate;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ClientUpdate
public class ClientDTO implements Serializable {


  private Integer id;
  @NotEmpty(message = "Cannot be null")
  @Length(min = 5, max = 80, message = "Size must be between 5 and 80 characters")
  private String name;

  @NotEmpty(message = "Cannot be null")
  @Email(message = "Email format invalid")
  private String email;

  public ClientDTO(Client client) {
    id = client.getId();
    name = client.getName();
    email = client.getEmail();
  }
}
