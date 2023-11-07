package model.builder.product;

import model.entity.Product;

public class ProductConcreteBuilder implements ProductBuilder{
    private int productID;
    private int categoryID;
    private String nameProduct;
    private String size;
    private String color;
    private int quantity; // số lượng
    int numberOfProductPurchase =0;
    private double price;
    private String describe; // mô tả
    @Override
    public ProductBuilder setProductID(int productID) {
        return this;
    }

    @Override
    public ProductBuilder setCategoryID(int categoryID) {
        this.categoryID = categoryID;
        return this;
    }

    @Override
    public ProductBuilder setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
        return this;
    }

    @Override
    public ProductBuilder setSize(String size) {
        this.size = size;
        return this;
    }

    @Override
    public ProductBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public ProductBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public ProductBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    @Override
    public ProductBuilder setDescribe(String describe) {
        this.describe= describe;
        return this;
    }

    @Override
    public ProductBuilder setNumberOfProductPurchase(int numberOfProductPurchase) {
        this.numberOfProductPurchase= numberOfProductPurchase;
        return this;
    }

    @Override
    public Product build() {
        return new Product(productID, categoryID, nameProduct, size, color, quantity, price, describe);
    }
}
