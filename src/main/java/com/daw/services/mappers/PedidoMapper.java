package com.daw.services.mappers;

import java.util.ArrayList;

import com.daw.persistence.entities.Pedido;
import com.daw.persistence.entities.PizzaPedido;
import com.daw.services.dto.PedidoDTO;
import com.daw.services.dto.PizzaPedidoOutputDTO;

public class PedidoMapper {
	
	public static PedidoDTO toDto (Pedido pedido) {
		PedidoDTO dto = new PedidoDTO();
		
		dto.setCliente(pedido.getCliente().getNombre());
		dto.setDireccion(pedido.getCliente().getDireccion());
		dto.setFecha(pedido.getFecha());
		dto.setId(pedido.getId());
		dto.setMetodo(pedido.getMetodo());
		dto.setNotas(pedido.getNotas());		
		dto.setTelefono(pedido.getCliente().getTelefono());
		dto.setTotal(pedido.getTotal());
		dto.setPizzas(new ArrayList<PizzaPedidoOutputDTO>());
		
		//Esto lo que hace es recorrer el PizzaPedido de pedido. A cada elemento, lo cogemos.
		//Luego, cogemos ese valor (pp) y lo transformamos en un PizzaPedidoOutputDTO con el mapper
		//Lo añadimos a la lista de pizzas de pedido.
		for(PizzaPedido pp : pedido.getPizzaPedido()) {
			dto.getPizzas().add(PizzaPedidoMapper.toDto(pp));
		}
		
		return dto;

	}
	
	

}
