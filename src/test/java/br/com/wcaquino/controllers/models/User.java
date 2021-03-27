package br.com.wcaquino.controllers.models;

import java.util.List;

public class User {

    private String name;
    private int age;
    private double salary;
    private List<Son> sons;
    private Address  address;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
