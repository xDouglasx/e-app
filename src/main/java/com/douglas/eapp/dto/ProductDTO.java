package com.douglas.eapp.dto;

import com.douglas.eapp.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

  private Integer id;
  private String name;
  private double price;

  public ProductDTO(Product product){
    this.id = product.getId();
    this.name = product.getName();
    this.price = product.getPrice();
  }
}
