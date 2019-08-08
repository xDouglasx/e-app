package com.douglas.eapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class CustomerOrder implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date date;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "customerOrder")
  private Payment payment;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToOne
  @JoinColumn(name = "delivery_address_id")
  private Address deliveryAddress;

  @OneToMany(mappedBy = "id.customerOrder")
  private Set<OrderItem> orderItems = new HashSet<>();

  public CustomerOrder(Date date, Client client, Address deliveryAddress) {
    this.date = date;
    this.client = client;
    this.deliveryAddress = deliveryAddress;
  }

  @Override
  public String toString() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public double getTotalValue(){
    double sum = 0.0;
    for(OrderItem order : orderItems){
      sum = sum + order.getSubTotal();
    }
    return sum;
  }


}
