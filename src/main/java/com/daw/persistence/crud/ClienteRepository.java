package com.daw.persistence.crud;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Cliente;

public interface ClienteRepository extends ListCrudRepository<Cliente, Integer> {
	Optional<Cliente> findByTelefonoStartingWith(String telefono);
}
