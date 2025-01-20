package com.daw.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.daw.services.ClienteService;
import com.daw.services.PedidoService;
import com.daw.services.PizzaPedidoService;
import com.daw.services.dto.PedidoDTO;
import com.daw.services.dto.PizzaPedidoInputDTO;
import com.daw.services.dto.PizzaPedidoOutputDTO;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	public PedidoService pedidoService;
	
	@Autowired
	public PizzaPedidoService pizzaPedidoService;
	
	@Autowired
	public ClienteService clienteService;
	
	//CRUDs de Pedido
	
	@GetMapping
	public ResponseEntity<List<PedidoDTO>> getAll(){
		return ResponseEntity.ok(pedidoService.findAll());
	}
	
	@GetMapping("/{idPedido}")
	public ResponseEntity<PedidoDTO> getPedido(@PathVariable("idPedido") int idPedido){
		if(this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.ok(this.pedidoService.findById(idPedido));
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	public ResponseEntity<PedidoDTO> create(@RequestBody Pedido pedido){
		if(!this.clienteService.existsCliente(pedido.getIdCliente())) {
			return ResponseEntity.notFound().build();
		}
		return new ResponseEntity<PedidoDTO>(this.pedidoService.create(pedido), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idPedido}")
	public ResponseEntity<Pedido> pedido(@PathVariable("idPedido") int idPedido, @RequestBody Pedido pedido){
		if(idPedido != pedido.getId()) {
			return ResponseEntity.notFound().build();
		} else if(!this.pedidoService.existsPedido(idPedido)) {
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
	
	//CRUDs de PizzaPedido
	
	//Este método devuelve todas las pizzas de un pedido.
	
	@GetMapping("/{idPedido}/pizzas")
	public ResponseEntity<List<PizzaPedidoOutputDTO>> listPizzas(@PathVariable int idPedido){
		return ResponseEntity.ok(this.pizzaPedidoService.findByIdPedido(idPedido));

	}
	
	
	@GetMapping("/{idPedido}/pizzas/{idPizzaPedido}")
	/*Necesitamos 2 parámetros. el numero del pedido, y el número del pizzaPedido*/
	public ResponseEntity<PizzaPedidoOutputDTO> findByIdPizza(@PathVariable int idPedido, @PathVariable int idPizzaPedido){
		/*Si no existe el pedido, not found*/
		if(!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		/*Si no existe el pizza Pedido, not found*/
		if(!this.pizzaPedidoService.existsPizzaPedido(idPizzaPedido)) {
			return ResponseEntity.notFound().build();
		}
		/*llamamos a buscar findPizzaPedidoDTO (porque lo que queremos hacer es devolverlo ya siendo DTO)*/
		return ResponseEntity.ok(this.pizzaPedidoService.findPizzaPedidoDTO(idPizzaPedido));
	}
	
	@PostMapping("/{idPedido}/pizzas")
	public ResponseEntity<PizzaPedidoOutputDTO> addPizza(@PathVariable int idPedido, @RequestBody PizzaPedidoInputDTO pizzaPedido){
		if(!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		if(!this.pizzaPedidoService.existsPizzaPedido(pizzaPedido.getIdPizza())) {
			return ResponseEntity.notFound().build();
		}
		
		return new ResponseEntity<PizzaPedidoOutputDTO>(this.pedidoService.addModPizza(pizzaPedido), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idPedido}/pizzas")
	public ResponseEntity<PizzaPedidoOutputDTO> modPizza(@PathVariable int idPedido, @RequestBody PizzaPedidoInputDTO pizzaPedido){
		if(!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		if(!this.pizzaPedidoService.existsPizzaPedido(pizzaPedido.getIdPizza())) {
			return ResponseEntity.notFound().build();
		}
		
		return new ResponseEntity<PizzaPedidoOutputDTO>(this.pedidoService.addModPizza(pizzaPedido), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idPedido}/pizzas/{idPizzaPedido}")
	public ResponseEntity<PizzaPedidoOutputDTO> deletePizza(@PathVariable int idPedido, @PathVariable int idPizzaPedido){
		if(!this.pedidoService.existsPedido(idPedido)) {
			return ResponseEntity.notFound().build();
		}
		if(!this.pizzaPedidoService.existsPizzaPedido(idPizzaPedido)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.pedidoService.deletePizza(idPedido, idPizzaPedido));
	}
	
	@GetMapping("/llevar")
	public ResponseEntity<List<Pedido>> findByLlevar(){
		String metodo = "L";
		return ResponseEntity.ok(this.pedidoService.findByMetodo(metodo));
	}
	
	@GetMapping("/consumirLocal")
	public ResponseEntity<List<Pedido>> findByConsumirLocal(){
		String metodo = "C";
		return ResponseEntity.ok(this.pedidoService.findByMetodo(metodo));
	}
	

	@GetMapping("/domicilio")
	public ResponseEntity<List<Pedido>> findByDomicilio(){
		String metodo = "D";
		return ResponseEntity.ok(this.pedidoService.findByMetodo(metodo));
	}
	
	
	@GetMapping("/hoy")
	public ResponseEntity<List<Pedido>> findByHoy(){
		LocalDateTime hoy = LocalDateTime.now();
		return ResponseEntity.ok(this.pedidoService.findByHoy(hoy));
	}
	
	@GetMapping("/pedidos/{idCliente}")
	public ResponseEntity<List<Pedido>> findByCliente (@PathVariable int idCliente){
		if(!this.clienteService.existsCliente(idCliente)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.pedidoService.findByCliente(idCliente));
	}
	
	
}
