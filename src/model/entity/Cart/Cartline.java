package model.entity.Cart;

import model.service.ProductServiceDTO;

public class Cartline {
    private int cartLineID;
    private int productID;
    private  int variantID;
    private int quantitySelected;
//    private double totalPriceOfUnitProductInCartline;
    private int count =0;

    public Cartline(int cartLineID, int productID, int variantID, int quantitySelected) {
        this.cartLineID = ++count;
        this.productID = productID;
        this.variantID = variantID;
        this.quantitySelected = quantitySelected;
    }
    public Cartline(int productID, int variantID, int quantitySelected) {
        this.cartLineID = ++count;
        this.productID = productID;
        this.variantID = variantID;
        this.quantitySelected = quantitySelected;
    }

    public int getCartLineID() {
        return cartLineID;
    }

    public void setCartLineID(int cartLineID) {
        this.cartLineID = cartLineID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getVariantID() {
        return variantID;
    }

    public void setVariantID(int variantID) {
        this.variantID = variantID;
    }

    public int getQuantitySelected() {
        return quantitySelected;
    }

    public void setQuantitySelected(int quantitySelected) {
        this.quantitySelected = quantitySelected;
    }

    @Override
    public String toString() {
        return "Line:" + cartLineID +
                ", ID sản phẩm: " + productID +
                ", ID Variant: " + variantID +
                ", Số lượng sản phẩm đã chọn: " + quantitySelected + "\n";
    }
}
