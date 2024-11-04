package com.daw.persistence.crud;


import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Pizza;

public interface PizzaRepository extends ListCrudRepository<Pizza, Integer>{

}
