package com.daw.services;

import java.util.List;

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

}
