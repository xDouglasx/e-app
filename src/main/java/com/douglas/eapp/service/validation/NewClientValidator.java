package com.douglas.eapp.service.validation;

import com.douglas.eapp.controller.exception.model.FieldMessage;
import com.douglas.eapp.domain.enums.ClientType;
import com.douglas.eapp.dto.NewClientDTO;
import com.douglas.eapp.repository.ClientRepository;
import com.douglas.eapp.service.validation.utils.BR;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NewClientValidator implements ConstraintValidator<NewClient, NewClientDTO> {

  @Autowired
  ClientRepository clientRepository;

  @Override
  public void initialize(NewClient newClient) {
  }

  @Override
  public boolean isValid(NewClientDTO newCLientDTO, ConstraintValidatorContext context) {

    List<FieldMessage> list = new ArrayList<>();

    if(newCLientDTO.getPersonType().equals(ClientType.PERSON.getKey()) && !BR.isValidCPF(newCLientDTO.getCpfOrCnpj())){
      list.add(new FieldMessage("cpfOrCnpj", "invalid CPF"));
    }

    if(newCLientDTO.getPersonType().equals(ClientType.COMPANY.getKey()) && !BR.isValidCNPJ(newCLientDTO.getCpfOrCnpj())){
      list.add(new FieldMessage("cpfOrCnpj", "invalid CNPJ"));
    }

    if(clientRepository.findByEmail(newCLientDTO.getEmail()) != null){
      list.add(new FieldMessage("Email", "Email already in use"));
    }

    for (FieldMessage e : list) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(e.getMessage())
          .addPropertyNode(e.getFieldName()).addConstraintViolation();
    }
    return list.isEmpty();
  }
}
