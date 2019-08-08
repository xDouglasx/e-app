package com.douglas.eapp.repository;

import com.douglas.eapp.domain.Client;
import com.douglas.eapp.domain.CustomerOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Integer> {

  @Transactional(readOnly = true)
  Page<CustomerOrder> findByClient(Client client, Pageable page);
}
