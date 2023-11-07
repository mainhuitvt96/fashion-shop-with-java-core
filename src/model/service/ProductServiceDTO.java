package model.service;

import model.DTO.ProductDTO;
import model.builder.product.ProductConcreteBuilder;
import model.builder.product.ProductDTO.ProductConcreteBuilderDTO;
import model.entity.Cart.Cartline;
import model.entity.Product;
import model.entity.Variant;
import model.exeption.InvalidChoiceException;
import model.util.FileReaderUlti;
import model.util.FileWriterUlti;
import model.util.Input;
import model.util.ReturnFunction;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ProductServiceDTO {
    private static final ProductServiceDTO productServiceDTO = new ProductServiceDTO();
    private  static  final CategoryService newCategoryService = CategoryService.getInstance();
    private  static  final VariantService  variantService = VariantService.getInstance();
    private  ProductServiceDTO(){
    }
    public static ProductServiceDTO  getInstance(){
        return productServiceDTO;
    }
    private static final List<ProductDTO> PRODUCT_LIST = new ArrayList<>();
    private static final List<Cartline> CARTLINE_LIST = new ArrayList<>();
    public List<Cartline> getCartlineList(){
        return CARTLINE_LIST;
    }
    //private static final List<Product> PRODUCT_CART = new ArrayList<>();
    private static final String path = "src\\model\\data\\product_DTO.csv";
    private static final String pathOfCartline = "src\\model\\data\\cartline.csv";

    static {
        try {
            List<ProductDTO> productList = FileReaderUlti.fileReadingProductDTO(path);
            PRODUCT_LIST.addAll(productList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<ProductDTO> getProductList(){
        return PRODUCT_LIST;
    }
    public List<Cartline> showAllCart(){
        return CARTLINE_LIST;
    }
    public void selectItemToBuy(){
        displayBasicProductList();
        int productId = Input.choiceIntegerInput("Nhập id sản phẩm muốn mua: ");
        boolean checkExistVariant = VariantService.getInstance().checkExistVariantByProductID(productId);
        if (checkExistVariant == true) {
            VariantService.getInstance().displayVariantListByProductID(productId);
            int variantId = Input.choiceIntegerInput("Nhập id variant muốn mua: ");
            int quantity = Input.choiceIntegerInput("Nhập số lượng muốn mua: ");
                     Cartline cartline = new Cartline(productId, variantId, quantity);
            CARTLINE_LIST.add(cartline);
            System.out.println("Đã thêm sản phẩm thành công.");
        } else {
            System.out.println("Id variant không tồn tại");
        }

        //FileWriterUlti.writeFileCartLineList(pathOfCartline, CARTLINE_LIST);
        //Không cần dòng này vì mỗi lần thanh toán xong thì sẽ remove giỏ hàng --> để dành phát triển tính năng này về sau.
    }
    public void customizeProduct(){
        do {
            try{
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
                switch (choice){
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
            }catch (Exception e){
                e.printStackTrace();
            }
        }while (true);
    }
    public void editInfoOfProductByID(){
        do {
            try{
                PRODUCT_LIST.forEach(product -> System.out.println(product.toString()));
                int productID = Input.choiceIntegerInput("Nhập vào ID sản phẩm bạn muốn sửa");
                System.out.println("""
                Bạn muốn chỉnh sửa thông tin nào của sản phẩm? 
                1. ID danh mục mà sản phẩm thuộc về
                2. Tên sản phẩm
                3. Mô tả sản phẩm
                4. Cập nhật tất cả các thuộc tính của sản phẩm
                5. Quay lại
                """);
                int choice = Input.choiceIntegerInput("Nhập vào lựa chọn của bạn: ");
                switch (choice){
                    case 1 -> editCategoryIdByProductID(productID);
                    case 2 -> editNameProductByID(productID);
                    case 3 -> editDescribeByID(productID);
                    case 4 -> editAllInfoOfProductByID(productID);
                    case 5 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("Vui lòng nhập lại lựa chọn.");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }while (true);

    }
    public void addNewProduct(){
        do {
            try {

                newCategoryService.displayCategoryList();
                int newCategoryID = Input.choiceIntegerInput("Nhập vào ID của danh mục mà sản phẩm thuộc về: ");
                //productID, categoryID, nameProduct, price, numberOfProductPurchase, dayAddProduct, describe
                String newNameProduct = Input.promt("Nhập vào tên sản phẩm: ").trim();
                double newPrice = Input.choiceIntegerInput("Nhập vào giá tiền: ");
                int numberOfProductPurchase = 0;
                String dayAddProduct = Input.promt("Nhập vào ngày thêm sản phẩm: ").trim();
                String newDescribe = Input.promt("Nhập vào mô tả sản phẩm: ").trim();
                if (PRODUCT_LIST.isEmpty()){
                    PRODUCT_LIST.add(new ProductConcreteBuilderDTO()
                            .setCategoryID(newCategoryID)
                            .setNameProduct(newNameProduct)
                            .setPrice(newPrice)
                            .setNumberOfProductPurchase(numberOfProductPurchase)
                            .setDayAddProduct(dayAddProduct)
                            .setDescribe(newDescribe).build());
                    FileWriterUlti.writeFileProductListDTO(path, PRODUCT_LIST);
                    System.out.println("Thêm sản phẩm thành công");
                }else {
                    boolean checkExistProduct= false;
                    for (ProductDTO product: PRODUCT_LIST){
                        if (product.getNameProduct().equalsIgnoreCase(newNameProduct) &&
                            product.getPrice() == newPrice){
                            checkExistProduct = true;
                        }
                    }
                    if (!checkExistProduct){
                        PRODUCT_LIST.add(new ProductConcreteBuilderDTO()
                                .setCategoryID(newCategoryID)
                                .setNameProduct(newNameProduct)
                                .setPrice(newPrice)
                                .setNumberOfProductPurchase(numberOfProductPurchase)
                                .setDayAddProduct(dayAddProduct)
                                .setDescribe(newDescribe).build());
                        FileWriterUlti.writeFileProductListDTO(path, PRODUCT_LIST);
                        System.out.println("Thêm sản phẩm thành công");
                    } else {
                        System.out.println("Đã tồn tại sản phẩm này");
                    }
                    return;
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }while (true);
    }
    public void removeProductById(){
        displayBasicProductList();
        int productID = Input.choiceIntegerInput("Nhập vào ID sản phẩm bạn muốn xóa: ");
        for (int index = 0; index < PRODUCT_LIST.size(); index++){
            int valueOfProductID = PRODUCT_LIST.get(index).getProductID();
            if (valueOfProductID == productID){
                PRODUCT_LIST.remove(index);
                System.out.println("Sản phẩm đã được xóa thành công!");
                break;
            }
        }
        FileWriterUlti.writeFileProductListDTO(path, PRODUCT_LIST);
    }
    public void displayBasicProductList(){
        for (ProductDTO product: PRODUCT_LIST){
            System.out.println("ID sản phẩm: " + product.getProductID()
                    + ", tên sản phẩm: " + product.getNameProduct()
                    + ", giá: " + product.getPrice()
                    + ", ngày thêm sản phẩm: " + product.getDayAddProduct()
                    + ", số lượng đã bán: " + product.getNumberOfProductPurchase() + "\n");
        }
    }
    public boolean checkExistProductByID(int inputProductID){
        boolean checkExist = false;
        for (ProductDTO product: PRODUCT_LIST){
            int valueOfProductID = product.getProductID();
            if (valueOfProductID == inputProductID){
                checkExist = true;
                break;
            }
        }
        return checkExist;
    }
    public void editNameProductByID(int productID){
        for (ProductDTO product: PRODUCT_LIST){
            if (product.getProductID() == productID){
                System.out.println("Tên sản phẩm hiện tại là: " + product.getNameProduct());
                String newNameOfProduct = Input.promt("Nhập vào tên mới: ").trim();
                product.setNameProduct(newNameOfProduct);
                break;
            }
        }
        FileWriterUlti.writeFileProductListDTO(path, PRODUCT_LIST);
        System.out.println("Đã cập nhật thành công tên sản phẩm");
    }
    public void editCategoryIdByProductID (int productID){
        for (ProductDTO product: PRODUCT_LIST){
            if (product.getProductID() == productID){
                System.out.println("ID danh mục hiện tại là: " + product.getCategoryID());
                int newCategoryId = Input.choiceIntegerInput("Nhập vào ID danh mục mới: ");
                product.setCategoryID(newCategoryId);
                break;
            }
        }
        FileWriterUlti.writeFileProductListDTO(path, PRODUCT_LIST);
        System.out.println("Đã cập nhật thành công Danh mục của sản phẩm");
    }
    public void editDescribeByID(int productID){
        for (ProductDTO product: PRODUCT_LIST){
            if (product.getProductID() == productID){
                System.out.println("Mô tả sản phẩm hiện tại là: " + product.getDescribe());
                String newDescribe = Input.promt("Nhập vào mô tả mới: ").trim();
                product.setDescribe(newDescribe);
                break;
            }
        }
        FileWriterUlti.writeFileProductListDTO(path, PRODUCT_LIST);
        System.out.println("Đã cập nhật thành công mô tả sản phẩm");
    }
    public ProductDTO getProductById(int id) {
        for ( ProductDTO productDTO : PRODUCT_LIST) {
            if (productDTO.getProductID() == id) {
                return productDTO;
            }
        }
        return null;
    }
    public void findProductByName(){
        String nameProduct = Input.promt("Nhập tên sản phẩm bạn muốn tìm kiếm: ").trim();
        boolean checkExistNameProduct = false;
        for (ProductDTO product: PRODUCT_LIST){
            if (product.getNameProduct().toLowerCase().contains(nameProduct.toLowerCase())){
                // kiểm tra tên sách có chứa đoạn chuỗi vừa nhập vào hay không
                checkExistNameProduct = true;
                break;
            }
        }
        if (checkExistNameProduct){
            PRODUCT_LIST.stream().filter(ele -> ele.getNameProduct().toLowerCase()
                    .contains(nameProduct.toLowerCase())).forEach(System.out::println);
            //Duyệt danh sách sản phẩm và lọc ra những phần từ có chứa tên đã nhập vào
        } else {
            System.out.println("Không tìm thấy sản phẩm này");
        }
    }
    public void findProductByID(){
        int productID = Input.choiceIntegerInput("Nhập vào ID sản phẩm bạn muốn tìm");
        if (productID <= PRODUCT_LIST.size()){
            for (ProductDTO product: PRODUCT_LIST){
                if (productID == product.getProductID()){
                    System.out.println(product);
                    break;
                }
            }
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID trên");
        }
    }
    public void editAllInfoOfProductByID (int productID) throws ParseException {
        for (ProductDTO product: PRODUCT_LIST){
            if (product.getProductID() == productID){
                System.out.println("Thông tin hiện tại của sản phẩm là: ");
                System.out.println(product.toString());
                int newCategoryID = Input.choiceIntegerInput("Nhập vào ID danh mục mới mà sản phẩm thuộc về: ");
                String newNameProduct = Input.promt("Nhập vào tên mới cho sản phẩm:").trim();
                double newPrice = Input.choiceIntegerInput("Nhập và giá mới cho sản phẩm: ");
                String dayAddProduct = Input.promt("Nhập vào ngày theo định dạng (dd/MM/yyyy)");
                String newDescribe = Input.promt("Nhập vào mô tả mới cho sản phẩm: ").trim();
                product.setCategoryID(newCategoryID);
                product.setNameProduct(newNameProduct);
                product.setPrice(newPrice);
                product.setDayAddProduct(dayAddProduct);
                product.setDescribe(newDescribe);
                break;
            }
        }
        FileWriterUlti.writeFileProductListDTO(path, PRODUCT_LIST);
        System.out.println("Đã cập nhật thành công các thông tin của sản phẩm");
        return;
    }
    public void displayProductByCategoryID(){
        newCategoryService.displayCategoryList();
        int categoryID = Input.choiceIntegerInput("Nhập vào ID danh mục mà bạn muốn xem danh sách sản phẩm thuộc về danh mục này: ");
        boolean checkExistProduct = false;
        for (ProductDTO product: PRODUCT_LIST){
            if (product.getCategoryID() == categoryID){
                checkExistProduct = true;
                System.out.println(product);
            }
        }
        if (!checkExistProduct){
            System.out.println("Không có sản phẩm thuộc danh mục này.");
        }
    }
    public void displayDetailOfProduct() {
        do {
            try {
                PRODUCT_LIST.stream().forEach(ProductDTO -> System.out.println("ID: " + ProductDTO.getProductID() + ". " + ProductDTO.getNameProduct()));
                System.out.println("""
                                                
                        1. Hiển thị thông tin sản phẩm theo ID hoặc tên
                        2. Mua sản phẩm theo ID
                        3. Quay lại
                        """);
                int choice = Input.choiceIntegerInput("Nhập lựa chọn: ");
                switch (choice) {
                    case 1:
                        System.out.println("""
                                Bạn muốn tìm theo?
                                1. ID
                                2. Name
                                """);
                        int searchChoice = Input.choiceIntegerInput("Nhập lựa chọn: ");
                        switch (searchChoice) {
                            case 1:
                                int id = Input.choiceIntegerInput("Nhập ID: ");
                                if (id <= PRODUCT_LIST.size()) {
                                    for (ProductDTO productDTO : PRODUCT_LIST) {
                                        if (id == productDTO.getProductID()) {
                                            System.out.println(productDTO);
                                            break;
                                        }
                                    }
                                } else System.out.println("Không tìm thấy lựa chọn trên!");
                                break;
                            case 2:
                                String productName = Input.promt("Nhập tên sản phẩm: ");
                                boolean existBookName = false;
                                for (ProductDTO productDTO : PRODUCT_LIST) {
                                    if (productDTO.getNameProduct().toUpperCase().contains(productName.toUpperCase())) {
                                        existBookName = true;
                                        break;
                                    }
                                }
                                if (existBookName) {
                                    PRODUCT_LIST.stream().filter(ele -> ele.getNameProduct().toUpperCase().contains(productName.toUpperCase())).forEach(System.out::println);
                                } else System.out.println("Không tìm thấy nội dung trên");
                                break;
                            default:
                                throw new InvalidChoiceException("Lựa chọn không hợp lệ");
                        }
                        ReturnFunction.backPage();
                        break;
                    case 2:
                        selectItemToBuy();
                        break;
                    case 3:
                        return;
                    default:
                        throw new InvalidChoiceException("Lựa chọn không hợp lệ");
                }
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
    public void sortProduct() {
        do {
            try {
                System.out.print("""
                        Bạn muốn sắp xếp sản phẩm như thế nào?
                        1. Từ rẻ tới đắt
                        2. Từ đắt tới rẻ
                        3. Từ cũ nhất tới mới nhất
                        4. Từ mới nhất tới cũ nhất
                        5. Best seller
                        6. Quay lại trang trước
                        """);
                int setChoice = Input.choiceIntegerInput("Enter your choice: ");
                switch (setChoice) {
                    case 1:
                        PRODUCT_LIST.sort(Comparator.comparingDouble(ProductDTO::getPrice));
                        for (ProductDTO productDTO : PRODUCT_LIST) {
                            System.out.println("ID: " + productDTO.getProductID() + ". " + productDTO.getNameProduct() + " | Giá tiền: " +productDTO.getPrice() + "₫");
                        }
                        ReturnFunction.backPage();
                        break;
                    case 2:
                        PRODUCT_LIST.sort(Comparator.comparing(ProductDTO::getPrice).reversed());
                        for (ProductDTO productDTO : PRODUCT_LIST) {
                            System.out.println("ID: " + productDTO.getProductID() + ". " + productDTO.getNameProduct() + " | Giá tiền: " +productDTO.getPrice() + "₫");
                        }
                        ReturnFunction.backPage();
                        break;
                    case 3:
                        PRODUCT_LIST.stream().sorted(Comparator.comparing(ProductDTO::getDayAddProduct))
                                .forEach(ProductDTO -> System.out.println("ID: " + ProductDTO.getProductID() + ". "
                                        + ProductDTO.getNameProduct()
                                        + " | Ngày thêm sản phẩm: " + ProductDTO.getDayAddProduct()));
                        ReturnFunction.backPage();
                        break;
                    case 4:
                        PRODUCT_LIST.stream().sorted(Comparator.comparing(ProductDTO::getDayAddProduct).reversed())
                                .forEach(ProductDTO -> System.out.println("ID: " + ProductDTO.getProductID() + ". "
                                        + ProductDTO.getNameProduct()
                                        + " | Ngày thêm sản phẩm: " + ProductDTO.getDayAddProduct()));
                        ReturnFunction.backPage();
                        break;
                    case 5:
                        PRODUCT_LIST.sort(Comparator.comparingDouble(ProductDTO::getNumberOfProductPurchase).reversed());
                        for (int indexOfProductList = 0; indexOfProductList < 10; indexOfProductList++) {
                            System.out.println("ID: " + PRODUCT_LIST.get(indexOfProductList).getProductID() + ". "
                                    + PRODUCT_LIST.get(indexOfProductList).getNameProduct()
                                    + " Số lượng đã bán: " + PRODUCT_LIST.get(indexOfProductList).getNumberOfProductPurchase());
                        }
                        ReturnFunction.backPage();
                    case 6:
                        return;
                    default:
                        throw new InvalidChoiceException("Lựa chọn không hợp lệ");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}




