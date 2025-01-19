package com.daw.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.PizzaPedidoRepository;
import com.daw.persistence.entities.Pizza;
import com.daw.persistence.entities.PizzaPedido;
import com.daw.services.dto.PizzaPedidoOutputDTO;
import com.daw.services.dto.PizzaPedidoInputDTO;
import com.daw.services.mappers.PizzaPedidoMapper;

@Service
public class PizzaPedidoService {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private PizzaPedidoRepository pizzaPedidoRepository;
	
	public List<PizzaPedido> findAll(){
		return this.pizzaPedidoRepository.findAll();
	}
	
	public Optional<PizzaPedido> findPizzaPedido(int idPizzaPedido){
		return this.pizzaPedidoRepository.findById(idPizzaPedido);
	}
	
	//La lógica que queremos crear aquí es, poder meter un input, transformarlo en entity, y el entity en output.
	//La capa de abstracción mas compleja es entender que en un PizzaPedidoInputDTO vienen muchos datos
	//Esos datos, estarán en nuestra entidad. Por eso puede sorprender ver un "getIdPizza" si no hemos añadido nosotros dicho ID 
	//Pero idPizza está en el pizzaPedido (el body en el postman)
	public PizzaPedidoOutputDTO save(PizzaPedidoInputDTO inputDTO) {
		/*Creamos un pizza pedido, y lo igualamos al input transformado a entity*/
		PizzaPedido entity = PizzaPedidoMapper.toEntity(inputDTO);
		/*creamos una pizza, que sea igual a una pizza que exista en base de datos (viene en el ID de pizza, que viene en un
		 * cuerpo de pizzaPedido. la cantidad (que también viene) y el precio (Que también viene)
		 */
		Pizza pizza = this.pizzaService.findById(entity.getIdPizza()).get();
		entity.setPrecio(entity.getCantidad() * pizza.getPrecio());
		
		entity = this.pizzaPedidoRepository.save(entity);
		
		/*seteamos la pizza a la nueva que hemos creado*/
		entity.setPizza(pizza);		
		/*Convertirmos a DTO y devolvemos*/
		return PizzaPedidoMapper.toDto(entity);
	}
	
	public boolean existsPizzaPedido(int idPizzaPedido){
		return this.pizzaPedidoRepository.existsById(idPizzaPedido);
	}
	
	public PizzaPedidoOutputDTO create (PizzaPedidoInputDTO pizzaPedido) {
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
		/*Básicamente vamos a obtener todos los pizzaPedidos entregando el ID de un pedido*/
		 /*Creamos la lista de pizzaPedidoDTO*/
		 
		 List<PizzaPedidoOutputDTO> pizzaPedidoDTO =  new ArrayList<PizzaPedidoOutputDTO>();
		 
		 /*Por cada pedido (lo hemos encontrado con el findByIdPedido, lo convertimos a Dto con el mapper y lo añadimos a la lista*/
		 for(PizzaPedido pp : this.pizzaPedidoRepository.findByIdPedido(idPedido)) {
		  pizzaPedidoDTO.add(PizzaPedidoMapper.toDto(pp));
		 }
		 
		 /*Devolvemos el pizzaPedidoDTO*/
		 
		 return pizzaPedidoDTO;
	 }
	 
	 
	 
	 public PizzaPedidoOutputDTO findPizzaPedidoDTO(int idPizza) {
		 /*En este caso, lo hacemos 1 poco mas directo, buscamos con el findById, en este caso, de idPizza (en vez de idPedido)*/
			PizzaPedido pp = this.pizzaPedidoRepository.findById(idPizza).get();
			//Cambiamos el pizzaPedido (pp) a dto con el mapper y devolvemos.
			return PizzaPedidoMapper.toDto(pp);
		}
		
	 
	 
	 
	 
}
