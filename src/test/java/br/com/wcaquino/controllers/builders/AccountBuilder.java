package br.com.wcaquino.controllers.builders;

import br.com.wcaquino.controllers.models.Account;

public class AccountBuilder {

    private Account elemento;
    private AccountBuilder(){}

    public static AccountBuilder oneAccount(String name, Long userId) {
        AccountBuilder builder = new AccountBuilder();
        builder.elemento = new Account(name, userId);
        return builder;
    }

    public AccountBuilder withVisible(Boolean param) {
        elemento.setVisivel(param);
        return this;
    }

    public Account build() {
        return elemento;
    }
}
