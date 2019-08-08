package com.douglas.eapp.controller;

import com.douglas.eapp.domain.Client;
import com.douglas.eapp.dto.ClientDTO;
import com.douglas.eapp.dto.NewClientDTO;
import com.douglas.eapp.service.ClientService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "clients")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @GetMapping(value = "/{id}")
  public ResponseEntity findClient(@PathVariable Integer id) {
    return ResponseEntity.ok().body(clientService.findClient(id));
  }

  @PostMapping
  public ResponseEntity<Void> insert(@Valid @RequestBody NewClientDTO newClientDTO) {
    Client client = clientService.insert(clientService.fromDTO(newClientDTO));
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(client.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @PutMapping(value = "/{id}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Client> update(@Valid @RequestBody ClientDTO clientDTO, @PathVariable("id") String id) {
    clientDTO.setId(Integer.parseInt(id));
    return ResponseEntity.ok(clientService.update(clientService.fromDTO(clientDTO)));
  }

  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable("id") String id) {
    clientService.delete(Integer.parseInt(id));
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<List<ClientDTO>> findAll() {
    List<ClientDTO> clientList = clientService.findAll()
        .stream()
        .map(ClientDTO::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(clientList);
  }

  @GetMapping(value = "/page")
  public ResponseEntity<Page<ClientDTO>> findPage(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerpage,
      @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
      @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
    Page<ClientDTO> clientList = clientService.findPage(page, linesPerpage, orderBy, direction).map(ClientDTO::new);
    return ResponseEntity.ok().body(clientList);
  }
}
