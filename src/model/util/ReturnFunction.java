package model.util;

import model.entity.User.Customer;
import model.entity.User.Owner;
import model.exeption.InvalidChoiceException;
import model.service.ProductServiceDTO;
import model.service.UserService;
import view.OnlineFashionShoppingView;

public class ReturnFunction {
    public static OnlineFashionShoppingView onlineFashionShoppingView = OnlineFashionShoppingView.getInstance();
    public static ProductServiceDTO productServiceDTO = ProductServiceDTO.getInstance();
    public static UserService userService = UserService.getInstance();
    public static void backPage() throws InvalidChoiceException {
        do {
            try {
                System.out.println("""
                                            
                        1. Mua sản phẩm online
                        2. Quay lại trang trước
                        3. Quay lại Main Menu
                        4. Log Out
                        5. Đóng chương trình
                                """);
                int backChoice = Input.choiceIntegerInput("Nhâp vào lựa chọn của bạn: ");
                switch (backChoice){
                    case 1 -> productServiceDTO.selectItemToBuy();
                    case 2 -> {
                        return;
                    }
                    case 3 -> {
                        if (userService.getCurrentUser() instanceof Customer) {
                            onlineFashionShoppingView.displayCustomerrInterface();
                        } else if (userService.getCurrentUser() instanceof Owner) {
                            onlineFashionShoppingView.displayOwerInterface();
                        }
                    }
                    case 4 -> onlineFashionShoppingView.displayMainMenu();
                    case 5 -> System.exit(0);
                    default -> throw new InvalidChoiceException("Lựa chọn không hợp lệ");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }while (true);


    }
}
