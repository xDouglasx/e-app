package com.douglas.eapp.repository;


import com.douglas.eapp.domain.Category;
import com.douglas.eapp.domain.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

  @Query("SELECT DISTINCT product FROM Product product INNER JOIN product.categories cat "
      + "WHERE product.name LIKE %:name% AND cat IN :categories ")
  @Transactional(readOnly = true)
  Page<Product> search(@Param("name") String name, @Param("categories") List<Category> categories, Pageable pageRequest);
}
