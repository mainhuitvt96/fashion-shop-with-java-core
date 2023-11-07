package model.builder.product.ProductDTO;

import model.DTO.ProductDTO;
import model.builder.product.ProductBuilder;
import model.entity.Product;

import java.text.ParseException;
import java.util.Date;

public interface ProductBuilderDTO {
    ProductBuilderDTO setProductID(int productID);
    ProductBuilderDTO setCategoryID(int color);
    ProductBuilderDTO setNameProduct(String nameProduct);
    ProductBuilderDTO setPrice (double price);
    ProductBuilderDTO setNumberOfProductPurchase(int numberOfProductPurchase);
    ProductBuilderDTO setDayAddProduct(String dayAddProduct) throws ParseException;
    ProductBuilderDTO setDescribe (String describe);
    ProductDTO build();
}
