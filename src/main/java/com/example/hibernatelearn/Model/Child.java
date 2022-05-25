package com.example.hibernatelearn.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class Child {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @NotNull
    private String childName;


    private long parentId;
//
//    @ManyToOne
//    Parent parent;


    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
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


}
