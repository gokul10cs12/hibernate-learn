package com.example.hibernatelearn.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class Child {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @NotNull
    private String childName;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id")
    Parent parent;

    public Child(String childName) {
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public Child() {
    }
}
