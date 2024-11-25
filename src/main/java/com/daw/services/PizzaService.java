package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.daw.persistence.crud.PizzaRepository;
import com.daw.persistence.entities.Pizza;

@Service
public class PizzaService {
	
	
	private final PizzaRepository pizzaRepository;
	
	public PizzaService(PizzaRepository pizzaRepository) {
		this.pizzaRepository = pizzaRepository;
	}
	
	public List<Pizza> findAll(){
		return this.pizzaRepository.findAll();
	}
	
	public Optional<Pizza> findById(int idPizza){
		return this.pizzaRepository.findById(idPizza);
	}
	
	public Pizza save (Pizza pizza) {
		return this.pizzaRepository.save(pizza);
	}
	
	public Pizza create (Pizza pizza) {
		return this.pizzaRepository.save(pizza);
	}
	
	public boolean deleteById (int idPizza) {
		boolean result = false;
		
		if(this.pizzaRepository.existsById(idPizza)) {
			
			this.pizzaRepository.deleteById(idPizza);
			result = true;
		}
		
		return result;
				
	}
	
	public boolean exists(int idPizza) {
		if(this.pizzaRepository.existsById(idPizza)) {
			return true;
		}
			return false;
	}
	
	public List<Pizza> disponiblesPrecio(){
		return this.pizzaRepository.findByDisponibleOrderByPrecioAsc(true);
	}
	
	public List<Pizza> disponiblesNombre(String nombre){
		return pizzaRepository.findByNombreAndDisponibleTrue(nombre);
	}
	
	public List<Pizza> contieneIngrediente (String ingrediente){
		return pizzaRepository.findByDescripcionContaining(ingrediente);
	}
	
	public List<Pizza> noContieneIngrediente (String ingrediente){
		return pizzaRepository.findByDescripcionNotContaining(ingrediente);
	}
	
	public Pizza updatePrecio (int idPizza, double precio) {
		
		Pizza pizza = this.findById(idPizza).get();
			pizza.setPrecio(precio);
			pizza = this.pizzaRepository.save(pizza);
		return pizza;
		
	}
	
	public Pizza updateDisponible (int idPizza) {
		
		Pizza pizza = this.findById(idPizza).get();

		if(pizza.isDisponible()) {
			pizza.setDisponible(false);
		}else {
			pizza.setDisponible(true);
		}
		
		pizza = this.pizzaRepository.save(pizza);
	
		return pizza;
		
	}
	
	
	
}
