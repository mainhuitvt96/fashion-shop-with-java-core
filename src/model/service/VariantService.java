package model.service;

import model.DTO.VariantDTO;
import model.entity.Variant;
import model.exeption.InvalidAccountException;
import model.exeption.InvalidChoiceException;
import model.util.FileReaderUlti;
import model.util.FileWriterUlti;
import model.util.Input;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class VariantService {
    private static final VariantService variantService = new VariantService();
    private VariantService(){
    }
    public static VariantService getInstance(){
        return variantService;
    }
    private static final List<Variant> VARIANT_LIST = new ArrayList<>();
    private static final ProductServiceDTO productServiceDTO = ProductServiceDTO.getInstance();
    private static final String path = "src\\model\\data\\variant.csv";
    static {
        try {
            List<Variant> dataVariantList = FileReaderUlti.fileReadingVariant(path);
            VARIANT_LIST.addAll(dataVariantList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void customizeVariant(){
        do {
            try {
                productServiceDTO.displayBasicProductList();
                int inputProductID = Input.choiceIntegerInput("Nhập vào ID sản phẩm bạn muốn thao tác với Variant: ");
                boolean checExistProduct = productServiceDTO.checkExistProductByID(inputProductID);
                boolean checkExistVariant = checkExistVariantByProductID(inputProductID);
                if (checExistProduct == true){
                    displayVariantListByProductID(inputProductID);
                    System.out.println("""
                        --------------------VARIANT--------------------
                        Bạn muốn thao tác gì với Variant:
                        1. Thêm mới variant
                        2. Sửa thông tin variant
                        3. Xóa variant
                        4. Thêm số lượng sản phẩm
                        5. Xem thông tin chi tiết Variant theo ID sản phẩm
                        6. Quay lại
                        """);
                    int choice = Input.choiceIntegerInput("Nhập vào lựa chọn của bạn");
                    switch (choice){
                        case 1 -> addNewVariantByProduct(inputProductID);
                        case 2 -> editVariantInformation(inputProductID);
                        case 3 -> removeVariant(inputProductID);
                        case 4 -> insertQuantity(inputProductID);
                        case 5 -> displayVariantListByProductID(inputProductID);
                        case 6 -> {
                            return;
                        }
                    }
                }else {
                    System.out.println("Không tồn tại sản phẩm có ID: " + inputProductID);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }while (true);

    }
    public void addNewVariantByProduct(int inputProductID){
        do {
            try {
                boolean checkExistVariant = checkExistVariantByProductID(inputProductID);
                if (checkExistVariant == false){
                    System.out.println("Sản phẩm " + inputProductID + " chưa có variant");
                    String size = Input.promt("Nhập vào kích thước mới: ");
                    String color = Input.promt("Nhập vào màu sắc mới: ");
                    int quantity = Input.choiceIntegerInput("Nhập vào số lượng sản phẩm: ");
                    Variant newVariant = new Variant(inputProductID, size, color, quantity);
                    VARIANT_LIST.add(newVariant);
                    FileWriterUlti.writeFileVariantList(path, VARIANT_LIST);
                    System.out.println("Variant đã được thêm vào danh sách");
                } else {
                    displayVariantListByProductID(inputProductID);
                    String size = Input.promt("Nhập vào kích thước mới: ");
                    String color = Input.promt("Nhập vào màu sắc mới: ");
                    int quantity = Input.choiceIntegerInput("Nhập vào số lượng sản phẩm: ");
                    boolean checkExistVariantInlist = false;
                    for (Variant variant: VARIANT_LIST){
                        if (variant.getSize().equalsIgnoreCase(size) &&
                            variant.getColor().equalsIgnoreCase(color) &&
                            variant.getProductID() == inputProductID){
                            checkExistVariantInlist = true;
                            System.out.println("Đã tồn tại Variant này, không thêm mới được");
                            break;
                        }

                    }
                    if (!checkExistVariantInlist){
                        Variant newVariant = new Variant(inputProductID, size, color, quantity);
                        VARIANT_LIST.add(newVariant);
                        FileWriterUlti.writeFileVariantList(path, VARIANT_LIST);
                        System.out.println("Variant đã được thêm vào danh sách");

                    }
                }
                return;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }while (true);
    }
    public void removeVariant(int inputProductID){
        displayVariantListByProductID(inputProductID);
        int variantID = Input.choiceIntegerInput("Nhập ID Variant bạn muốn xóa: ");
        boolean removed = false;
        for (int index = 0; index < VARIANT_LIST.size(); index++){
            int valueOfVariantIDByIndex = (VARIANT_LIST.get(index).getVariantID());
            if (valueOfVariantIDByIndex == variantID){
                VARIANT_LIST.remove(index);
                removed = true;
                break;
            }
        }
        if (removed){
            FileWriterUlti.writeFileVariantList(path, VARIANT_LIST);
            System.out.println("Variant đã được xóa ra khỏi danh sách!");
        } else{
            System.out.println("Không tồn tại Variant: " + variantID +" để xóa.");
        }
    }
    public void displayVariantListByProductID(int productID){
        for (Variant variant: VARIANT_LIST){
            int valueOfProductId = variant.getProductID();
            if (valueOfProductId == productID){
                System.out.println(variant);
            }
        }
    }

    public void displayVariantList(){
        System.out.println("Danh sách Variant: ");
        for (Variant variant: VARIANT_LIST){
            System.out.println(variant);
        }
    }
    public void editVariantInformation(int inputProductID){ // điều chỉnh lại chức năng của function này
        do {
            try {
                displayVariantListByProductID(inputProductID);
                int id = Input.choiceIntegerInput("Nhập ID variant bạn muốn sửa: ");
                if (checkExistVariantByID(id) == true){
                    System.out.println("""
                        Bạn muốn chỉnh sửa thông tin nào của Variant trên:
                        1. Kích thước sản phẩm
                        2. Màu sắc
                        3. Số lượng
                        4. Quay lại
                        """);
                    int choice = Input.choiceIntegerInput("Nhập vào lựa chọn: ");
                    switch (choice){
                        case 1 -> editVriantSize(id);
                        case 2 -> editVriantColor(id);
                        case 3 -> editVriantQuantity(id);
                        case 4 -> {
                            return;
                        }
                        default -> throw new  InvalidChoiceException("Lựa chọn không hợp lệ");
                    }
                }else {
                    System.out.println("Không tồn tại Variant với ID " + id);
                }
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }while (true);
    }
    public boolean checkExistVariantByID(int variantID){
        boolean checkExistVariant = false;
        for (Variant variant: VARIANT_LIST){
            int valueOfVariantID = variant.getVariantID();
            if (valueOfVariantID == variantID){
                checkExistVariant = true;
                break;
            }
        }
        return checkExistVariant;
    }
    public boolean checkExistVariantByProductID(int productID){
        boolean checkExist = false;
        for (Variant variant: VARIANT_LIST){
            if (variant.getProductID() == productID){
                checkExist = true;
            }
        }
        return checkExist;
    }
    public void editVriantSize(int variantID) throws Exception {

        for (Variant variant: VARIANT_LIST){
            int valueOfVariantIDByIndex = variant.getVariantID();
            if (valueOfVariantIDByIndex == variantID){
                System.out.println("Kích thước hiện tại là: " + variant.getSize().trim());
                String editSize = Input.promt("Nhập vào kích thước mới: ").trim();
                variant.setSize(editSize);
                break;
            }
        }
        FileWriterUlti.writeFileVariantList(path, VARIANT_LIST);
        System.out.println("Đã chỉnh sửa thành công");
    }
    public void editVriantColor(int variantID) throws Exception {
        for (int index = 0; index < VARIANT_LIST.size(); index++){
            Variant variant = VARIANT_LIST.get(index);
            int valueOfVariantID = variant.getVariantID();
            if ((valueOfVariantID == variantID)){
                System.out.println("Màu sắc hiện tại là: " + variant.getColor().trim());
                String editColor = Input.promt("Nhập vào màu mới: ").trim();
                variant.setColor(editColor.trim());
                VARIANT_LIST.set(index,variant);
                break;
            }
        }
        FileWriterUlti.writeFileVariantList(path, VARIANT_LIST);
        System.out.println("Đã chỉnh sửa thành công");
    }
    public void editVriantQuantity(int variantID) throws Exception {
        for (int index = 0; index < VARIANT_LIST.size(); index++){
            Variant variant = VARIANT_LIST.get(index);
            int valueOfVariantID = variant.getVariantID();
            if ((valueOfVariantID == variantID)){
                System.out.println("Số lượng hiện tại là: " + variant.getQuantity());
                int editQuantity = Input.choiceIntegerInput("Nhập vào số lượng mới: ");
                variant.setQuantity(editQuantity);
                VARIANT_LIST.set(index,variant);
                break;
            }
        }
        FileWriterUlti.writeFileVariantList(path, VARIANT_LIST);
        System.out.println("Đã chỉnh sửa thành công");
    }
    public void insertQuantity(int inputProductID){
        displayVariantListByProductID(inputProductID);
        int variantID = Input.choiceIntegerInput("Nhập vào Variant bạn muốn thêm số lượng:");
        for (int index = 0; index < VARIANT_LIST.size(); index++){
            Variant variant = VARIANT_LIST.get(index);
            int valueOfVariantID = variant.getVariantID();
            if (valueOfVariantID == variantID){
                System.out.println("Số lượng sản phẩm hiện tại là: " + variant.getQuantity());
                int inputQuantity = Input.choiceIntegerInput("Nhập vào số lượng bạn muốn thêm: ");
                int insertQuantity = variant.getQuantity() + inputQuantity;
                variant.setQuantity(insertQuantity);
                VARIANT_LIST.set(index,variant);
                break;
            }
        }
        FileWriterUlti.writeFileVariantList(path, VARIANT_LIST);
        System.out.println("Đã thêm số lượng thành công");
    }
    public  Variant getVariantByID(int id){
        for (Variant variant: VARIANT_LIST){
            if (variant.getVariantID() == id){
                return variant;
            }
        }
        return null;
    }
    public boolean checkZeroQuantity(int variantID){
        boolean check = false;
        for (Variant variant: VARIANT_LIST){
            if (variant.getVariantID() == variantID){
                if (variant.getQuantity() == 0) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }
    public Boolean checkLessThenQuanktity(int variantID, int quantity){
        boolean check = false;
        for (Variant variant: VARIANT_LIST){
            if (variant.getVariantID() == variantID){
                if (variant.getQuantity() < quantity) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }
}
