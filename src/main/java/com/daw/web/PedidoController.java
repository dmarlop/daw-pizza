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
import com.daw.services.PizzaPedidoService;
import com.daw.services.dto.PedidoDTO;
import com.daw.services.dto.PizzaPedidoOutputDTO;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	public PedidoService pedidoService;
	
	@Autowired
	public PizzaPedidoService pizzaPedidoService;
	
	//CRUDs de Pedido
	
	@GetMapping
	public ResponseEntity<List<PedidoDTO>> getAll(){
		return ResponseEntity.ok(pedidoService.findAll());
	}
	
	@GetMapping("/{idPedido}")
	public ResponseEntity<PedidoDTO> getPedido(@PathVariable("/idPedido") int idPedido){
		if(this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.ok(this.pedidoService.findById(idPedido));
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	public ResponseEntity<Pedido> pedido(@RequestBody Pedido pedido){
		return new ResponseEntity<Pedido>(this.pedidoService.create(pedido), HttpStatus.CREATED);
	}
	/*
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
*/	
	/*
	@DeleteMapping("/{idPedido}")
	public ResponseEntity<Pedido> delete(@PathVariable("idPedido") int idPedido){
		if(this.pedidoService.delete(idPedido)) {
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
		
	}
	*/
	//CRUDs de PizzaPedido
	
	@GetMapping("/{idPedido}/pizzas")
	public ResponseEntity<List<PizzaPedidoOutputDTO>> listPizzas(@PathVariable int idPedido){
		return ResponseEntity.ok(this.pizzaPedidoService.findByIdPedido(idPedido));
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
