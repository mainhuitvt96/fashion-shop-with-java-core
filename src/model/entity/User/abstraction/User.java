package model.entity.User.abstraction;

public abstract class User {
    private int ID;
    private String userName;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String passWord;
    private String address;
    private static int count = 0;

    public User() {
    }

    //Cấu trúc này để tạo mới tài khoản
    protected User(String userName, String fullName, String phoneNumber, String email, String passWord, String address) {
        this.ID = ++count;
        this.userName = userName;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passWord = passWord;
        this.address = address;
    }

    // Cấu trúc này để giao hàng
    protected User(String fullName, String phoneNumber, String email, String address) {
        this.ID = ++count;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    // cấu trúc này để đăng nhập
    protected User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "THÔNG TIN NGƯỜI DÙNG: "
                + "\n" + "- ID: " + ID
                + "\n" + "- Tên họ đầy đủ: " + fullName
                + "\n" + "- Số điện thoại: " + phoneNumber
                + "\n" + "- Email: " + email
                + "\n" + "- Địa chỉ nhà: " + address;
    }
}
