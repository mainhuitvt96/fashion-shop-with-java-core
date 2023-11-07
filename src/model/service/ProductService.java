package model.service;

import model.DTO.ProductDTO;
import model.builder.product.ProductConcreteBuilder;
import model.entity.Category;
import model.entity.Product;
import model.exeption.InvalidChoiceException;
import model.util.FileReaderUlti;
import model.util.FileWriterUlti;
import model.util.Input;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Formattable;
import java.util.List;

public class ProductService {
    private static final ProductService productService = new ProductService();
    CategoryService newCategoryService = CategoryService.getInstance();

    private ProductService() {

    }

    public static ProductService getInstance() {
        return productService;
    }

    private static final List<Product> PRODUCT_LIST = new ArrayList<>();
    //private static final List<Product> PRODUCT_CART = new ArrayList<>();
    private static final String path = "src\\model\\data\\product.csv";

    static {
        try {
            List<Product> productList = FileReaderUlti.fileReadingProduct(path);
            PRODUCT_LIST.addAll(productList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Product> getProductList() {
        return PRODUCT_LIST;
    }

    public void customizeProduct() {
        do {
            try {
                System.out.println("""
                        Bạn muốn thao tác gì với sản phẩm:
                        1. Thêm mới sản phẩm
                        2. Sửa thông tin sản phẩm
                        3. Xóa sản phẩm ra khỏi danh sách
                        4. Xem danh sách sản phẩm theo danh mục
                        5. Xem Danh sách chi tiết các sản phẩm đang có
                        6. Quay lại
                        """);
                int choice = Input.choiceIntegerInput("Nhập vào lựa chọn của bạn");
                switch (choice) {
                    case 1 -> addNewProduct();
                    case 2 -> editInfoOfProductByID();
                    case 3 -> removeProductById();
                    case 4 -> displayProductByCategoryID();
                    case 5 -> displayDetailOfProduct();
                    case 6 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("Vui lòng nhập lựa chọn là số từ đến 4");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    } // chưa copy

    public void addNewProduct() {
        do {
            try {
                //displayProductByCategoryID(); // chỉ hiển thị lúc test
                newCategoryService.displayCategoryList();
                int newCategoryID = Input.choiceIntegerInput("Nhập vào ID của danh mục mà bạn muốn thêm sản phẩm:");
                //System.out.println("Danh sách sản phẩm thuộc danh mục hiện đang có."); //chỉ hiện thị lúc test
                String newNameProduct = Input.promt("Nhập vào tên sản phẩm: ").trim();
                String newSize = Input.promt("Nhập vào kích thước sản phẩm: ").trim();
                String newColor = Input.promt("Nhập vào màu sắc sản phẩm: ").trim();
                int newQuantity = Input.choiceIntegerInput("Nhập vào số lượng sản phẩm:");
                double newPrice = Input.choiceIntegerInput("Nhập vào giá tiền: ");
                String newDescribe = Input.promt("Nhập vào mô tả sản phẩm: ").trim();
                if (PRODUCT_LIST.isEmpty()) {
                    PRODUCT_LIST.add(new ProductConcreteBuilder()
                            .setCategoryID(newCategoryID)
                            .setNameProduct(newNameProduct)
                            .setSize(newSize)
                            .setColor(newColor)
                            .setQuantity(newQuantity)
                            .setPrice(newPrice)
                            .setDescribe(newDescribe).build());
                    FileWriterUlti.writeFileProductList(path, PRODUCT_LIST);
                    System.out.println("Thêm sản phẩm thành công");
                } else {
                    boolean checkExistProduct = false;
                    for (Product product : PRODUCT_LIST) {
                        if (product.getNameProduct().equalsIgnoreCase(newNameProduct) &&
                                product.getSize().equalsIgnoreCase(newSize) &&
                                product.getColor().equalsIgnoreCase(newColor)) {
                            checkExistProduct = true;
                        }
                    }
                    if (!checkExistProduct) {
                        PRODUCT_LIST.add(new ProductConcreteBuilder()
                                .setCategoryID(newCategoryID)
                                .setNameProduct(newNameProduct)
                                .setSize(newSize)
                                .setColor(newColor)
                                .setQuantity(newQuantity)
                                .setPrice(newPrice)
                                .setDescribe(newDescribe).build());
                        FileWriterUlti.writeFileProductList(path, PRODUCT_LIST);
                        System.out.println("Đã thêm sản phẩm thành công!");
                    } else {
                        System.out.println("Đã tồn tại sản phẩm này");
                    }
                    return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    } // rồi

    public void removeProductById() {
        displayBasicProductList();
        int productID = Input.choiceIntegerInput("Nhập vào ID sản phẩm bạn muốn xóa: ");
        for (int index = 0; index < PRODUCT_LIST.size(); index++) {
            int valueOfProductID = PRODUCT_LIST.get(index).getProductID();
            if (valueOfProductID == productID) {
                PRODUCT_LIST.remove(index);
                System.out.println("Sản phẩm đã được xóa thành công!");
                break;
            }
        }
        FileWriterUlti.writeFileProductList(path, PRODUCT_LIST);
    } // rồi

    public void editInfoOfProductByID() {
        do {
            try {
                PRODUCT_LIST.forEach(product -> System.out.println("ID sản phẩm: " + product.getProductID()
                        + ", tên sản phẩm: " + product.getNameProduct()
                        + ", size: " + product.getSize()
                        + ", màu sắc: " + product.getColor()
                        + ", số lượng " + product.getQuantity()
                        + ", giá: " + product.getPrice()
                        + ", ID Danh mục: " + product.getCategoryID()
                        + ", Mô tả sản phẩm: " + product.getDescribe()));
                int productID = Input.choiceIntegerInput("Nhập vào ID sản phẩm bạn muốn sửa");
                System.out.println("""
                        Bạn muốn chỉnh sửa thông tin nào của sản phẩm? 
                        1. ID danh mục mà sản phẩm thuộc về
                        2. Tên sản phẩm
                        3. Màu sắc
                        4. Số lượng sản phẩm
                        5. Mô tả sản phẩm
                        6. Giá tiền
                        7. Kích thước
                        8. Cập nhật tất cả các thuộc tính của sản phẩm
                        9. Quay lại
                        """);
                int choice = Input.choiceIntegerInput("Nhập vào lựa chọn của bạn: ");
                switch (choice) {
                    case 1 -> editCategoryIdByProductID(productID);
                    case 2 -> editNameProductByID(productID);
                    case 3 -> editColorByProductID(productID);
                    case 4 -> editQuantityByProductID(productID);
                    case 5 -> editDescribeByID(productID);
                    case 6 -> editPriceByProductID(productID);
                    case 7 -> editSizeByProductID(productID);
                    case 8 -> editAllInfoOfProductByID(productID);
                    case 9 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("Vui lòng nhập lại lựa chọn.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);

    } // chưa coppy

    public void editNameProductByID(int productID) {
        for (Product product : PRODUCT_LIST) {
            if (product.getProductID() == productID) {
                System.out.println("Tên sản phẩm hiện tại là: " + product.getNameProduct());
                String newNameOfProduct = Input.promt("Nhập vào tên mới: ").trim();
                product.setNameProduct(newNameOfProduct);
                break;
            }
        }
        FileWriterUlti.writeFileProductList(path, PRODUCT_LIST);
        System.out.println("Đã cập nhật thành công tên sản phẩm");
    } // roi

    public void editCategoryIdByProductID(int productID) {
        for (Product product : PRODUCT_LIST) {
            if (product.getProductID() == productID) {
                System.out.println("ID danh mục hiện tại là: " + product.getCategoryID());
                int newCategoryId = Input.choiceIntegerInput("Nhập vào ID danh mục mới: ");
                product.setCategoryID(newCategoryId);
                break;
            }
        }
        FileWriterUlti.writeFileProductList(path, PRODUCT_LIST);
        System.out.println("Đã cập nhật thành công Danh mục của sản phẩm");
    } // roi

    public void editColorByProductID(int productID) {
        for (Product product : PRODUCT_LIST) {
            if (product.getProductID() == productID) {
                System.out.println("Màu sắc hiện tại là: " + product.getColor());
                String newColor = Input.promt("Nhập vào màu sắc mới: ").trim();
                product.setColor(newColor);
                break;
            }
        }
        FileWriterUlti.writeFileProductList(path, PRODUCT_LIST);
        System.out.println("Đã cập nhật thành công màu sắc của sản phẩm");
    } // KHÔNG CẦN

    public void editQuantityByProductID(int productID) {
        for (Product product : PRODUCT_LIST) {
            if (product.getProductID() == productID) {
                System.out.println("Số lượng hiện tại là: " + product.getQuantity());
                int newQuantity = Input.choiceIntegerInput("Nhập vào số lượng mới: ");
                product.setQuantity(newQuantity);
                break;
            }
        }
        FileWriterUlti.writeFileProductList(path, PRODUCT_LIST);
        System.out.println("Đã cập nhật thành công số lượng của sản phẩm ");
    } // KHÔNG CẦN

    public void editDescribeByID(int productID) {
        for (Product product : PRODUCT_LIST) {
            if (product.getProductID() == productID) {
                System.out.println("Mô tả sản phẩm hiện tại là: " + product.getDescribe());
                String newDescribe = Input.promt("Nhập vào mô tả mới: ").trim();
                product.setDescribe(newDescribe);
                break;
            }
        }
        FileWriterUlti.writeFileProductList(path, PRODUCT_LIST);
        System.out.println("Đã cập nhật thành công mô tả sản phẩm");
    } // rồi

    public void editPriceByProductID(int productID) {
        for (Product product : PRODUCT_LIST) {
            if (product.getProductID() == productID) {
                System.out.println("Giá tiền hiện tại của sản phẩm là: " + product.getPrice());
                double newPrice = Input.choiceIntegerInput("Nhập vào số giá tiền mới: ");
                product.setPrice(newPrice);
                break;
            }
        }
        FileWriterUlti.writeFileProductList(path, PRODUCT_LIST);
        System.out.println("Đã cập nhật thành công số lượng của sản phẩm ");
    } // KHÔNG CẦN

    public void editSizeByProductID(int productID) {
        for (Product product : PRODUCT_LIST) {
            if (product.getProductID() == productID) {
                System.out.println("Kích thước hiện tại của sản phẩm là: " + product.getSize());
                String newSize = Input.promt("Nhập vào Kích thước mới: ").trim();
                product.setSize(newSize);
                break;
            }
        }
        FileWriterUlti.writeFileProductList(path, PRODUCT_LIST);
        System.out.println("Đã cập nhật thành công kích thước của sản phẩm");
    } // không cần

    public void editAllInfoOfProductByID(int productID) {
        for (Product product : PRODUCT_LIST) {
            if (product.getProductID() == productID) {
                System.out.println("Thông tin hiện tại của sản phẩm là: ");
                System.out.println(product.toString());
                int newCategoryID = Input.choiceIntegerInput("Nhập vào ID danh mục mới mà sản phẩm thuộc về: ");
                String newNameProduct = Input.promt("Nhập vào tên mới cho sản phẩm:").trim();
                String newSize = Input.promt("Nhập vào kích thước mới cho sản phẩm: ").trim();
                String newColor = Input.promt("Nhập vào màu sắc mới cho sản phẩm: ").trim();
                int newQuantity = Input.choiceIntegerInput("Nhập vào số lượng mới cho sản phẩm: ");
                double newPrice = Input.choiceIntegerInput("Nhập và giá mới cho sản phẩm: ");
                String newDescribe = Input.promt("Nhập vào mô tả mới cho sản phẩm: ").trim();
                product.setCategoryID(newCategoryID);
                product.setNameProduct(newNameProduct);
                product.setSize(newSize);
                product.setColor(newColor);
                product.setQuantity(newQuantity);
                product.setPrice(newPrice);
                product.setDescribe(newDescribe);
                break;
            }
        }
        FileWriterUlti.writeFileProductList(path, PRODUCT_LIST);
        System.out.println("Đã cập nhật thành công các thông tin của sản phẩm");
        return;
    } // r

    public void displayProductByCategoryID() {
        newCategoryService.displayCategoryList();
        int categoryID = Input.choiceIntegerInput("Nhập vào ID danh mục mà bạn muốn xem danh sách sản phẩm thuộc về danh mục này; ");
        boolean checkExistProduct = false;
        for (Product product : PRODUCT_LIST) {
            if (product.getCategoryID() == categoryID) {
                checkExistProduct = true;
                System.out.println(product);
            }
            if (!checkExistProduct) {
                System.out.println("Không có sản phẩm thuộc danh mục này.");
            }
        }
    }

    public void displayDetailOfProduct() {
        for (Product product : PRODUCT_LIST) {
            System.out.println(product);
        }
    } // K CẦN

    public void displayBasicProductList() {
        for (Product product : PRODUCT_LIST) {
            System.out.println("ID sản phẩm: " + product.getProductID()
                    + ", tên sản phẩm: " + product.getNameProduct()
                    + ", size: " + product.getSize()
                    + ", màu sắc: " + product.getColor()
                    + ", số lượng " + product.getQuantity()
                    + ", giá: " + product.getPrice());
        }
    }

    public void sortProduct() { // tính năng này chưa hoàn thiện, sửa code lại một xíu
        do {
            try {
                System.out.println("""
                        Bạn muốn sắp xếp danh sách sản phẩm như thế nào?
                        1. Theo giá từ cao đến thấp
                        2. Theo giá từ thấp đến cao
                        3. Theo ngày thêm sản phẩm từ cũ đến mới
                        4. Theo ngày thêm sản phẩm từ mới đến cũ
                        5. Quay lại trang trước
                        """);
                int setChoice = Input.choiceIntegerInput("Enter your choice: ");
                switch (setChoice) {
                    case 1 -> {

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public void findProductByID() {
        int productID = Input.choiceIntegerInput("Nhập vào ID sản phẩm bạn muốn tìm");
        if (productID <= PRODUCT_LIST.size()) {
            for (Product product : PRODUCT_LIST) {
                if (productID == product.getProductID()) {
                    System.out.println(product);
                    break;
                }
            }
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID trên");
        }
    }

    public void findProductByName() {
        String nameProduct = Input.promt("Nhập tên sản phẩm bạn muốn tìm kiếm: ").trim();
        boolean checkExistNameProduct = false;
        for (Product product : PRODUCT_LIST) {
            if (product.getNameProduct().toLowerCase().contains(nameProduct.toLowerCase())) {
                // kiểm tra tên sách có chứa đoạn chuỗi vừa nhập vào hay không
                checkExistNameProduct = true;
                break;
            }
        }
        if (checkExistNameProduct) {
            PRODUCT_LIST.stream().filter(ele -> ele.getNameProduct().toLowerCase()
                    .contains(nameProduct.toLowerCase())).forEach(System.out::println);
            //Duyệt danh sách sản phẩm và lọc ra những phần từ có chứa tên đã nhập vào
        } else {
            System.out.println("Không tìm thấy sản phẩm này");
        }
    }

    public void displayDetailOfBook() {

    }
}
