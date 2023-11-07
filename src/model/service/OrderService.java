package model.service;

import model.DTO.ProductDTO;
import model.entity.Cart.Cartline;
import model.entity.Variant;
import model.exeption.InvalidChoiceException;
import model.util.FileReaderUlti;
import model.util.FileWriterUlti;
import model.util.Input;
import model.util.ReturnFunction;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private static final OrderService orderService = new OrderService();
    private OrderService(){}
    public static OrderService getInstance(){
        return orderService;
    }
    private static final List<Cartline> CARTLINES = ProductServiceDTO.getInstance().getCartlineList(); // lấy bên product service
    private static final List<Variant> VARIANT_LIST = new ArrayList<>();
    private static final String path = "src\\model\\data\\variant.csv";
    private static final UserService userService = UserService.getInstance();
    static {
        try {
            List<Variant> dataVariantList = FileReaderUlti.fileReadingVariant(path);
            VARIANT_LIST.addAll(dataVariantList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean isPaid = false;
    public boolean checkPaidCart() {
        return isPaid;
    }
    public void checkCart() throws InvalidChoiceException {
        int totalCart = 0;
        if (CARTLINES.isEmpty()) {
            System.out.print("""
                    Giỏ hàng:
                    Không có gì trong giỏ hàng
                    Bạn muốn mua thêm sản phẩm không?
                    """);
            ReturnFunction.backPage();
        } else {
            System.out.println("Giỏ hàng:" + "\n" + "Tổng số sản phẩm: " + CARTLINES.size());
            for (Cartline cartline : CARTLINES) {
                double unitPrice = cartline.getQuantitySelected()*ProductServiceDTO.getInstance().getProductById(cartline.getProductID()).getPrice();
                System.out.println("Tên sản phẩm " + ProductServiceDTO.getInstance().getProductById(cartline.getProductID()).getNameProduct()
                        + ", Size: "+ VariantService.getInstance().getVariantByID(cartline.getVariantID()).getSize()
                        + ",  Color: " + VariantService.getInstance().getVariantByID(cartline.getVariantID()).getColor()
                        + ",  Quantity: " + cartline.getQuantitySelected()
                        + ",  Giá tiền: " + unitPrice + "₫");
                totalCart += unitPrice;
            }
            System.out.println("---------------------------------------------------------------------------------------------------");
            System.out.println("\t\t\t\t\t\t\t" + "Tổng tiền " + totalCart + "₫");
            payTheBill();
        }
    }
    public void payTheBill() throws InvalidChoiceException {
        do {
            try {
                System.out.println("""
                1. Thanh toán giỏ hàng
                2. Ship COD
                3. Quay lại
                """);
                int choice = Input.choiceIntegerInput("Lựa chọn của bạn: ");
                switch (choice) {
                    case 1 -> {
                        for (int i = 0; i < CARTLINES.size(); i++) {
                            for (int j = 0; j < VARIANT_LIST.size(); j++) {
                                if (CARTLINES.get(i).getProductID() == VARIANT_LIST.get(j).getProductID()){
                                    if (CARTLINES.get(i).getVariantID() == (VARIANT_LIST.get(j).getVariantID())) {
                                        VARIANT_LIST.get(j).setQuantityPurchase(CARTLINES.get(i).getQuantitySelected()); // tru sl trong variant;
                                        ProductDTO productDTO = ProductServiceDTO.getInstance().getProductById(CARTLINES.get(i).getProductID());
                                        productDTO.setNumberOfProductPurchase(CARTLINES.get(i).getQuantitySelected()); //luu xuong file
                                        isPaid = true; //Neu da tra tien r thi in hoa don ra file
                                    }
                                }
                            }
                        }
                        System.out.println("Đang thực hiện giao dịch ...");
                        System.out.println("Bạn đã thanh toán thành công, sách sẽ được chuyển tới từ 3 - 5 ngày ");
                        FileWriterUlti.writeFileInvoice("src\\model\\data\\invoice.txt", userService.getCurrentUser());
                        FileWriterUlti.writeFileAppendInvoice("src\\model\\data\\invoice.txt", CARTLINES, VARIANT_LIST);
                        FileWriterUlti.writeFileVariantList("src\\model\\data\\variant.csv", VARIANT_LIST);
                        FileWriterUlti.writeFileAppendInvoiceHistory("src\\model\\data\\invoiceHistory.csv", UserService.getInstance().getCurrentUser(), CARTLINES);
                        FileWriterUlti.writeFileProductListDTO("src\\model\\data\\product_DTO.csv",ProductServiceDTO.getInstance().getProductList());
                        CARTLINES.clear();
                        return;
                    }
                    case 2 -> {
                        do {
                            try {
                                System.out.println("Địa chỉ hiện tại của tài khoản là:  "+ userService.getCurrentUser().getAddress());
                                String verify = Input.promt("Nhập vào địa chỉ giao hàng (Y/N): ");
                                if (verify.equalsIgnoreCase("Y")) {
                                    for (int i = 0; i < CARTLINES.size(); i++) {
                                        for (int j = 0; j < VARIANT_LIST.size(); j++) {
                                            if (CARTLINES.get(i).getProductID() == VARIANT_LIST.get(j).getProductID()) {
                                                if (CARTLINES.get(i).getVariantID() == (VARIANT_LIST.get(j).getProductID())) {
                                                    VARIANT_LIST.get(j).setQuantityPurchase(CARTLINES.get(i).getQuantitySelected()); // tru sl trong variant;
                                                    ProductDTO productDTO = ProductServiceDTO.getInstance().getProductById(CARTLINES.get(i).getProductID());
                                                    productDTO.setNumberOfProductPurchase(CARTLINES.get(i).getQuantitySelected()); //luu xuong file
                                                    isPaid = true; //Neu da tra tien r thi in hoa don ra file
                                                }
                                            }
                                        }
                                    }
                                    System.out.println("Bạn đã đặt hàng thành công, sách sẽ được chuyển tới từ 3 - 5 ngày, vui lòng thanh toán khi nhận hàng!");
                                    FileWriterUlti.writeFileInvoice("src\\model\\data\\invoice.txt", userService.getCurrentUser());
                                    FileWriterUlti.writeFileAppendInvoice("src\\model\\data\\invoice.txt", CARTLINES, VARIANT_LIST);
                                    FileWriterUlti.writeFileVariantList("src\\model\\data\\variant.csv", VARIANT_LIST);
                                    FileWriterUlti.writeFileAppendInvoiceHistory("src\\model\\data\\invoiceHistory.csv", UserService.getInstance().getCurrentUser(), CARTLINES);
                                    FileWriterUlti.writeFileProductListDTO("src\\model\\data\\product_DTO.csv",ProductServiceDTO.getInstance().getProductList());
                                    CARTLINES.clear();
                                    return;
                                } else if (verify.equalsIgnoreCase("N")) {
                                    String newAddress = Input.promt("Nhập vào địa chỉ giao hàng: ");
                                    return;
                                } else throw new InvalidChoiceException("Đầu vào không hợp lệ");

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } while (true);
                    }
                    case 3 ->{
                        return;
                    }
                    default -> throw new InvalidChoiceException("Vui lòng nhập lựa chọn là số từ 1 đến 3");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }while (true);

    }
}
