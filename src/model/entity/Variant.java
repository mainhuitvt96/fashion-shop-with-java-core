package model.entity;

public class Variant {
    private int variantID;
    private int productID;
    private String size;
    private String color;
    private int quantity;
    private static int count = 0;

    public Variant(int variantID, String size, String color) {
        this.variantID = ++count;
        this.size = size;
        this.color = color;
    }

    public Variant(String size, String color) {
        this.variantID = ++count;
        this.size = size;
        this.color = color;
    }

    public Variant(int productID, String size, String color, int quantity) {
        this.variantID = ++count;
        this.productID = productID;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }

    public Variant(int variantID, int productID, String size, String color, int quantity) {
        this.variantID = variantID;
        this.productID = productID;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }

    public int getVariantID() {
        return variantID;
    }

    public void setVariantID(int variantID) {
        this.variantID = variantID;
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

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setQuantityPurchase(int numberProductPurchase) {
        this.quantity -= numberProductPurchase;
    }

    @Override
    public String toString() {
        return "Thông tin Variant: " +
                " VariantID: " + variantID +
                ", ID sản phẩm: " + productID +
                ", Size: " + size +
                ", Color: " + color +
                ", Quantity: " + quantity;
    }
}
