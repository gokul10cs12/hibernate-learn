package com.example.hibernatelearn.domain.composite;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class NameId implements Serializable {

    private String firstName;
    private String lastName;

    public NameId(){

    }

    public NameId(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
