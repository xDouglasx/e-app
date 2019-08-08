package com.douglas.eapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode
public class Product implements Serializable {

  private static final long serialVersionUID = 1L;
  @JsonIgnore
  @ManyToMany
  @JoinTable(name = "PRODUCT_CATEGORY",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  List<Category> categories = new ArrayList<>();
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private double price;
  @JsonIgnore
  @OneToMany(mappedBy = "id.product")
  private Set<OrderItem> orderItems = new HashSet<>();

  public Product() {
  }

  public Product(String name, double price) {
    this.name = name;
    this.price = price;
  }

  @JsonIgnore
  public List<CustomerOrder> getCustomerOrders() {
    List<CustomerOrder> customerOrders = new ArrayList<>();
    orderItems.forEach(x -> customerOrders.add(x.getCustomerOrder()));
    return customerOrders;
  }

}
