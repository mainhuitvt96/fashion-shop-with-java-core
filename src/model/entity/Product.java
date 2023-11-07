package model.entity;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Product {
    private int productID;
    private int categoryID;
    private String nameProduct;
    private String size;
    private String color;
    private int quantity;
    private double price;
    private int numberOfProductPurchase = 0;
    private Date dayAddProduct;
    private String describe;
    private static int count = 0;


    //Định dạng tiền
    public  static Locale locale = new Locale("vi", "VN");
    public  static NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

    public int getProductID() {
        return productID;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Product.count = count;
    }

    public Product(int productID, int categoryID, String nameProduct, String size, String color, int quantity, double price, String describe) {
        this.productID = ++count;
        this.categoryID = categoryID;
        this.nameProduct = nameProduct;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.describe = describe;
    }
    public Product (){
    }

    @Override
    public String toString() {
        return "THÔNG TIN SẢN PHẨM"
                + "\n" + "✨ Sản phẩm: " + productID
                + "\n" + "❒ Tên sản phẩm: " + nameProduct
                + "\n" + "❒ ID Danh mục: " + getCategoryID()
                + "\n" + "❒ Mô tả: " + getDescribe()
                + "\n" + "❒ Kích cỡ: " + size
                + "\n" + "❒ Màu sắc: " + color
                + "\n" + "❒ Số lượng trong kho: " + quantity
                + "\n" + "❒ Giá tiền: " + numberFormat.format(price)
                + "\n" + "----------------------------------------------------";

    }
}
