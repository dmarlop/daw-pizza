package com.daw.services;

import java.util.List;

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
}
