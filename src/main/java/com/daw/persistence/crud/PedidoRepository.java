package com.daw.persistence.crud;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Pedido;
import com.daw.services.dto.PedidoDTO;
import com.daw.services.dto.PizzaPedidoOutputDTO;

public interface PedidoRepository extends ListCrudRepository<Pedido, Integer>{
	List<Pedido> findByMetodo (String metodo);
	List<Pedido> findByFecha (LocalDateTime metodo);
	List<Pedido> findByIdCliente (int idCliente);
}
