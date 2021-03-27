package br.com.wcaquino.controllers.models;

public class Address {

    private String endereco;
    private int numero;

    public Address(String endereco, int numero) {
        this.endereco = endereco;
        this.numero = numero;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getNumero() {
        return numero;
    }
}
