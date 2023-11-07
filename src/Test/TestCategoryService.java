package Test;

import model.service.CategoryService;

public class TestCategoryService {
    public static void main(String[] args) {
        CategoryService categoryService = new CategoryService();
        categoryService.customizeCategory();
    }
}
