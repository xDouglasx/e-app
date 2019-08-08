package com.douglas.eapp.controller;

import com.douglas.eapp.controller.utils.UtilsURL;
import com.douglas.eapp.domain.Product;
import com.douglas.eapp.dto.ProductDTO;
import com.douglas.eapp.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/products")
@Controller
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
    return ResponseEntity.ok(productService.findCustomerProduct(id));
  }

  @GetMapping
  public ResponseEntity<Page<ProductDTO>> findPage(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerpage,
      @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
      @RequestParam(value = "direction", defaultValue = "ASC") String direction,
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "categories", defaultValue = "") String categories) {

    String decodedName = UtilsURL.decodeParam(name);
    List<Integer> ids = UtilsURL.decodeIntList(categories);
    Page<ProductDTO> productList = productService.search(decodedName, ids, page, linesPerpage, orderBy, direction)
        .map(ProductDTO::new);
    return ResponseEntity.ok().body(productList);
  }
}
