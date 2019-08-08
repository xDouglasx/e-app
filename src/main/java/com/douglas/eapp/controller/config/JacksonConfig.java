package com.douglas.eapp.controller.config;

import com.douglas.eapp.domain.CardPayment;
import com.douglas.eapp.domain.TicketPayment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

  @Bean
  public Jackson2ObjectMapperBuilder objectMapperBuilder(){
    return new Jackson2ObjectMapperBuilder(){
      @Override
      public void configure(ObjectMapper objectMapper){
        objectMapper.registerSubtypes(CardPayment.class);
        objectMapper.registerSubtypes(TicketPayment.class);
        super.configure(objectMapper);
      }
    };
  }
}
