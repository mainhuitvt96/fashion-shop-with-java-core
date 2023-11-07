package view;

import model.entity.Category;
import model.entity.User.Customer;
import model.entity.User.Owner;
import model.exeption.InvalidChoiceException;
import model.service.*;
import model.util.Input;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class OnlineFashionShoppingView {
    private static final  OnlineFashionShoppingView onlineFashionShoppingView = new OnlineFashionShoppingView();
    private  OnlineFashionShoppingView(){

    }
    public  static  OnlineFashionShoppingView getInstance(){
        return onlineFashionShoppingView;
    }
   private  static  final UserService userService = UserService.getInstance();
    private static final CategoryService categoryService = CategoryService.getInstance();
    private  static  final ProductServiceDTO productServiceDTO = ProductServiceDTO.getInstance();
    private  static  final VariantService variantService = VariantService.getInstance();
    private  static  final CartLineService cartLineService = CartLineService.getInstance();
    private  static  final OrderService orderService = OrderService.getInstance();
    public void displayMainMenu(){
        do {
            try{
                System.out.println("""
                                                     🌸❄♕♕♕💖💖💖♕♕♕❄🌸
                        【CHÀO MỪNG TRANG THƯƠNG MẠI MUA SẮM  QUẦN ÁO TRỰC TUYẾN TẠI VIỆT NAM】
                        Vui lòng lựa chọn 1 trong những lựa chọn sau:
                        ① Đăng ký ⌨
                        ② Đăng nhập ⍇
                        ⓷ Tắt chương trình ▣
                        """);
                int choice = Input.choiceIntegerInput("Nhập vào lựa chọn: ");
                switch (choice){
                    case 1 -> userService.signUp();
                    case 2 ->{
                        userService.logIn();
                        if (userService.getCurrentUser() instanceof Customer) {
                            displayCustomerrInterface();
                            break;
                        } else if (userService.getCurrentUser() instanceof Owner) {
                            displayOwerInterface();
                            break;
                        }
                    }
                    case 3 -> System.exit(0);
                    default -> throw new InvalidChoiceException("Lựa chọn không hợp lệ, nhập vào 1,2 hoặc 3");
                }
            }catch (Exception e){
                System.err.println(e.getMessage());
            }

        }while (true);
    }
    public void displayOwerInterface(){
        do {
            try {
                System.out.println("""
                        ------------------------OWNER------------------------
                        ➊. Hiển thị danh sách sản phẩm đang có
                        ➋. Thêm/ Xóa/ Sửa sản phẩm
                        ➌. Xem thông tin tài khoản và chỉnh sửa
                        ➍. Xem thông tin khách hàng
                        ➎. Thêm/Xóa/ Sửa danh mục sản phẩm
                        ➏. Thêm xóa sửa Variant
                        ➐. Sắp xếp danh sách sản phẩm
                        ➑. Logout
                        ❾. Đóng chương trình
                        """);
                int choice = Input.choiceIntegerInput("Nhấn phím tắt để thao tác ứng dụng: ");
                switch (choice) {
                    case 1 -> productServiceDTO.displayDetailOfProduct();
                    case 2 -> productServiceDTO.customizeProduct();
                    case 3 -> {
                        System.out.println(userService.getCurrentUser());
                        userService.editAccountInformation();
                    }
                    case 4 -> userService.getCustomerInfo();
                    case 5 -> categoryService.customizeCategory();
                    case 6 -> variantService.customizeVariant();
                    case 7 -> productServiceDTO.sortProduct();
                    case 8 -> displayMainMenu();
                    case 9 -> System.exit(0);
                    default -> throw new InvalidChoiceException("Lựa chọn không hợp lệ!");
                }
            }catch (Exception e) {
                System.out.println("Xảy ra lỗi hệ thống/ Invalid input/ Format Unexpected " + e.getMessage());
            }
        }while (true);
    }
    public void displayCustomerrInterface(){
        do {
            try {
                System.out.println("""
                        ----------------------CUSTOMER----------------------
                        ➊. Hiển thị sản phẩm trong giỏ hàng
                        ➋. Thêm sản phẩm vào giỏ hàng
                        ➌. Tìm sản phẩm theo Tên
                        ➍. Xem thông tin cá nhân người dùng
                        ➎. Hiển thị toàn bộ danh sách sản phẩm đang có
                        ➏. Hiển thị danh sách sản phẩm theo danh mục
                        ➐. Kiểm tra danh sách sản phẩm có bao nhiêu variant
                        ➑. Logout
                        ❾. Đóng chương trình
                        """);
                int choice = Input.choiceIntegerInput("Nhấn phím tắt để thao tác ứng dụng: ");
                switch (choice) {
                    case 1 -> orderService.checkCart();
                    case 2 -> productServiceDTO.selectItemToBuy();
                    case 3 -> productServiceDTO.findProductByName();
                    case 4 -> {
                        System.out.println(userService.getCurrentUser());
                        userService.editAccountInformation();
                    }
                    case 5 ->  productServiceDTO.displayDetailOfProduct();
                    case 6 -> productServiceDTO.displayProductByCategoryID();
                    case 7 ->{
                        productServiceDTO.displayBasicProductList();
                        int productID = Input.choiceIntegerInput("Nhập vào thông tin ID bạn muốn xem Vairiant");
                        variantService.displayVariantListByProductID(productID);
                    }
                    case 8 -> displayMainMenu();
                    case 9 -> System.exit(0);
                    default -> throw new InvalidChoiceException("Lựa chọn không hợp lệ!");
                }
            }catch (Exception e) {
                System.out.println("Xảy ra lỗi hệ thống/ Invalid input/ Format Unexpected " + e.getMessage());
            }
        }while (true);
    }

}
