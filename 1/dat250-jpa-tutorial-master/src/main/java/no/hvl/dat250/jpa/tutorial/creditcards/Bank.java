package no.hvl.dat250.jpa.tutorial.creditcards;

import jakarta.persistence.*;
import no.hvl.dat250.jpa.tutorial.relationshipexample.Job;

import java.util.*;

@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <CreditCard> creditCardList= new ArrayList<>();



    public Set<CreditCard> getOwnedCards() {
        return new HashSet<>(creditCardList);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreditCardList(Collection<CreditCard> creditCards) {
        this.creditCardList.clear();
        if (creditCards != null) {
            this.creditCardList.addAll(creditCards);
        }

    }





}
