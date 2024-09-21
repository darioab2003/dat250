package no.hvl.dat250.jpa.tutorial.creditcards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import no.hvl.dat250.jpa.tutorial.relationshipexample.Family;
import no.hvl.dat250.jpa.tutorial.relationshipexample.Job;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private Integer number;


    @ManyToMany(mappedBy = "addresses")
    private Set<Customer> customers;  // Cambiado de Collection a Set

    public Set<Customer> getOwners() {
        return customers;
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
