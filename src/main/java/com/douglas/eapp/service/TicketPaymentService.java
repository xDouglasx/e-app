package com.douglas.eapp.service;

import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class TicketPaymentService {

  public Date getExpireDate(Date date){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, 7);
    return calendar.getTime();
  }
}
