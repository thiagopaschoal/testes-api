package br.com.wcaquino.controllers.builders;

import br.com.wcaquino.controllers.models.Address;
import br.com.wcaquino.controllers.models.User;

public class UserBuilder {

    private User user;

    private UserBuilder() {}

    public static UserBuilder oneUser(String name, int age) {
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.user = new User(name, age);
        return userBuilder;
    }

    public UserBuilder withSalary(double salary) {
        this.user.setSalary(salary);
        return this;
    }

    public UserBuilder withAddress(Address address) {
        this.user.setAddress(address);
        return this;
    }

    public User build() {
        return user;
    }


}
