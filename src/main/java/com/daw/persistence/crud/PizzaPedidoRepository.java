package com.daw.persistence.crud;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.daw.persistence.entities.PizzaPedido;

@Repository
public interface PizzaPedidoRepository extends ListCrudRepository<PizzaPedido, Integer> {

	List<PizzaPedido> findByIdPedido(int idPedido);
	
}
