package model.builder.product.ProductDTO;

import model.DTO.ProductDTO;
import model.builder.product.ProductBuilder;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProductConcreteBuilderDTO implements ProductBuilderDTO{
    private int productID;
    private int categoryID;
    private String nameProduct;
    private double price;
    private int numberOfProductPurchase = 0;
    private Date dayAddProduct;
    private String describe;
    @Override
    public ProductBuilderDTO setProductID(int productID) {
        return this;
    }

    @Override
    public ProductBuilderDTO setCategoryID(int categoryID) {
        this.categoryID = categoryID;
        return this;
    }

    @Override
    public ProductBuilderDTO setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
        return this;
    }


    @Override
    public ProductBuilderDTO setPrice(double price) {
        this.price = price;
        return this;    }

    @Override
    public ProductBuilderDTO setNumberOfProductPurchase(int numberOfProductPurchase) {
        this.numberOfProductPurchase = numberOfProductPurchase;
        return this;
    }

    @Override
    public ProductBuilderDTO setDayAddProduct(String dayAddProduct) throws ParseException{
        this.dayAddProduct = new SimpleDateFormat("dd/MM/yyyy").parse(dayAddProduct);
        return this;
    }

    @Override
    public ProductBuilderDTO setDescribe(String describe) {
        this.describe = describe;
        return this;
    }

    @Override
    public ProductDTO build() {
        return new ProductDTO(productID, categoryID, nameProduct, price, numberOfProductPurchase,dayAddProduct, describe);
    }
}
