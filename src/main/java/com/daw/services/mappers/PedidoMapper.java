package com.daw.services.mappers;

import com.daw.persistence.entities.Pedido;
import com.daw.services.dto.PedidoDTO;

public class PedidoMapper {
	
	public static PedidoDTO toDto (Pedido pedido) {
		PedidoDTO dto = new PedidoDTO();
		
		dto.setCliente(pedido.getCliente().getNombre());
		dto.setDireccion(pedido.getCliente().getDireccion());
		dto.setFecha(pedido.getFecha());
		dto.setId(pedido.getId());
		dto.setMetodo(pedido.getMetodo());
		dto.setNotas(pedido.getNotas());
		//dto.setPizzas(pedido.getPizzaPedido().getFirst());
		dto.setTelefono(pedido.getCliente().getTelefono());
		dto.setTotal(pedido.getTotal());
		
		return dto;
		
	}

}
