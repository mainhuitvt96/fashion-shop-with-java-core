package model.entity.User;

import model.entity.User.abstraction.User;

public class Owner extends User {
    private int ID;
    private static int count;

    public Owner(String userName, String fullName, String phoneNumber, String email, String passWord, String address) {
        super(userName, fullName, phoneNumber, email, passWord, address);
        this.ID = ++count;
    }
    public Owner(String fullName, String phoneNumber, String email, String address) {
        super(fullName, phoneNumber, address, email);
        this.ID = ++count;
    }

    public Owner(String userName, String passWord) {
        super(userName, passWord);
        this.ID = ++count;
    }



    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }
}
