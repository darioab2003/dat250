package no.hvl.dat250.jpa.tutorial.creditcards;

import jakarta.persistence.*;
import no.hvl.dat250.jpa.tutorial.relationshipexample.Family;

import java.util.Collection;
import java.util.Objects;

@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    private Integer credit_limit;
    private Integer balance;

    @ManyToOne
    private Pincode pincode;
    @ManyToOne
    private Bank bank;

    public Pincode getPincode() {
        return pincode;
    }

    public void setPincode(Pincode pincode) {
        this.pincode = pincode;
    }

    public Bank getOwningBank() {
        return bank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCreditLimit() {
        return credit_limit;
    }

    public void setCreditLimit(Integer credit_limit) {
        this.credit_limit = credit_limit;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CreditCard that = (CreditCard) obj;
        return Objects.equals(number, that.number) &&
                Objects.equals(credit_limit, that.credit_limit) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, credit_limit, balance);
    }

}
