package model.entity.User;

import model.entity.User.abstraction.User;

public class Customer extends User {
    private int ID;
    private static int count;

    public Customer(String userName, String fullName, String phoneNumber, String email, String passWord, String address) {
        super(userName, fullName, phoneNumber, email, passWord, address);
        this.ID = ++count;
    }
    public Customer(String userName, String passWord) {
        super(userName, passWord);
        this.ID = ++count;
    }

    public Customer(String userName, String fullName, String phoneNumber, String email, String passWord) {

    }
    @Override
    public int getID(){
        return ID;
    }
}
