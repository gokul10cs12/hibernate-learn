package com.example.hibernatelearn.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class AuthorUuid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //let the hibernate decide the UUID
    @Type(type = "org.hibernate.type.UUIDCharType")  // we are saving/persist the type as string in the database, and convert typ to UUID when calling
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    private String firstName;
    private String lastName;



    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
