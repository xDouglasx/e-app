package com.douglas.eapp.domain;

import com.douglas.eapp.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@JsonTypeName("cardPayment")
public class CardPayment extends Payment {

  private Integer installmentsNumber;

  public CardPayment(PaymentStatus paymentStatus, CustomerOrder customerOrder, Integer installmentsNumber) {
    this.paymentStatus = paymentStatus.getKey();
    this.customerOrder = customerOrder;
    this.installmentsNumber = installmentsNumber;
  }
}
