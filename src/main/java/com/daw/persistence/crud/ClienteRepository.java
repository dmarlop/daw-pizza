package com.daw.persistence.crud;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Cliente;

public interface ClienteRepository extends ListCrudRepository<Cliente, Integer> {

}
