package Test;

import model.service.ProductService;

public class TestproductService {
    public static void main(String[] args) {
        ProductService productService = ProductService.getInstance();
        productService.customizeProduct();

    }
}
