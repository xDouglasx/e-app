package com.douglas.eapp.repository;

import com.douglas.eapp.domain.Client;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

  @Transactional(readOnly = true)
  Optional<Client> findByEmail(String email);
}
