package br.com.wcaquino.controllers.builders;

import br.com.wcaquino.controllers.models.User;

public class UserBuilder {
    private User user;
    private UserBuilder(){}

    public static UserBuilder oneUser(String name, int age) {
        UserBuilder builder = new UserBuilder();
        builder.user = new User(name, age);
        return builder;
    }

    public UserBuilder withSalary(double param) {
        user.setSalary(param);
        return this;
    }

    public User build() {
        return user;
    }
}
