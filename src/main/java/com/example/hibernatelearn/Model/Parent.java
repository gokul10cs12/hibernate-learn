package com.example.hibernatelearn.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "parent")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String parentName;

    @JsonManagedReference
    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    Set<Child> children;

    public Parent(String parentName) {
    }

    public Set<Child> getChildren() {
        return children;
    }

    public void setChildren(Set<Child> children) {

        this.children = children;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Parent() {
    }
}
