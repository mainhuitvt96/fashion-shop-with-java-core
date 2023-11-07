package model.builder.product;

import model.entity.Product;

public interface ProductBuilder {
    ProductBuilder setProductID(int productID);
    ProductBuilder setCategoryID(int categoryID);
    ProductBuilder setNameProduct(String nameProduct);
    ProductBuilder setSize (String size);
    ProductBuilder setColor (String color);
    ProductBuilder setQuantity (int quantity);
    ProductBuilder setPrice (double price);
    ProductBuilder setDescribe (String describe);
    ProductBuilder setNumberOfProductPurchase (int numberOfProductPurchase);
    Product build();
}
