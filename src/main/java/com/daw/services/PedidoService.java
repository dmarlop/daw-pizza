package com.daw.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.crud.PedidoRepository;
import com.daw.persistence.entities.Pedido;
import com.daw.persistence.entities.PizzaPedido;
import com.daw.services.dto.PedidoDTO;
import com.daw.services.dto.PizzaPedidoOutputDTO;
import com.daw.services.dto.PizzaPedidoInputDTO;
import com.daw.services.mappers.PedidoMapper;
import com.daw.services.mappers.PizzaPedidoMapper;

@Service
public class PedidoService {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private PizzaPedidoService pizzaPedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public List<PedidoDTO> findAll(){
		List<PedidoDTO> pedidosDTO = new ArrayList<PedidoDTO>();
		
		for(Pedido p : this.pedidoRepository.findAll()) {
			pedidosDTO.add(PedidoMapper.toDto(p));
		}
		
		return pedidosDTO;
	}
	
	public PedidoDTO findById(int idPedido) {
		return PedidoMapper.toDto(this.pedidoRepository.findById(idPedido).get()) ;
	}
	
	public Pedido save (Pedido pedido) {
		return this.pedidoRepository.save(pedido);
	}
	
	public PedidoDTO create(Pedido pedido) {
		pedido.setFecha(LocalDateTime.now());
		pedido.setTotal(0.0);
		pedido.setCliente(this.clienteService.findCliente(pedido.getIdCliente()).get());
		pedido.setPizzaPedido(new ArrayList<PizzaPedido>());
		pedido = this.pedidoRepository.save(pedido);
		return PedidoMapper.toDto(pedido);
		
		
	}
	
	public Pedido update(Pedido pedido) {		
		return this.pedidoRepository.save(pedido);
	}
	
	public boolean delete (int idPedido) {
		boolean result = false;
		if(this.existsPedido(idPedido)) {
			this.pedidoRepository.deleteById(idPedido);
			result = true;
			return result;
		}
		return result;
	}
	
	public boolean existsPedido(int idPedido) {
		return this.pedidoRepository.existsById(idPedido);
	}
	
	
	public PedidoDTO verPizzas(int idPedido) {
		/*Creamos un pedido, lo mismo, lo buscamos de la base de datos*/
		Pedido pedido = this.pedidoRepository.findById(idPedido).get();
		/*Como en vez de 1 pedido puntual va a ser una lista, creamos la lista*/
		List<PizzaPedidoOutputDTO> list = new ArrayList<PizzaPedidoOutputDTO>();
		
		/*Cogemos todos los pizzaPedidos del pedido, y los añadimos a la lista (mapeandolos para que sean DTO's)*/
		for(PizzaPedido pizzaPedido : pedido.getPizzaPedido()) {
			list.add(PizzaPedidoMapper.toDto(pizzaPedido));
		}
		/*Pasamos el propio pedido a DTO*/
		PedidoDTO pedidoDto = PedidoMapper.toDto(pedido);
		/*añadimos la lista*/
		pedidoDto.setPizzas(list);
		/*Devolvemos*/
		return pedidoDto;
	}
	
	//Devolvemos PizzaPedidoOutput DTO porque es lo que nos interesa, un pizza pedido
	public PizzaPedidoOutputDTO verPizza(int idPedido, int idPizza) {
		//Creamos un pedido en java con el pedido encontrado en BBDD por el id.(Como es optional, añadimos get)
		
		Pedido pedido = this.pedidoRepository.findById(idPedido).get();
		/*Por cada pizzaPedido de ese pedido de bbdd, hacemos lo siguiente, si al coger la pizza(Depizzapedido) y
		hacer get ID, es igual al id proporcionado, lo que hacemos es que lo transformamos (con el mapper) a un PizzaPedidoDTO y lo devolvemos*/
		
		for(PizzaPedido pizzaPedido : pedido.getPizzaPedido()) {
			if(pizzaPedido.getPizza().getId()== idPizza) {
				return PizzaPedidoMapper.toDto(pizzaPedido);
				
			}
		}
		/*Error por si no lo encuentra*/
		 throw new IllegalArgumentException("Pizza no encontrada en el pedido con ID: " + idPedido);
	}
	
	
	public void actualizarPrecio(int idPedido) {
		/*Creamos un pedido trayéndonos 1 de base de datos por el id*/
		Pedido pedido = this.pedidoRepository.findById(idPedido).get();
		/*ponemos el valor a 0*/
		Double total = 0.0;
		
		/*cojo todos los pizza pedidos de pedido*/
		for(PizzaPedido pp : pedido.getPizzaPedido()) {
			/*el valor es la suma de todos los precios*/
			total += pp.getPrecio();
		}
		/*Seteamos el total, al nuevo total*/
		pedido.setTotal(total);
		
		/*Guardamos*/
		this.pedidoRepository.save(pedido);
	}
	
	
	public PizzaPedidoOutputDTO addModPizza(PizzaPedidoInputDTO inputDTO) {
		/*Creamos un outputDTO (el que vamos a devolver) que lo igualamos al pizza pedido input que hemos metido*/
		/*La lógica de poder añadir 1 input y devolver un output la gestionamos en el método save()*/
		PizzaPedidoOutputDTO outDTO = this.pizzaPedidoService.save(inputDTO);
		
		/*Recalculamos el precio*/
		this.actualizarPrecio(inputDTO.getIdPedido());
		
		return outDTO;	
	}
	
	public PizzaPedidoOutputDTO deletePizza(int idPedido, int idPizzaPedido) {
		//Creamos un pedido en java con el pedido encontrado en BBDD por el id.(Como es optional, añadimos get)
		
		Pedido pedido = this.pedidoRepository.findById(idPedido).get();
		
		//Por cada pedido, cogemos todos los pizza pedidos. Luego, de cada pizzaPedido, cogemos la pizza.
		//De dicha pizza, cogemos el ID, si coincide, entramos en el if
		//Cogemos la pizza, eliminamos la pizza por su id
		//Guardamos
		for(PizzaPedido pizzaPedido : pedido.getPizzaPedido()) {
			if(pizzaPedido.getId() == idPizzaPedido) {
				pedido.getPizzaPedido().remove(idPizzaPedido);
				this.pedidoRepository.save(pedido);
				return PizzaPedidoMapper.toDto(pizzaPedido);
				
			}
		}
		/*Error por si no lo encuentra*/
		 throw new IllegalArgumentException("Pizza no encontrada en el pedido con ID: " + idPedido);
	}
	
	public List<Pedido> findByMetodo (String metodo){
		return this.pedidoRepository.findByMetodo(metodo);
	}
	
	public List<Pedido> findByHoy (LocalDateTime fecha){
		return this.pedidoRepository.findByFecha(fecha);
	}
	
	public List<Pedido> findByCliente (int cliente){
		return this.pedidoRepository.findByIdCliente(cliente);
	}
	
	
}
