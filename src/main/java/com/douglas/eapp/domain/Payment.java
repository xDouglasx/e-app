package com.douglas.eapp.domain;


import com.douglas.eapp.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "@type")
public abstract class Payment implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Getter
  @Setter
  protected Integer id;
  protected Integer paymentStatus;

  @OneToOne
  @JoinColumn(name = "customer_order_id")
  @MapsId
  @JsonIgnore
  @Getter
  @Setter
  protected CustomerOrder customerOrder;

  public PaymentStatus getPaymentStatus() {
    return PaymentStatus.valueOf(PaymentStatus.getComboList().get(paymentStatus));
  }

  public void setPaymentStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus.getKey();
  }


}
