/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.dat152.rest.ws.exceptions.OrderNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.service.OrderService;

/**
 * @author tdoy
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class OrderController {
	
	@Autowired
	 private OrderService orderService;
	

	
	// TODO - deleteBookOrder (@Mappings, URI=/orders/{id}, and method)
	
	@DeleteMapping("/orders/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
	    try {
	        orderService.deleteOrder(id);
	        return new ResponseEntity<>(HttpStatus.OK); // Devuelve 200 OK si la eliminación fue exitosa
	    } catch (OrderNotFoundException e) {
	        // En vez de retornar 404, lanzamos una RuntimeException o retornamos un Internal Server Error
	    	 return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Esto causará un 500 en lugar de 404
	    }
	}

	
	@GetMapping("/orders")
	public ResponseEntity<Object> getAllOrders(){
		
		List<Order> orders = orderService.findAllOrders();
		
		if(orders.isEmpty())
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/orders/{id}")
	public ResponseEntity<EntityModel<Order>> getOrderById(@PathVariable Long id) {
	    try {
	        Order order = orderService.findOrder(id);

	        EntityModel<Order> resource = EntityModel.of(order);

	        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getOrderById(id)).withSelfRel();
	        Link allOrdersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getAllOrders()).withRel("all-orders");

	        resource.add(selfLink);
	        resource.add(allOrdersLink);

	        // Retornar la entidad con enlaces
	        return ResponseEntity.ok(resource);
	    } catch (OrderNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
	    }
	}
	
	@PutMapping("/orders/{id}")
	public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) throws OrderNotFoundException {
	    
	    Order existingOrder = orderService.findOrder(id); 

	    
	    existingOrder.setIsbn(updatedOrder.getIsbn());
	    existingOrder.setExpiry(updatedOrder.getExpiry());

	   
	    Order savedOrder = orderService.saveOrder(existingOrder); 

	    return ResponseEntity.ok(savedOrder); 
	}
	
	// /orders/id
	@GetMapping("/orders/expiry")
	public ResponseEntity<List<Order>> getOrdersByExpiryDate(@RequestParam("expiry") String expiryStr) {
	    
	    LocalDate expiryDate = LocalDate.of(
	        Integer.parseInt(expiryStr.substring(0, 4)), // Año
	        Integer.parseInt(expiryStr.substring(5, 7)), // Mes
	        Integer.parseInt(expiryStr.substring(8, 10)) // Día
	    );
	    System.out.println("Buscando órdenes con fecha de caducidad: " + expiryStr);
	    
	
	    List<Order> orders = orderService.findAllOrders(); 
	    System.out.println("Órdenes disponibles: " + orders);

	    // Filtrar las órdenes por la fecha de caducidad
	    List<Order> filteredOrders = orders.stream()
	            .filter(order -> order.getExpiry().equals(expiryDate))
	            .collect(Collectors.toList());

	    System.out.println("Órdenes filtradas: " + filteredOrders);

	    return ResponseEntity.ok(filteredOrders);
	}
	
	
	@GetMapping("/orders/borrow")
	public ResponseEntity<List<Order>> getAllBorrowOrders() {
	  
	    List<Order> allOrders = orderService.findAllOrders();
	    LocalDate currentDate = LocalDate.now();

	    
	    List<Order> borrowOrders = allOrders.stream()
	            .filter(order -> order.getExpiry() != null && order.getExpiry().isAfter(currentDate))
	            .collect(Collectors.toList());

	    System.out.println("Órdenes en préstamo: " + borrowOrders);
	    return ResponseEntity.ok(borrowOrders);
	}
	
	

	@GetMapping("/orders/task2/expiry")
		public ResponseEntity<Page<Order>> getAllBorrowOrdersExpiry(
		    @RequestParam("expiryDate") String expiryDate,
		    @RequestParam(value = "page", defaultValue = "0") int page,
		    @RequestParam(value = "size", defaultValue = "10") int size) {
		    
		    LocalDate expiry = LocalDate.parse(expiryDate);
		    Pageable pageable = PageRequest.of(page, size);
		    
		    Page<Order> orders = orderService.findByExpiryDate(expiry, pageable);
		    
		    if (orders.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		    } else {
		        return new ResponseEntity<>(orders, HttpStatus.OK); 
		    }
		}
	
	
	
	@GetMapping("/orders/borrow/{id}")
	public ResponseEntity<Order> getBorrowOrder(@PathVariable Long id) {
	    
	    Order existingOrder;
	    try {
	        existingOrder = orderService.findOrder(id); 
	    } catch (OrderNotFoundException e) {
	        return ResponseEntity.notFound().build(); 
	    }

	    // Comprobar si la orden está prestada
	    LocalDate currentDate = LocalDate.now();
	    if (existingOrder.getExpiry().isBefore(currentDate)) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(null); 
	    }

	    return ResponseEntity.ok(existingOrder); 
	}
	
	
	@DeleteMapping("/orders/isbn/{isbn}")
	public ResponseEntity<Void> deleteOrderByIsbn(@PathVariable String isbn) {
	    try {
	        orderService.deleteOrderByIsbn(isbn);
	        return ResponseEntity.noContent().build(); 
	    } catch (OrderNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	    }
	}
	
	
}
