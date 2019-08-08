package com.douglas.eapp.service;

import com.douglas.eapp.domain.Category;
import com.douglas.eapp.domain.Product;
import com.douglas.eapp.exception.ObjectNotFoundException;
import com.douglas.eapp.repository.CategoryRepository;
import com.douglas.eapp.repository.ProductRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  Logger AUDIT_LOGGER = LoggerFactory.getLogger("AUDIT");

  @Autowired
  ProductRepository productRepository;

  @Autowired
  CategoryRepository categoryRepository;

  public Product findCustomerProduct(Integer id) {
    AUDIT_LOGGER.info("logging info about product {}", id);
    Product customerProduct = productRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Customer with id: " + id + " not found."));
    AUDIT_LOGGER.info("Customer Product: {}", customerProduct);
    return customerProduct;
  }

  public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    List<Category> categories = categoryRepository.findAllById(ids);
    return productRepository.search(name, categories, pageRequest);

  }
  
}
