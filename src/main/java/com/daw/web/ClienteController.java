package com.daw.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Cliente;
import com.daw.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	public ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> getAll(){
		return ResponseEntity.ok(clienteService.findAll());
	}
	
	@GetMapping("/{idCliente}")
	public ResponseEntity<Cliente> getCliente(@PathVariable("/idCliente") int idCliente){
		
		if(this.clienteService.findCliente(idCliente).isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(this.clienteService.findCliente(idCliente).get());
		}
		
	
		
	}
	
	@PostMapping
	public ResponseEntity<Cliente> cliente(@RequestBody Cliente cliente){
		return new ResponseEntity<Cliente>(this.clienteService.create(cliente), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idCliente}")
	public ResponseEntity<Cliente> cliente(@PathVariable("idCliente") int idCliente, @RequestBody Cliente cliente){
		if(idCliente != cliente.getId()) {
			return ResponseEntity.notFound().build();
		} else if(this.clienteService.findCliente(idCliente).isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(this.clienteService.save(cliente));
		}
		
	}
	
	
	@DeleteMapping("/{idCliente}")
	public ResponseEntity<Cliente> delete(@PathVariable("idCliente") int idCliente){
		if(this.clienteService.delete(idCliente)) {
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
}
