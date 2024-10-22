/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no.hvl.dat152.rest.ws.exceptions.OrderNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.repository.OrderRepository;
import no.hvl.dat152.rest.ws.repository.UserRepository;

/**
 * @author tdoy
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	
	public List<User> findAllUsers(){
		
		List<User> allUsers = (List<User>) userRepository.findAll();
		
		return allUsers;
	}
	
	public User findUser(Long userid) throws UserNotFoundException {
		
		User user = userRepository.findById(userid)
				.orElseThrow(()-> new UserNotFoundException("User with id: "+userid+" not found"));
		
		return user;
	}
	
	
	// TODO public User saveUser(User user)
	public User saveUser(User user) {
	    return userRepository.save(user);
	}
	

	// TODO public void deleteUser(Long id) throws UserNotFoundException 
	public void deleteUser(Long id) throws UserNotFoundException {
	    // Verificar si el usuario existe, de lo contrario lanzar una excepción
	    User user = userRepository.findById(id)
	            .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));
	    
	    // Eliminar el usuario
	    System.out.println("User with id: " + id + " found. Proceeding to delete.");
	    userRepository.delete(user);
	}	


	
	// TODO public User updateUser(User user, Long id)
	public User updateUser(User user, Long id) throws UserNotFoundException {
	    User existingUser = userRepository.findById(id)
	            .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));

	    existingUser.setFirstname(user.getFirstname());
	    existingUser.setLastname(user.getLastname());

	    return userRepository.save(existingUser);
	}
	
	// TODO public Set<Order> getUserOrders(Long userid) 
	public Set<Order> getUserOrders(Long userid) throws UserNotFoundException {
	    User user = userRepository.findById(userid)
	            .orElseThrow(() -> new UserNotFoundException("User with id: " + userid + " not found"));
	    
	    return user.getOrders();
	}
	
	// TODO public Order getUserOrder(Long userid, Long oid)
	public Order getUserOrder(Long userid, Long orderid) throws UserNotFoundException, OrderNotFoundException {
	  
	    User user = userRepository.findById(userid)
	            .orElseThrow(() -> new UserNotFoundException("User with id: " + userid + " not found"));

	    Order order = user.getOrders().stream()
	            .filter(o -> o.getId().equals(orderid))
	            .findFirst()
	            .orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderid + " not found for user with id: " + userid));

	    return order; 
	}
	
	
	// TODO public void deleteOrderForUser(Long userid, Long oid)
	public void deleteOrderForUser(Long orderId) {
	    Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
	    orderRepository.delete(order);
	}

	
	
	// TODO public User createOrdersForUser(Long userid, Order order)
	public User createOrdersForUser(Long userid, Order order) throws UserNotFoundException {
	 
	    User user = userRepository.findById(userid)
	            .orElseThrow(() -> new UserNotFoundException("User with id: " + userid + " not found"));

	    order.setUser(user);

	
	    user.getOrders().add(order);
	    userRepository.save(user); 

	    return user;
	}
	
	
	public Order createOrderForUser(Long userid, Order order) throws UserNotFoundException {
	   
	    User user = userRepository.findById(userid)
	            .orElseThrow(() -> new UserNotFoundException("User with id: " + userid + " not found"));

	    order.setUser(user);

	    
	    user.getOrders().add(order);
	    userRepository.save(user); 

	    return order;
	}

}