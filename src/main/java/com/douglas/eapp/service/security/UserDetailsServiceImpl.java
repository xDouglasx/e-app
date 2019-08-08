package com.douglas.eapp.service.security;

import com.douglas.eapp.domain.Client;
import com.douglas.eapp.repository.ClientRepository;
import com.douglas.eapp.security.UserSS;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public UserDetails loadUserByUsername(String email){
    Client client = clientRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

    return new UserSS(client.getId(), client.getEmail(), client.getPassword(),
        client.getProfile().stream().map(p -> new SimpleGrantedAuthority(p.getValue())).collect(Collectors.toSet()));
  }
}
