package com.douglas.eapp.service;


import com.douglas.eapp.domain.Address;
import com.douglas.eapp.domain.Client;
import com.douglas.eapp.domain.enums.Profile;
import com.douglas.eapp.dto.ClientDTO;
import com.douglas.eapp.dto.NewClientDTO;
import com.douglas.eapp.exception.AuthorizationException;
import com.douglas.eapp.exception.ObjectNotFoundException;
import com.douglas.eapp.repository.AddressRepository;
import com.douglas.eapp.repository.CityRepository;
import com.douglas.eapp.repository.ClientRepository;
import com.douglas.eapp.security.UserSS;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private CityRepository cityRepository;

  @Autowired
  private AddressRepository addressRepository;

  public Client findClient(Integer id) {
    UserSS user = UserService.authenticated();
    if (user == null || !user.hasRole(Profile.ROLE_ADMIN) && !id.equals((user.getId()))){
      throw new AuthorizationException("Access Denied");
    }

    return clientRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Client with id: " + id + "not found."));
  }

  @Transactional
  public Client insert(Client client) {
    return clientRepository.save(client);
  }

  public Client update(Client updateClient) {
    Client client = mergeClient(findClient((updateClient.getId())), updateClient);
    return clientRepository.save(client);
  }

  public void delete(Integer id) {
    findClient(id);
    try {
      clientRepository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationException("Cannot delete");
    }
  }

  public List<Client> findAll() {
    return clientRepository.findAll();
  }

  public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    return clientRepository.findAll(pageRequest);
  }

  public Client fromDTO(ClientDTO clientDTO) {
    return Client.builder()
        .id(clientDTO.getId())
        .name(clientDTO.getName())
        .email(clientDTO.getEmail())
        .build();
  }

  public Client fromDTO(NewClientDTO newClientDTO) {

    Address address = Address.builder()
        .name(newClientDTO.getAddressName())
        .number(newClientDTO.getNumber())
        .complement(newClientDTO.getComplement())
        .district(newClientDTO.getDistrict())
        .zipCode(newClientDTO.getZipCode())
        .city(cityRepository.getOne(newClientDTO.getCityId()))
        .build();

    Client client = Client.builder()
        .name(newClientDTO.getName())
        .email(newClientDTO.getEmail())
        .cpfOrCnpj(newClientDTO.getCpfOrCnpj())
        .clientType(newClientDTO.getPersonType())
        .phones(Stream.of(newClientDTO.getPhone1(), newClientDTO.getPhone2(), newClientDTO.getPhone3()).collect(Collectors.toSet()))
        .password(passwordEncoder.encode(newClientDTO.getPassword()))
        .build();

    address.setClient(client);
    client.setAddresses(Collections.singletonList(address));
    return client;
  }

  private Client mergeClient(Client client, Client updateClient) {
    client.setEmail(updateClient.getEmail());
    client.setName(updateClient.getName());
    return client;
  }
}
