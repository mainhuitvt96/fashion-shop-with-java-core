package model.factory;

import model.entity.User.abstraction.User;
import model.entity.User.Customer;
import model.entity.User.Owner;

public class UserFactory {
    private static UserFactory userFactory = new UserFactory();
    private UserFactory(){
    }
    public static UserFactory getInstance(){
        return userFactory;
    }
    public User getUser(String userName,String fullName, String phoneNumber, String email, String passWord,String address, String typeUser) {
        String typeUserToUpperCase = typeUser.toUpperCase();
        return switch (typeUserToUpperCase) {
            case "CUSTOMER" -> new Customer(userName,fullName, phoneNumber, email, passWord, address);
            case "OWNER" -> new Owner(userName,fullName, phoneNumber, email, passWord,address);
            default -> null;
        };
    }
}
