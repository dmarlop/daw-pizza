package com.daw.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.PedidoRepository;
import com.daw.persistence.entities.Pedido;
import com.daw.services.dto.PedidoDTO;
import com.daw.services.mappers.PedidoMapper;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public List<PedidoDTO> findAll(){
		List<PedidoDTO> pedidosDTO = new ArrayList<PedidoDTO>();
		
		for(Pedido p : this.pedidoRepository.findAll()) {
			pedidosDTO.add(PedidoMapper.toDto(p));
		}
		
		return pedidosDTO;
	}
	
	public PedidoDTO findById(int idPedido) {
		return PedidoMapper.toDto(this.pedidoRepository.findById(idPedido).get()) ;
	}
	
	public Pedido save (Pedido pedido) {
		return this.pedidoRepository.save(pedido);
	}
	
	public Pedido create (Pedido pedido) {
		return this.save(pedido);
	}
	/*
	public boolean delete (int idPedido) {
		boolean result = false;
		if(this.findPedido(idPedido).isPresent()) {
			this.pedidoRepository.deleteById(idPedido);
			result = true;
			return result;
		}
		return result;
	}
	*/
	public boolean existsPedido(int idPedido) {
		return this.pedidoRepository.existsById(idPedido);
	}
	
	
}
