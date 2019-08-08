package com.douglas.eapp.domain;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode
@ToString
public class City implements Serializable {

  private static final long serialVersionUID = 1L;
  @ManyToOne
  @JoinColumn(name = "state_id")
  State state;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;

  public City() {
  }

  public City(String name, State state) {
    this.name = name;
    this.state = state;
  }
}
