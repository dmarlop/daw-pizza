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

import com.daw.persistence.entities.Pedido;
import com.daw.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	public PedidoService pedidoService;
	
	@GetMapping
	public ResponseEntity<List<Pedido>> getAll(){
		return ResponseEntity.ok(pedidoService.findAll());
	}
	
	@GetMapping("/{idPedido}")
	public ResponseEntity<Pedido> getPedido(@PathVariable("/idPedido") int idPedido){
		Optional<Pedido> pedido = this.pedidoService.findPedido(idPedido);
		if(pedido.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(pedido.get());
		}
		
	
		
	}
	
	@PostMapping
	public ResponseEntity<Pedido> pedido(@RequestBody Pedido pedido){
		return new ResponseEntity<Pedido>(this.pedidoService.create(pedido), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idPedido}")
	public ResponseEntity<Pedido> pedido(@PathVariable("idPedido") int idPedido, @RequestBody Pedido pedido){
		if(idPedido != pedido.getId()) {
			return ResponseEntity.notFound().build();
		} else if(this.pedidoService.findPedido(idPedido).isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(this.pedidoService.save(pedido));
		}
		
	}
	
	
	@DeleteMapping("/{idPedido}")
	public ResponseEntity<Pedido> delete(@PathVariable("idPedido") int idPedido){
		if(this.pedidoService.delete(idPedido)) {
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
	
	
}
