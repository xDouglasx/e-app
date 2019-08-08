package com.douglas.eapp;

import com.douglas.eapp.domain.Address;
import com.douglas.eapp.domain.CardPayment;
import com.douglas.eapp.domain.Category;
import com.douglas.eapp.domain.City;
import com.douglas.eapp.domain.Client;
import com.douglas.eapp.domain.CustomerOrder;
import com.douglas.eapp.domain.OrderItem;
import com.douglas.eapp.domain.Payment;
import com.douglas.eapp.domain.Product;
import com.douglas.eapp.domain.State;
import com.douglas.eapp.domain.TicketPayment;
import com.douglas.eapp.domain.enums.ClientType;
import com.douglas.eapp.domain.enums.PaymentStatus;
import com.douglas.eapp.domain.enums.Profile;
import com.douglas.eapp.repository.AddressRepository;
import com.douglas.eapp.repository.CategoryRepository;
import com.douglas.eapp.repository.CityRepository;
import com.douglas.eapp.repository.ClientRepository;
import com.douglas.eapp.repository.OrderItemRepository;
import com.douglas.eapp.repository.OrderRepository;
import com.douglas.eapp.repository.PaymentRepository;
import com.douglas.eapp.repository.ProductRepository;
import com.douglas.eapp.repository.StateRepository;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EAppApplication implements CommandLineRunner {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private StateRepository stateRepository;

  @Autowired
  private CityRepository cityRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  public static void main(String[] args) {
    SpringApplication.run(EAppApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    Category eletronics = new Category("Eletronics");
    Category office = new Category("Office");
    Category test = new Category("test");
    Category test2 = new Category("test2");
    Category test3 = new Category("test3");
    Category test4 = new Category("test4");
    Category test5 = new Category("test5");
    Category test6 = new Category("test6");

    Product computer = new Product("Computer", 2000.00);
    Product mouse = new Product("Mouse", 80.00);
    Product printer = new Product("Pinter", 800.00);
    Product couch = new Product("Couch", 2000.00);
    Product table = new Product("Table", 80.00);
    Product candle = new Product("Candle", 800.00);
    Product tv = new Product("Tv", 2000.00);
    Product microwave = new Product("Microwave", 80.00);
    Product ps4 = new Product("Ps4", 800.00);

    eletronics.getProducts().addAll(Arrays.asList(computer, mouse, printer));
    office.getProducts().addAll(Arrays.asList(printer, couch));
    test.getProducts().addAll(Arrays.asList(table, candle));
    test2.getProducts().addAll(Arrays.asList(tv, microwave, ps4));

    computer.getCategories().addAll(Collections.singletonList(eletronics));
    mouse.getCategories().addAll(Collections.singletonList(eletronics));
    printer.getCategories().addAll(Arrays.asList(eletronics, office));
    couch.getCategories().addAll(Collections.singletonList(office));
    table.getCategories().addAll(Collections.singletonList(test));
    candle.getCategories().addAll(Collections.singletonList(test));
    tv.getCategories().addAll(Collections.singletonList(test2));
    microwave.getCategories().addAll(Collections.singletonList(test2));
    ps4.getCategories().addAll(Collections.singletonList(test2));

    State saoPaulo = new State("Sao Paulo");
    State minasGerais = new State("Minas Gerais");

    City uberlandia = new City("Uberlandia", minasGerais);
    City campinas = new City("Campinas", saoPaulo);
    City diadema = new City("Diadema", saoPaulo);

    categoryRepository.saveAll(Arrays.asList(eletronics, office, test, test2, test3, test4, test5, test6));
    productRepository.saveAll(Arrays.asList(computer, mouse, printer, couch, table, candle, tv, microwave, ps4));
    stateRepository.saveAll(Arrays.asList(saoPaulo, minasGerais));
    cityRepository.saveAll(Arrays.asList(uberlandia, diadema, campinas));

    Client cli1 = new Client("Maria Silva", "maria@gmail.com", "36378912377", ClientType.PERSON, passwordEncoder.encode("vrau"));
    cli1.getPhones().addAll(Arrays.asList("27363323", "93838393"));

    Address address1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, diadema);
    Address address2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, uberlandia);

    cli1.getAddresses().addAll(Arrays.asList(address1, address2));

    Client cli2 = new Client("Ana", "ana@gmail.com", "41962657825", ClientType.PERSON, passwordEncoder.encode("vrau"));
    cli2.addProfile(Profile.ROLE_ADMIN);
    cli2.getPhones().addAll(Arrays.asList("27363323", "93838393"));
    cli2.getAddresses().addAll(Arrays.asList(address1, address2));

    clientRepository.saveAll(Arrays.asList(cli1, cli2));
    addressRepository.saveAll(Arrays.asList(address1, address2));

    saoPaulo.getCities().addAll(Arrays.asList(campinas, diadema));
    minasGerais.getCities().addAll(Collections.singletonList(uberlandia));

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    CustomerOrder customerOrder1 = new CustomerOrder(sdf.parse("30/09/2017 10:32"), cli1, address1);
    CustomerOrder customerOrder2 = new CustomerOrder(sdf.parse("10/10/2017 19:35"), cli1, address2);

    Payment payment1 = new CardPayment(PaymentStatus.PAID, customerOrder1, 6);
    customerOrder1.setPayment(payment1);

    Payment payment2 = new TicketPayment(PaymentStatus.PENDING, customerOrder2, sdf.parse("20/10/2017 00:00"), null);
    customerOrder2.setPayment(payment2);

    cli1.getCustomerOrders().addAll(Arrays.asList(customerOrder1, customerOrder2));

    orderRepository.saveAll(Arrays.asList(customerOrder1, customerOrder2));
    paymentRepository.saveAll(Arrays.asList(payment1, payment2));

    OrderItem item1 = new OrderItem(computer, customerOrder1, 0.00, 1, 2000.00);
    OrderItem item2 = new OrderItem(mouse, customerOrder1, 0.00, 2, 80.00);
    OrderItem item3 = new OrderItem(printer, customerOrder2, 100.00, 1, 800.00);

    printer.getOrderItems().addAll(Collections.singletonList(item1));
    computer.getOrderItems().addAll(Collections.singletonList(item3));
    mouse.getOrderItems().addAll(Collections.singletonList(item2));

    customerOrder1.getOrderItems().addAll(Arrays.asList(item1, item2));
    customerOrder2.getOrderItems().addAll(Collections.singletonList(item3));

    orderItemRepository.saveAll(Arrays.asList(item1, item2, item3));

  }
}
