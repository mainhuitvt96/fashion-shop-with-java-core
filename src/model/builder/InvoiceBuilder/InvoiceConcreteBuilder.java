package model.builder.InvoiceBuilder;

import model.entity.Invoice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoiceConcreteBuilder implements InvoiceBuilder{
    private String customerName;
    private String productName;
    private double productPrice;
    private String size;
    private String color;
    private long quantity;
    private Date dayOfPurchase;

    @Override
    public InvoiceBuilder setNameCustomer(String customerName) {
        this.customerName = customerName;
        return this;
    }

    @Override
    public InvoiceBuilder setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    @Override
    public InvoiceBuilder setProductPrice(double productPrice) {
        this.productPrice = productPrice;
        return this;
    }

    @Override
    public InvoiceBuilder setSize(String size) {
        this.size = size;
        return this;
    }

    @Override
    public InvoiceBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public InvoiceBuilder setQuantity(long quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public InvoiceBuilder setDayOfPurchase(String dayOfPurchase) throws ParseException {
        this.dayOfPurchase = new SimpleDateFormat("dd/MM/yyyy").parse(dayOfPurchase);
        return this;
    }

    @Override
    public Invoice build() {
        return new Invoice(productName, productName,productPrice,size,color,quantity,dayOfPurchase);
    }
}
