package com.douglas.eapp.domain;

import com.douglas.eapp.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.Date;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@JsonTypeName("ticketPayment")
public class TicketPayment extends Payment {

  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date expireDate;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date paymentDate;

  public TicketPayment(PaymentStatus paymentStatus, CustomerOrder customerOrder, Date expireDate, Date paymentDate) {
    this.paymentStatus = paymentStatus.getKey();
    this.customerOrder = customerOrder;
    this.expireDate = expireDate;
    this.paymentDate = paymentDate;

  }

}
