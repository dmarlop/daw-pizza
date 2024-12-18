package com.daw.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.PizzaPedidoRepository;
import com.daw.persistence.entities.PizzaPedido;
import com.daw.services.dto.PizzaPedidoOutputDTO;
import com.daw.services.mappers.PizzaPedidoMapper;

@Service
public class PizzaPedidoService {
	
	@Autowired
	private PizzaPedidoRepository pizzaPedidoRepository;
	
	public List<PizzaPedido> findAll(){
		return this.pizzaPedidoRepository.findAll();
	}
	
	public Optional<PizzaPedido> findPizzaPedido(int idPizzaPedido){
		return this.pizzaPedidoRepository.findById(idPizzaPedido);
	}
	
	public PizzaPedido save (PizzaPedido pizzaPedido) {
		return this.pizzaPedidoRepository.save(pizzaPedido);
	}
	
	public PizzaPedido create (PizzaPedido pizzaPedido) {
		return this.save(pizzaPedido);
	}
	 public boolean delete(int PizzaPedido) {
		 boolean result = false;
		 if(this.findPizzaPedido(PizzaPedido).isPresent()) {
			 this.pizzaPedidoRepository.deleteById(PizzaPedido);
			 result = true;
			 return result;
		 }
		 return result;
	 }
	
	 //CRUDS pizzaPedidoDTO
	 
	 public List<PizzaPedidoOutputDTO> findByIdPedido (int idPedido){
		 List<PizzaPedidoOutputDTO> pizzaPedidoDTO =  new ArrayList<PizzaPedidoOutputDTO>();
		 
		 for(PizzaPedido pp : this.pizzaPedidoRepository.findByIdPedido(idPedido)) {
		  pizzaPedidoDTO.add(PizzaPedidoMapper.toDto(pp));
		 }
		 
		 return pizzaPedidoDTO;
	 }
	 
	 public PizzaPedidoOutputDTO findByIdPizza (int idPizzaPedido) {
		 PizzaPedidoOutputDTO dto = null;
		 if(pizzaPedidoRepository.existsById(idPizzaPedido)) {
			 dto = PizzaPedidoMapper.toDto(pizzaPedidoRepository.findByIdPizzaPedido(idPizzaPedido));
		 }
		 return dto;
		 
	 }
	 
	 
	 
	 
}
