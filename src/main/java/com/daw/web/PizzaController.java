package com.daw.web;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Pizza;
import com.daw.services.PizzaService;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {
	
	@Autowired
	public PizzaService pizzaService;
	
	@GetMapping
	public ResponseEntity<List<Pizza>> getAll(){
		return ResponseEntity.ok(this.pizzaService.findAll());
	}
	
	@GetMapping("/{idPizza}")
	public ResponseEntity<Pizza> findById(@PathVariable  int idPizza){
		if(this.pizzaService.findById(idPizza).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(this.pizzaService.findById(idPizza).get());
	}
	
	@PostMapping
	public ResponseEntity<Pizza> create (@RequestBody Pizza pizza){
		
		return new ResponseEntity<Pizza> (this.pizzaService.create(pizza), HttpStatus.CREATED);
	}
	
	@PutMapping("/{idPizza}")
	public ResponseEntity<Pizza> update (@PathVariable int idPizza, @RequestBody Pizza pizza){
		if(idPizza != pizza.getId()) {
		
			return ResponseEntity.badRequest().build();
		}
		
		if(!this.pizzaService.exists(idPizza)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(this.pizzaService.save(pizza));
	}
	
	@DeleteMapping("/{idPizza}")
	public ResponseEntity<Pizza> delete (@PathVariable int idPizza){
		if(this.pizzaService.deleteById(idPizza)) {
			return new ResponseEntity<Pizza>(HttpStatus.OK);
		}
		else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@GetMapping("/ordenadas")
	public ResponseEntity<List<Pizza>> getPizzasOrdenadas(){
		return ResponseEntity.ok(this.pizzaService.disponiblesPrecio());
	}
	
	@GetMapping("/nombre")
	public ResponseEntity<List<Pizza>> getPizzasNombre(@RequestParam String nombre){
		return ResponseEntity.ok(this.pizzaService.disponiblesNombre(nombre));
	}
	
	@GetMapping("/ingrediente")
	public ResponseEntity<List <Pizza>> getPizzasIngrediente(@RequestParam String ingrediente){
		return ResponseEntity.ok(this.pizzaService.contieneIngrediente(ingrediente));
	}
	@GetMapping("/SinIngrediente")
	public ResponseEntity<List <Pizza>> getPizzasSinIngrediente(@RequestParam String ingrediente){
		return ResponseEntity.ok(this.pizzaService.noContieneIngrediente(ingrediente));
	}
	
}
