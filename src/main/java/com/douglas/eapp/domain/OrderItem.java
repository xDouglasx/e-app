package com.douglas.eapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class OrderItem implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  @JsonIgnore
  private OrderItemPK id = new OrderItemPK();

  private double discount;
  private Integer quantity;
  private double price;

  public OrderItem(Product product, CustomerOrder customerOrder, double discount, Integer quantity, double price) {
    this.id.setCustomerOrder(customerOrder);
    this.id.setProduct(product);
    this.discount = discount;
    this.quantity = quantity;
    this.price = price;
  }

  @JsonIgnore
  public CustomerOrder getCustomerOrder() {
    return id.getCustomerOrder();
  }
  public void setCustomerOrder(CustomerOrder order){ this.id.setCustomerOrder(order); }

  public Product getProduct() {
    return id.getProduct();
  }
  public void setProduct(Product product){  this.id.setProduct(product); }

  public double getSubTotal(){
    return (price - discount) * quantity;
  }



}
