package com.example.hibernatelearn.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class College {
    private Person person;
    private String rollNo;
    private String department;

    public College(Person person, String rollNo, String department) {
        this.person = person;
        this.rollNo = rollNo;
        this.department = department;
    }
}
