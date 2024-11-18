package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.PedidoRepository;
import com.daw.persistence.entities.Pedido;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public List<Pedido> findAll(){
		return this.pedidoRepository.findAll();
	}
	
	public Optional<Pedido> findPedido(int idPedido) {
		return this.pedidoRepository.findById(idPedido);
	}
	
	public Pedido save (Pedido pedido) {
		return this.pedidoRepository.save(pedido);
	}
	
	public Pedido create (Pedido pedido) {
		return this.save(pedido);
	}
	
	public boolean delete (int idPedido) {
		boolean result = false;
		if(this.findPedido(idPedido).isPresent()) {
			this.pedidoRepository.deleteById(idPedido);
			result = true;
			return result;
		}
		return result;
	}
	
	
}
