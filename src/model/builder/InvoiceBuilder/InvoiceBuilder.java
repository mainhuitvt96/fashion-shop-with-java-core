package model.builder.InvoiceBuilder;

import model.entity.Invoice;

import java.text.ParseException;
import java.util.Date;

public interface InvoiceBuilder {
    InvoiceBuilder setNameCustomer(String customerName);
    InvoiceBuilder setProductName(String productName);
    InvoiceBuilder setProductPrice(double productPrice);


    InvoiceBuilder setSize(String size);
    InvoiceBuilder setColor(String color);

    InvoiceBuilder setQuantity(long quantity);
    InvoiceBuilder setDayOfPurchase(String dayOfPurchase) throws ParseException;

    Invoice build();
}
