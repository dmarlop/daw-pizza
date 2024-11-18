package com.daw.persistence.crud;


import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Pizza;

public interface PizzaRepository extends ListCrudRepository<Pizza, Integer>{
	List<Pizza> findByDisponibleOrderByPrecioAsc(boolean disponible);
	List<Pizza> findByNombreAndDisponibleTrue(String nombre);
	List<Pizza> findByDescripcionContaining (String ingrediente);
	List<Pizza> findByDescripcionNotContaining (String ingrediente);
}
