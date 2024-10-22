/**
 * 
 */
package no.hvl.dat152.rest.ws.model;


import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "orders")
public class Order extends RepresentationModel<Order>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String isbn;
	
	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate expiry;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false) // Clave foránea en la tabla de orders
	@JsonIgnoreProperties("orders")
    private User user; // Referencia al usuario que creó el pedido
	
	public Order() {
		//default
	}
	
	public Order(String isbn, LocalDate expiry, User user) {
	    this.isbn = isbn;
	    this.expiry = expiry;
	    this.user = user; // Asegúrate de asignar el usuario aquí
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the expiry
	 */
	public LocalDate getExpiry() {
		return expiry;
	}

	/**
	 * @param expiry the expiry to set
	 */
	public void setExpiry(LocalDate expiry) {
		this.expiry = expiry;
	}
	
	/**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
	
	@Override
    public final int hashCode() {
		final int prime = 31;
		int result = 1;
//		result = prime * result + ((Long.valueOf(id) == null) ? 0 : Long.hashCode(id));
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((expiry == null) ? 0 : expiry.hashCode());
        return result;
    }
	
	@Override
	public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Order order = (Order) obj;
        
        return this.id == order.id;
	 }
	
	@Override
    public String toString() {
        return "Order [isbn=" + isbn + ", expiry=" + expiry  + "]";
    }
	
}
