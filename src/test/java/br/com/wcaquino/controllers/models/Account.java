package br.com.wcaquino.controllers.models;

public class Account {

    private Long id;
    private String nome;
    private Boolean visivel;
    private Long usuario_id;

    public Account(String nome, Long usuario_id) {
        this.nome = nome;
        this.visivel = true;
        this.usuario_id = usuario_id;
    }

    public Account(String nome, Boolean visivel, Long usuario_id) {
        this.nome = nome;
        this.visivel = visivel;
        this.usuario_id = usuario_id;
    }

    public Boolean getVisivel() {
        return visivel;
    }

    public Long getUsuario_id() {
        return usuario_id;
    }

    public String getNome() {
        return nome;
    }
}
