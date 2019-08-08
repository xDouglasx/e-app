package com.douglas.eapp.domain.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum ClientType {

  PERSON(1, "PERSON"),
  COMPANY(2, "COMPANY");

  private Integer key;
  private String value;

  ClientType(Integer key, String value) {
    this.key = key;
    this.value = value;
  }

  public static Map<Integer, String> getComboList() {
    Map<Integer, String> clientTypeMap = new LinkedHashMap<>();
    ClientType[] clientTypeEnum = ClientType.values();
    clientTypeMap.put(0, "");
    for (ClientType clientType : clientTypeEnum) {
      clientTypeMap.put(clientType.getKey(), clientType.getValue());
    }
    return clientTypeMap;
  }

  public Integer getKey() {
    return this.key;
  }

  public String getValue() {
    return this.value;
  }

}
