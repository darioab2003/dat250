/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.OrderNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.UnauthorizedOrderActionException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.repository.OrderRepository;
import no.hvl.dat152.rest.ws.security.UserDetailsImpl;

/**
 * @author tdoy
 */
@Service
public class OrderService {

	
	
	// TODO copy your solutions from previous tasks!
	@Autowired
	private OrderRepository orderRepository;
	
public Order saveOrder(Order order) {
		
		order = orderRepository.save(order);
		
		return order;
	}
	
	
	
	// TODO public void deleteOrder(Long id)
	public void deleteOrder(Long id) throws OrderNotFoundException {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order with id: " + id + " not found in the order list!");
        }
        orderRepository.deleteById(id);
    }

	
	
	// TODO public List<Order> findAllOrders()
	public List<Order> findAllOrders() {
        List<Order> allOrders = (List<Order>) orderRepository.findAll();
        return allOrders;
    }
	
	public void deleteOrderByIsbn(String isbn) throws OrderNotFoundException {
        List<Order> orders = orderRepository.findByIsbn(isbn);

        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found with ISBN: " + isbn);
        }

        for (Order order : orders) {
            orderRepository.delete(order);
        }
    }
	
	
	// TODO public List<Order> findByExpiryDate(LocalDate expiry, Pageable page)
	public Page<Order> findByExpiryDate(LocalDate expiry, Pageable pageable) {
	    return orderRepository.findByExpiryBefore(expiry, pageable);
	}
	
	
	
	public Order findOrder(Long id) throws OrderNotFoundException, UnauthorizedOrderActionException {
		
		verifyPrincipalOfOrder(id);
		Order order = orderRepository.findById(id)
				.orElseThrow(()-> new OrderNotFoundException("Order with id: "+id+" not found in the order list!"));
		
		return order;
	}
	
	private boolean verifyPrincipalOfOrder(Long id) throws UnauthorizedOrderActionException {
		
		JwtAuthenticationToken oauthJwtToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userPrincipal = (UserDetailsImpl) oauthJwtToken.getDetails();
		// verify if the user sending request is an ADMIN or SUPER_ADMIN
		for(GrantedAuthority authority : userPrincipal.getAuthorities()){
			if(authority.getAuthority().equals("ADMIN")) {
				return true;
			}
		}
		
		// otherwise, make sure that the user is the one who initially made the order
		String email = orderRepository.findEmailByOrderId(id);
		
		if(email.equals(userPrincipal.getEmail()))
			return true;
		
		throw new UnauthorizedOrderActionException("Unauthorized order action!");

	}

}
