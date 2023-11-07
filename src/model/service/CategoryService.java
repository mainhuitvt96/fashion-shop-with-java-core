package model.service;
import model.entity.Category;
import model.exeption.InvalidChoiceException;
import model.util.FileReaderUlti;
import model.util.FileWriterUlti;
import model.util.Input;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private static final CategoryService CATEGORY_SERVICE = new CategoryService();
    public CategoryService(){

    }
    public static CategoryService getInstance(){ return CATEGORY_SERVICE;}
    public static final String path = "src\\model\\data\\catagory.csv";
    private static final List<Category> CATEGORY_LIST = new ArrayList<>();
    static {
        try {
            List<Category> dataCategoryList = FileReaderUlti.fileReadingCategory(path);
            CATEGORY_LIST.addAll(dataCategoryList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  List<Category> getCategoryList(){
        return CATEGORY_LIST;
    }

    public void customizeCategory(){
        do {
            try {
                System.out.println("""
                Mời bạn lựa chọn thao tác với Danh mục:
                1. Hiển thị danh sách các danh mục
                2. Thêm mới danh mục vào danh sách
                3. Xóa danh mục dựa theo ID
                4. Chỉnh sửa tên danh mục dựa theo ID
                5. Quay lại
                """);
                int choice = Input.choiceIntegerInput("Mời bạn nhập vào lựa chọn: ");
                switch (choice){
                    case 1 -> displayCategoryList();
                    case 2 -> addNewCategory();
                    case 3 -> removeCategoryByID();
                    case 4 -> editNameCategoryByID();
                    case 5 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("Vui lòng nhập lại lựa chọn");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }while (true);

    }

    public void addNewCategory(){
    do{
        try {
            displayCategoryList();
            String categoryName = Input.promt("Nhập vào tên danh mục mới: ");
            boolean checkExistCategoryName = false;
            for (Category category: CATEGORY_LIST) {
                String valueOfCategoryName = category.getName();
                if (valueOfCategoryName.equalsIgnoreCase(categoryName.trim())) {
                    checkExistCategoryName = true;
                    break;
                }
                if (!checkExistCategoryName){
                    Category newCategory = new Category(category.getIDCategory(), categoryName);
                    CATEGORY_LIST.add(newCategory);
                    FileWriterUlti.writeFileCategoryList(path, CATEGORY_LIST);
                    System.out.println("Đã thêm danh mục mới");
                    return;
                } else {
                    System.out.println("Đã tồn tại danh mục này");
                    return;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }while (true);


    }

    public void removeCategoryByID(){
        displayCategoryList();
        int categoryID = Input.choiceIntegerInput("Nhập ID danh mục bạn muốn xóa: ");
        boolean removed = false;
        for (int index = 0; index < CATEGORY_LIST.size(); index++){
            int valueOfCategoryIDByIndex = (CATEGORY_LIST.get(index).getIDCategory());
            if (valueOfCategoryIDByIndex == categoryID){
                CATEGORY_LIST.remove(index);
                removed = true;
                break;
            }
        }
        if (removed){
            FileWriterUlti.writeFileCategoryList(path, CATEGORY_LIST);
            System.out.println("Danh mục đã được xóa ra khỏi danh sách!");
        } else{
            System.out.println("Xóa không thành công!");
        }
    }
    public boolean checkExistCategoryID(int categoryID){
        boolean checkExistCategoryID = false;
        for (Category category: CATEGORY_LIST) {
            int valueOfCategoryID = category.getIDCategory();
            if (valueOfCategoryID == categoryID) {
                checkExistCategoryID = true;
                break;
            }
        }
        return checkExistCategoryID;
    }

    public void displayCategoryList(){
        System.out.println("Danh sách Danh Mục: ");
        for (Category category: CATEGORY_LIST){
            System.out.println(
                    "Category ID: " + category.getIDCategory()
                            +", Name of category : " + category.getName());
        }
    }
    public void editNameCategoryByID() throws Exception {
        displayCategoryList();
        int inputProductID = Input.choiceIntegerInput("Nhập vào ID danh mục bạn muốn chỉnh sửa: ");
        for (Category category: CATEGORY_LIST){
            int valueOfCategoryIDByIndex = category.getIDCategory();
            if (valueOfCategoryIDByIndex == inputProductID){
                System.out.println("Tên danh mục hiện tại là: " + category.getName());
                String editNameCategory = Input.promt("Nhập vào tên danh mục mới: ");
                category.setName(editNameCategory);
                System.out.println("Đã chỉnh sửa thành công cho danh mục có ID là " +  category.getIDCategory());
                break;
            }
        }
        FileWriterUlti.writeFileCategoryList(path,CATEGORY_LIST);

    }
}
