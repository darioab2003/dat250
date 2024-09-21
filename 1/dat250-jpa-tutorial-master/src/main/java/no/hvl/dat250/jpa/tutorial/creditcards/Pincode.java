package no.hvl.dat250.jpa.tutorial.creditcards;

import jakarta.persistence.*;
import no.hvl.dat250.jpa.tutorial.relationshipexample.Job;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Pincode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pincode;
    private Integer count;



    public Long getId() {
        return id;
    }

    public String getCode() {
        return pincode;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}
