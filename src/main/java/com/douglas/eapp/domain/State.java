package com.douglas.eapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class State implements Serializable {

  private static final long serialVersionUID = 1L;
  @JsonIgnore
  @OneToMany(mappedBy = "state")
  List<City> cities = new ArrayList<>();
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;

  public State() {
  }

  public State(String name) {
    this.name = name;
  }


}
