package com.douglas.eapp.domain;

import com.douglas.eapp.domain.enums.ClientType;
import com.douglas.eapp.domain.enums.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class Client implements Serializable {

  private static final long serialVersionUID = 1L;
  @Getter
  @Setter
  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
  private List<Address> addresses = new ArrayList<>();
  @OneToMany(mappedBy = "client")
  @Getter
  @Setter
  @JsonIgnore
  private List<CustomerOrder> customerOrders = new ArrayList<>();
  @Id
  @Getter
  @Setter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Getter
  @Setter
  private String name;
  @Getter
  @Setter
  private String email;
  @Getter
  @Setter
  private String cpfOrCnpj;
  @Getter
  @Setter
  @ElementCollection
  @CollectionTable(name = "phones")
  private Set<String> phones = new HashSet<>();
  private Integer clientType;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name="profiles")
  private Set<Integer> profiles = new HashSet<>();

  @JsonIgnore
  @Getter
  @Setter
  private String password;

  public Client() {
    addProfile(Profile.ROLE_CLIENT);
  }

  public Client(String name, String email, String cpfOrCnpj, ClientType clientType, String password) {
    this.name = name;
    this.email = email;
    this.cpfOrCnpj = cpfOrCnpj;
    this.clientType = clientType.getKey();
    this.password = password;
    addProfile(Profile.ROLE_CLIENT);
  }

  public ClientType getClientType() {
    return ClientType.valueOf(ClientType.getComboList().get(clientType));
  }

  public void setClientType(ClientType client) {
    this.clientType = client.getKey();
  }

  public Set<Profile> getProfile() {
    return profiles.stream().map(x -> Profile.valueOf(Profile.getComboList().get(x))).collect(Collectors.toSet());
  }

  public void addProfile(Profile profile){
     profiles.add(profile.getKey());
  }

}
