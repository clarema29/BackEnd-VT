package com.visiontecnologica.repository;

import com.visiontecnologica.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
    UserDetails findByEmail(String email);
}
