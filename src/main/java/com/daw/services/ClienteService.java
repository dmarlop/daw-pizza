package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.ClienteRepository;
import com.daw.persistence.entities.Cliente;


@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	public List<Cliente> findAll(){
		
		return this.clienteRepository.findAll();
	}
	public Optional<Cliente> findCliente (int idCliente){
		return this.clienteRepository.findById(idCliente);
	}
	
	public Cliente save (Cliente cliente){
		
		return this.clienteRepository.save(cliente);
	}
	
	public Cliente create (Cliente cliente){
		
		return this.clienteRepository.save(cliente);
	}
	
	public boolean delete (int idCliente) {
		boolean result = false;
		
		if(this.findCliente(idCliente).isPresent()) {
			this.clienteRepository.deleteById(idCliente);
			result = true;
		}
		return result;
	}

}
