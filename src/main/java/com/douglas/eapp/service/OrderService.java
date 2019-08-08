package com.douglas.eapp.service;

import com.douglas.eapp.domain.Category;
import com.douglas.eapp.domain.Client;
import com.douglas.eapp.domain.CustomerOrder;
import com.douglas.eapp.domain.TicketPayment;
import com.douglas.eapp.domain.enums.PaymentStatus;
import com.douglas.eapp.exception.AuthorizationException;
import com.douglas.eapp.exception.ObjectNotFoundException;
import com.douglas.eapp.repository.ClientRepository;
import com.douglas.eapp.repository.OrderItemRepository;
import com.douglas.eapp.repository.OrderRepository;
import com.douglas.eapp.repository.PaymentRepository;
import com.douglas.eapp.repository.ProductRepository;
import com.douglas.eapp.security.UserSS;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private TicketPaymentService ticketPaymentService;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private ClientRepository clientRepository;

  public CustomerOrder findCustomerOrder(Integer id) {
    return orderRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Customer with id: " + id + " not found."));
  }

  public CustomerOrder insert(CustomerOrder customerOrder) {
    customerOrder.setDate(new Date());
    customerOrder.getPayment().setPaymentStatus(PaymentStatus.PENDING);
    customerOrder.getPayment().setCustomerOrder(customerOrder);
    if (customerOrder.getPayment() instanceof TicketPayment) {
      TicketPayment ticketPayment = (TicketPayment) customerOrder.getPayment();
      ticketPayment.setExpireDate(ticketPaymentService.getExpireDate(customerOrder.getDate()));
    }
    orderRepository.save(customerOrder);
    paymentRepository.save(customerOrder.getPayment());
    customerOrder.getOrderItems().forEach(item -> {
      item.setDiscount(0.0);
      item.setPrice(productRepository.
          findById(item.getProduct().getId())
          .orElseThrow(
              () -> new ObjectNotFoundException("Product with id: " + item.getProduct().getId() + " not found."))
          .getPrice());
      item.setCustomerOrder(customerOrder);
    });
    orderItemRepository.saveAll(customerOrder.getOrderItems());
    return customerOrder;
  }

  public Page<CustomerOrder> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

    UserSS user = UserService.authenticated();
    if (user == null) {
      throw new AuthorizationException("Access Denied");
    }
    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    Client client = clientRepository.findById(user.getId()).orElseThrow(() -> new ObjectNotFoundException(String.format(
        "Client with id : %s not found", user.getId()
    )));
    return orderRepository.findByClient(client, pageRequest);
  }
}
