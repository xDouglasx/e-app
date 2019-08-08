package com.douglas.eapp.domain.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum PaymentStatus {

  PENDING(1, "PENDING"),
  EXPIRED(2, "EXPIRED"),
  PAID(3, "PAID");

  private Integer key;
  private String value;

  PaymentStatus(Integer key, String value) {
    this.key = key;
    this.value = value;
  }

  public static Map<Integer, String> getComboList() {
    Map<Integer, String> PaymentStatusMap = new LinkedHashMap<>();
    PaymentStatus[] paymentStatusEnum = PaymentStatus.values();
    PaymentStatusMap.put(0, "");
    for (PaymentStatus paymentStatus : paymentStatusEnum) {
      PaymentStatusMap.put(paymentStatus.getKey(), paymentStatus.getValue());
    }
    return PaymentStatusMap;
  }

  public Integer getKey() {
    return this.key;
  }

  public String getValue() {
    return this.value;
  }
}
