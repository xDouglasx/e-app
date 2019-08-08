package com.douglas.eapp.controller;

import com.douglas.eapp.security.JWTUtil;
import com.douglas.eapp.service.UserService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

  @Autowired
  private JWTUtil jwtUtil;

  @RequestMapping(value = "/refresh_token")
  public ResponseEntity<Void> refreshToken(HttpServletResponse response){
    response.addHeader("Authorization", "Bearer " + jwtUtil.generateToken(UserService.authenticated().getUsername()));
    return ResponseEntity.noContent().build();
  }

}
