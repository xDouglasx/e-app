package com.douglas.eapp.controller.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UtilsURL {

  public static String decodeParam(String s) {
    try {
      return URLDecoder.decode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      return "";
    }
  }

  public static List<Integer> decodeIntList(String string) {
    return string.isEmpty() ? Collections.emptyList() : Arrays
        .stream(string.split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }
}
