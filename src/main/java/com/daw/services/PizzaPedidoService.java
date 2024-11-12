package com.daw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.PizzaPedidoRepository;
import com.daw.persistence.entities.PizzaPedido;

@Service
public class PizzaPedidoService {
	
	@Autowired
	private PizzaPedidoRepository pizzaPedidoRepository;
	
	public List<PizzaPedido> findAll(){
		return this.pizzaPedidoRepository.findAll();
	}
	
}
