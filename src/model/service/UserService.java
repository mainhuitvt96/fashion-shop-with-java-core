package model.service;

import model.entity.User.Customer;
import model.entity.User.Owner;
import model.entity.User.abstraction.User;
import model.exeption.InvalidAccountException;
import model.exeption.InvalidChoiceException;
import model.factory.UserFactory;
import model.util.FileReaderUlti;
import model.util.FileWriterUlti;
import model.util.Input;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static  final  UserService userService  = new UserService();
    private  boolean checkCustomer = false;
    private boolean checkOwner = false;
    private  UserService(){

    }
    public static UserService getInstance(){
        return userService;
    }
    private static final List<Customer> CUSTOMER_LIST = new ArrayList<>();
    private static final List<Owner> OWNER_LIST = new ArrayList<>();
    private static final List<User> USER_LIST = new ArrayList<>();
    private User currentUser;

    static {
        List<Customer> customerList = FileReaderUlti.fileReadingCustomer("src\\model\\data\\customer.csv");
        CUSTOMER_LIST.addAll(customerList);
        List<Owner> ownerList = FileReaderUlti.fileReadingOwner("src\\model\\data\\owner.csv");
        OWNER_LIST.addAll(ownerList);
        USER_LIST.addAll(CUSTOMER_LIST);
        USER_LIST.addAll(OWNER_LIST);
    }

    public User getCurrentUser(){
        return currentUser;
    }
    public List<Customer> getCustomerList(){
        return CUSTOMER_LIST;
    }
    public List<Owner> getOwnerList(){
        return OWNER_LIST;
    }
    public boolean isCheckCustomer(){
        return checkCustomer;
    }
    public boolean isCheckOwner(){
        return checkOwner;
    }

    public void logIn(){
        do {
            try{
                String userName = Input.promt("Nhập tài khoản (user name): ");
                String password = Input.promt("Nhập vào mật khẩu: ");
                String logIn = Input.promt("Xác nhận đăng nhập,(Ấn phím Y (Yes) hoặc phím N (No)");
                if (logIn.equalsIgnoreCase("Y")){ // phương thức so sánh 2 chuỗi không phân biệt chữ hoa và chữ thường
                    for (User user: USER_LIST){
                        if (userName.equals(user.getUserName())){
                            for (Customer customer: CUSTOMER_LIST){
                                if (userName.equals(customer.getUserName())){
                                    if (password.equals(customer.getPassWord())){
                                        System.out.println("Đăng nhập thành công! Xin chào " + userName);
                                        currentUser = customer;
                                        checkCustomer = true;

                                        return;
                                    }
                                }
                            }
                            for (Owner owner: OWNER_LIST){
                                if (userName.equals(owner.getUserName())){
                                    if (password.equals(owner.getPassWord())){
                                        System.out.println("Đăng nhập thành công! Xin chào " + userName);
                                        currentUser = owner;
                                        checkOwner = true;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    throw new InvalidAccountException("Sai thông tin đăng nhập (userName hoặc password0");
                }
            }catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }while (true);
    }

    public void signUp(){
        System.out.println("""
                ----------------------------------------------------------
                Bạn muốn tạo tài khoản nào ?
                1. Chủ shop (owner)
                2. Người dùng
                3. Quay lại
                """);
        int choice = Input.choiceIntegerInput("Vui lòng nhập vào lựa chọn: ");
        do {
            try{
                User newUser;
                switch (choice){
                    case 1 -> {

                        String ownerName = Input.prompt("Nhập thông tin tài khoản (username): ", "USER_NAME");
                        if (OWNER_LIST.isEmpty()){
                            String password = Input.prompt("Nhập mật khẩu (ít nhất @,viết hoa,thường,số,từ 8 chữ trở lên): ", "PASSWORD");
                            String fullName = Input.promt("Nhập họ tên đầy đủ: ");
                            String phoneNumber = Input.prompt("Nhập số điện thoại (+840xxxx hoặc 0xxxx,10 chữ số): ", "PHONE_NUMBER");
                            String email = Input.prompt("Nhập Email: ", "EMAIL");
                            String address = Input.promt("Nhập địa chỉ: ");
                            UserFactory userFactor = UserFactory.getInstance();
                            newUser = userFactor.getUser(ownerName, fullName, phoneNumber, email, password, address,"owner");
                            FileWriterUlti.writeFileUser("src\\model\\data\\owner.csv", newUser);
                            OWNER_LIST.add((Owner) newUser);
                            USER_LIST.add(newUser);
                            System.out.println("Đăng ký tài khoản thành công.");
                        }else {
                            boolean checkExistOwner = false;
                            for (User user: OWNER_LIST){
                                if (ownerName.equals(user.getUserName())){
                                    checkExistOwner = true;
                                    break;
                                }
                            }
                            if (checkExistOwner){
                                throw new InvalidAccountException("Tài khoản này đã tồn tại, vui lòng đặt tên username khác! ");
                            } else {
                                String password = Input.prompt("Nhập mật khẩu (ít nhất @,viết hoa,thường,số,từ 8 chữ trở lên): ", "PASSWORD");
                                String fullName = Input.promt("Nhập họ tên đầy đủ: ");
                                String phoneNumber = Input.prompt("Nhập số điện thoại (+840xxxx hoặc 0xxxx,10 chữ số): ", "PHONE_NUMBER");
                                String email = Input.prompt("Nhập Email: ", "EMAIL");
                                String address = Input.promt("Nhập địa chỉ: ");
                                UserFactory userFactor = UserFactory.getInstance();
                                newUser = userFactor.getUser(ownerName, fullName, phoneNumber, email, password, address,"owner");
                                FileWriterUlti.writeFileUser("src\\model\\data\\owner.csv", newUser);
                                OWNER_LIST.add((Owner) newUser);
                                USER_LIST.add(newUser);
                                System.out.println("Đăng ký tài khoản thành công.");
                            }
                        }

                        return;
                    }
                    case 2 ->{
                        String customerName = Input.prompt("Nhập thông tin tài khoản (username): ", "USER_NAME");
                        boolean checkExistCustomer= false;
                        for (User user: CUSTOMER_LIST){
                            if (customerName.equals(user.getUserName())){
                                checkExistCustomer = true;
                                break;
                            }
                        }
                        if (checkExistCustomer){
                            throw new InvalidAccountException("Tài khoản này đã tồn tại, vui lòng đặt tên username khác! ");
                        } else {
                            String password = Input.prompt("Nhập mật khẩu (ít nhất @,viết hoa,thường,số,từ 8 chữ trở lên): ", "PASSWORD");
                            String fullName = Input.promt("Nhập họ tên đầy đủ: ");
                            String phoneNumber = Input.prompt("Nhập số điện thoại (+840xxxx hoặc 0xxxx,10 chữ số): ", "PHONE_NUMBER");
                            String email = Input.prompt("Nhập Email: ", "EMAIL");
                            String address = Input.promt("Nhập địa chỉ: ");
                            UserFactory userFactor = UserFactory.getInstance();
                            newUser = userFactor.getUser(customerName, fullName, phoneNumber, email, password, address,"customer");
                            FileWriterUlti.writeFileUser("src\\model\\data\\customer.csv", newUser);
                            CUSTOMER_LIST.add((Customer) newUser);
                            USER_LIST.add(newUser);
                            System.out.println("Đăng ký tài khoản thành công.");
                        }
                        return;
                    }
                    case 3 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("Vui lòng chỉ nhập vào lựa chọn là  1, 2 hoặc 3");
                }

            }catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }while (true);
    }
    public void getCustomerInfo(){
        do {
            try{
                CUSTOMER_LIST.forEach(Customer -> System.out.println(
                        "ID: " +Customer.getID()
                        +". " + Customer.getFullName()));
                int ID = Input.choiceIntegerInput("Nhập ID khách hàng muốn xem: ");
                if (ID <= CUSTOMER_LIST.size()){
                    CUSTOMER_LIST.stream().filter(ele -> ele.getID() == ID).forEach(System.out::println);
                    editCustomerAccountInformation(ID);
                    return;
                } else throw new InvalidChoiceException("Vui lòng nhập lại");
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }while (true);
    }
    public void editAccountInformation(){ // chỉnh sửa thông tin đăng nhập của người dùng hiện tại
        do {
            try{
                System.out.println("""
                Bạn muốn chỉnh sửa thông tin nào?
                1. Tên họ đầy đủ
                2. Số điện thoại
                3. Địa chỉ email
                4. Địa chỉ
                5. Mật khẩu
                6. Quay lại Main Menu
                """);
                int choice = Input.choiceIntegerInput("Nhập vào lựa chọn");
                switch (choice){
                    case 1 -> {
                        System.out.println("Họ tên hiện tại của bạn là: ");
                        System.out.println(currentUser.getFullName());
                        String editUserName = Input.promt("Nhập họ tên mới: ");
                        currentUser.setFullName(editUserName);

                        System.out.println("Bạn chỉnh sửa thành công!");
                    }
                    case 2 -> {
                        System.out.println("Số điện thoại hiện tại của bạn là: ");
                        System.out.println(currentUser.getPhoneNumber());
                        String editPhoneNumber = Input.promt("Nhập số điện thoại mới: ");
                        currentUser.setPhoneNumber(editPhoneNumber);
                        System.out.println("Bạn chỉnh sửa thành công!");
                    }
                    case 3 -> {
                        System.out.println("Địa chỉ email hiện tại của bạn là: ");
                        System.out.println(currentUser.getEmail());
                        String editEmail = Input.promt("Nhập địa chỉ email mới: ");
                        currentUser.setEmail(editEmail);
                        System.out.println("Bạn chỉnh sửa thành công!");
                    }
                    case 4 -> {
                        System.out.println("Địa chỉ hiện tại của bạn là: ");
                        System.out.println(currentUser.getAddress());
                        String editAddress = Input.promt("Nhập địa chỉmới: ");
                        currentUser.setAddress(editAddress);
                        System.out.println("Bạn chỉnh sửa thành công!");
                    }
                    case 5 -> {
                        String oldPasswordVerify = Input.promt("Nhập lại password cũ của bạn");
                        if (oldPasswordVerify.equals(currentUser.getPassWord())){
                            String editUserPassword = Input.prompt("Nhập vào mật khẩu mới: ", "PASSWORD");
                            currentUser.setPassWord(editUserPassword);
                            System.out.println();
                            System.out.println("Bạn chỉnh sửa thành công!");
                            break;
                        }
                        throw new InvalidAccountException("Bạn đã nhập sai mật khẩu, vui lòng nhập lại đúng tui mới cho sửa!");
                    }
                    case 6 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("Vui lòng nhập lại lựa chọn 1 trong 6 lựa chọn trên");
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }while (true);
    }
    public void editCustomerAccountInformation(int ID){
        do {
            try {
                System.out.println("""
                        Bạn muốn sửa thông tin nào trong danh sách bên dưới?
                        1. Tên họ đầy đủ
                        2. Số điện thoại
                        3. Địa chỉ email
                        4. Địa chỉ nhà
                        5. Mật khẩu
                        6. Quay lại Main Menu
                        """);
                int choice = Input.choiceIntegerInput("Nhập vào lựa chọn: ");
                switch (choice) {
                    case 1 -> {
                        for (Customer customer : CUSTOMER_LIST) {
                            if (customer.getID() == ID) {
                                System.out.println("Họ tên hiện tại: ");
                                System.out.println(customer.getFullName());
                                String editUserName = Input.promt("Nhập họ tên mới: ");
                                customer.setFullName(editUserName);
                                System.out.println("Bạn chỉnh sửa thành công!");
                                break;
                            }
                        }
                    }
                    case 2 -> {
                        for (Customer customer : CUSTOMER_LIST) {
                            if (customer.getID() == ID) {
                                System.out.println("Số điện thoại hiện tại là: ");
                                System.out.println(customer.getPhoneNumber());
                                String editPhoneNumber = Input.prompt("Nhập số điện thoại mới cho người dùng: ", "PHONE_NUMBER");
                                customer.setPhoneNumber(editPhoneNumber);
                                System.out.println("Bạn đã chỉnh sửa thành công");
                                break;
                            }
                        }
                    }
                    case 3 -> {
                        for (Customer customer : CUSTOMER_LIST) {
                            if (customer.getID() == ID) {
                                System.out.println("Email hiện tại là: ");
                                System.out.println(customer.getEmail());
                                String editUserEmail = Input.prompt("Nhập email mới cho người dùng: ", "EMAIL");
                                customer.setEmail(editUserEmail);
                                System.out.println("Bạn đã chỉnh sửa thành công");
                                break;
                            }
                        }
                    }
                    case 4 -> {
                        for (Customer customer : CUSTOMER_LIST) {
                            if (customer.getID() == ID) {
                                System.out.println("Địa chỉ hiện tại là: ");
                                System.out.println(customer.getAddress());
                                String editUserAddress = Input.promt("Nhập địa chỉ mới cho người dùng: ");
                                customer.setAddress(editUserAddress);
                                System.out.println("Bạn đã chỉnh sửa thành công");
                                break;
                            }
                        }
                    }
                    case 5 -> {
                        for (Customer customer : CUSTOMER_LIST) {
                            if (customer.getID() == ID) {
                                String oldPassWordVerify = Input.promt("Nhập lại mật khẩu cũ: ");
                                if (oldPassWordVerify.equals(customer.getPassWord())) {
                                    String editUserPassword = Input.prompt("Nhập mật khẩu mới cho người dùng: ", "PASSWORD");
                                    customer.setPassWord(editUserPassword);
                                    System.out.println("Bạn đã chỉnh sửa thành công");
                                    break;
                                } else  { throw new InvalidAccountException("Bạn đã nhập sai mật khẩu cũ, nhập lại đúng tui mới cho sửa!"); }
                            }
                        }
                    }
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }while (true);
    }
}
