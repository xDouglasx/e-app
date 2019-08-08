package com.douglas.eapp.service.validation;

import com.douglas.eapp.controller.exception.model.FieldMessage;
import com.douglas.eapp.domain.Client;
import com.douglas.eapp.dto.ClientDTO;
import com.douglas.eapp.repository.ClientRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

public class ClientValidator implements ConstraintValidator<ClientUpdate, ClientDTO> {

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public void initialize(ClientUpdate clientUpdate) {
  }

  @Override
  public boolean isValid(ClientDTO clientDTO, ConstraintValidatorContext context) {

    List<FieldMessage> list = new ArrayList<>();
    Map<String, String> map =  (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    Integer uriId = Integer.parseInt(map.get("id"));
    Client client = clientRepository.findByEmail(clientDTO.getEmail()).get();

    if( client != null && !uriId.equals(client.getId())){
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
