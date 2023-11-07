package model.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Invoice {
    private int idInvoice;
    private String customerName;
    private String productName;
    private double productPrice;
    private String size;
    private String color;
    private long quantity;
    private Date dayOfPurchase;
    public Invoice(){}
    public String getFormatDayOfPurchase(){
        return new SimpleDateFormat("dd-MM-yyyy").format(dayOfPurchase);
    }

    public Invoice(String customerName, String productName, double productPrice, String size, String color, long quantity, Date dayOfPurchase) {
        this.customerName = customerName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.dayOfPurchase = dayOfPurchase;
    }

    @Override
    public String toString() {
        return  " Tên khách hàng: " + customerName +
                "; Tên sản phẩm: " + productName  +
                "; Giá: " + productPrice + "₫"+
                "; Số lượng" + quantity +
                "; Size: " + size +
                "; Màu sắc" + color +
                "; Ngày mua" + getFormatDayOfPurchase();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Date getDayOfPurchase() {
        return dayOfPurchase;
    }

    public void setDayOfPurchase(Date dayOfPurchase) {
        this.dayOfPurchase = dayOfPurchase;
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
}
