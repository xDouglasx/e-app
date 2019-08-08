package com.douglas.eapp.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Embeddable
public class OrderItemPK implements Serializable {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "customer_order_id")
  private CustomerOrder customerOrder;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    OrderItemPK other = (OrderItemPK) obj;
    if (customerOrder == null) {
      if (other.customerOrder != null) {
        return false;
      }
    } else if (!customerOrder.equals(other.customerOrder)) {
      return false;
    }
    if (customerOrder == null) {
      if (other.customerOrder != null) {
        return false;
      }
    } else if (!customerOrder.equals(other.customerOrder)) {
      return false;
    }
    return true;
  }

}
