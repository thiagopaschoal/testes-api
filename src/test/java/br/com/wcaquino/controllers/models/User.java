package br.com.wcaquino.controllers.models;

import java.util.List;

public class User {

    private String name;
    private int age;
    private double salary;
    private List<Son> sons;
    private Address address;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public List<Son> getFilhos() {
        return sons;
    }

    public void setFilhos(List<Son> sons) {
        this.sons = sons;
    }

    public Address getEndereco() {
        return address;
    }

    public void setEndereco(Address address) {
        this.address = address;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
