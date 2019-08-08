package com.douglas.eapp.domain.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Profile {

  ROLE_ADMIN(1, "ROLE_ADMIN"),
  ROLE_CLIENT(2, "ROLE_CLIENT");

  private Integer key;
  private String value;

  Profile(Integer key, String value) {
    this.key = key;
    this.value = value;
  }

  public static Map<Integer, String> getComboList() {
    Map<Integer, String> profileMap = new LinkedHashMap<>();
    Profile[] profileEnum = Profile.values();
    profileMap.put(0, "");
    for (Profile profile : profileEnum) {
      profileMap.put(profile.getKey(), profile.getValue());
    }
    return profileMap;
  }

  public Integer getKey() {
    return this.key;
  }

  public String getValue() {
    return this.value;
  }

}
