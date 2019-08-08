package com.douglas.eapp.controller;

import com.douglas.eapp.domain.CustomerOrder;
import com.douglas.eapp.service.OrderService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<CustomerOrder> getOrder(@PathVariable Integer id) {
    return ResponseEntity.ok(orderService.findCustomerOrder(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CustomerOrder> insert(@Valid @RequestBody CustomerOrder customerOrder) {
    return ResponseEntity.ok(orderService.insert(customerOrder));
  }

  @GetMapping
  public ResponseEntity<Page<CustomerOrder>> findPage(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerpage,
      @RequestParam(value = "orderBy", defaultValue = "date") String orderBy,
      @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
    return ResponseEntity.ok().body(orderService.findPage(page, linesPerpage, orderBy, direction));
  }
}
