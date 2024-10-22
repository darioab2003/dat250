/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.dat152.rest.ws.exceptions.OrderNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.UnauthorizedOrderActionException;
import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.service.UserService;

/**
 * @author tdoy
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
	@GetMapping("/users")
	public ResponseEntity<Object> getUsers(){
		
		List<User> users = userService.findAllUsers();
		
		if(users.isEmpty())
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<Object> getUser(@PathVariable("id") Long id) throws UserNotFoundException, OrderNotFoundException{
		
		User user = userService.findUser(id);
		
		return new ResponseEntity<>(user, HttpStatus.OK);	
		
	}
	
	// TODO - createUser (@Mappings, URI=/users, and method)
	@PreAuthorize("hasAuthority('SUPER_ADMIN')")
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
	    User createdUser = userService.saveUser(user);
	    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	// TODO - updateUser (@Mappings, URI, and method)
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
	    try {
	        User updatedUser = userService.updateUser(user, id);
	        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	    } catch (UserNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	
	// TODO - deleteUser (@Mappings, URI, and method)
	@PreAuthorize("hasAuthority('SUPER_ADMIN')")
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) throws UserNotFoundException {
	    try {
	        userService.deleteUser(id);
	        return new ResponseEntity<>(HttpStatus.OK); // Devuelve 200 OK en lugar de 204 No Content
	    } catch (UserNotFoundException e) {
	        throw new UserNotFoundException("User not found with id: " + id); // Lanzar excepción si no se encuentra el usuario
	    } catch (Exception e) {
	        // Lanza un error interno del servidor (500) si ocurre cualquier otra excepción
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
		

	// TODO - getUserOrders (@Mappings, URI=/users/{id}/orders, and method)
	@GetMapping("/users/{id}/orders")
	public ResponseEntity<Set<Order>> getUserOrders(@PathVariable("id") Long id) {
	    try {
	        Set<Order> orders = userService.getUserOrders(id);
	        return new ResponseEntity<>(orders, HttpStatus.OK);
	    } catch (UserNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	// TODO - getUserOrder (@Mappings, URI=/users/{uid}/orders/{oid}, and method)
	
	@GetMapping("/users/{userid}/orders/{oid}")
	public ResponseEntity<Order> getUserOrder(@PathVariable("userid") Long userid, @PathVariable("oid") Long oid) {
	    try {
	        Order order = userService.getUserOrder(userid, oid);
	        return new ResponseEntity<>(order, HttpStatus.OK); // Devuelve 200 si se encuentra la orden
	    } catch (UserNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Devuelve 404 si el usuario no existe
	    } catch (OrderNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Devuelve 404 si la orden no existe
	    }
	}

	// TODO - deleteUserOrder (@Mappings, URI, and method)
	// UserController.java
	@PreAuthorize("hasAuthority('USER')")
	@DeleteMapping("/users/{userid}/orders/{oid}")
	public ResponseEntity<Void> deleteUserOrder(@PathVariable("userid") Long userid, @PathVariable("oid") Long oid) {
	    
        userService.deleteOrderForUser(oid); // Llamamos al método de eliminación
        return new ResponseEntity<>(HttpStatus.OK); // Si todo fue exitoso, devolvemos 204
	    
	}

	
	@PreAuthorize("hasAuthority('SUPER_ADMIN')")
	@PostMapping("/users/{userid}/orders")
	  public ResponseEntity<EntityModel<Map<String, Object>>> createUserOrder(
	            @PathVariable("id") Long userid, @RequestBody Order order) throws UserNotFoundException, OrderNotFoundException {
	        try {
	            // Crear la orden para el usuario
	            Order newOrder = userService.createOrdersForUser(userid, order);

	            // Crear una lista con el ISBN de la orden
	            List<String> isbns = List.of(newOrder.getIsbn());

	            // Crear un mapa para envolver la orden y el ISBN
	            Map<String, Object> responseMap = new HashMap<>();
	            responseMap.put("isbn", isbns);
	            responseMap.put("order", newOrder);

	            // Crear enlaces HATEOAS
	            Link userLink = linkTo(methodOn(UserController.class).getUser(userid)).withRel("user");
	            Link allOrdersLink = linkTo(methodOn(UserController.class).getUserOrders(userid)).withRel("allOrders");

	            // Envolver el mapa en un EntityModel con los enlaces HATEOAS
	            EntityModel<Map<String, Object>> resource = EntityModel.of(responseMap);
	            resource.add(userLink);
	            resource.add(allOrdersLink);

	            return new ResponseEntity<>(resource, HttpStatus.CREATED);
	        } catch (UserNotFoundException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 si no se encuentra el usuario
	        }
	    }
	
}
