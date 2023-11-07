package model.DTO;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProductDTO {
    private int productID;
    private int categoryID;
    private String nameProduct;
    private double price;
    private int numberOfProductPurchase = 0; // số lượng sản phẩm đã bán
    private Date dayAddProduct;
    private String describe;
    private static int count = 0;


    //Định dạng tiền
    public  static Locale locale = new Locale("vi", "VN");
    public  static NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        public int getProductID() {
        return productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getNameProduct() {
        return nameProduct;
    }


    public int getNumberOfProductPurchase() {
        return numberOfProductPurchase;
    }

    public String getDayAddProduct() {
        return new SimpleDateFormat("dd/MM/yyyy").format(dayAddProduct);
    }

    public String getDescribe() {
        return describe;
    }

    public static int getCount() {
        return count;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumberOfProductPurchase(int numberOfProductPurchase) {
        this.numberOfProductPurchase += numberOfProductPurchase;
    }

    public void setDayAddProduct(String dayAddProduct) throws ParseException {
        this.dayAddProduct = new SimpleDateFormat("dd/MM/yyyy").parse(dayAddProduct);
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public ProductDTO(int productID, int categoryID, String nameProduct, double price,
                      int numberOfProductPurchase, Date dayAddProduct, String describe) {
        this.productID = ++count;
        this.categoryID = categoryID;
        this.nameProduct = nameProduct;
        this.price = price;
        this.numberOfProductPurchase = numberOfProductPurchase;
        this.dayAddProduct = dayAddProduct;
        this.describe = describe;
    }
    public ProductDTO(int categoryID, String nameProduct, double price,
                      int numberOfProductPurchase, Date dayAddProduct, String describe) {
        this.productID = ++count;
        this.categoryID = categoryID;
        this.nameProduct = nameProduct;
        this.price = price;
        this.numberOfProductPurchase = numberOfProductPurchase;
        this.dayAddProduct = dayAddProduct;
        this.describe = describe;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "THÔNG TIN SẢN PHẨM"
                + "\n" + "✨ Sản phẩm: " + productID
                + "\n" + "❒ Tên sản phẩm: " + nameProduct
                + "\n" + "❒ ID Danh mục: " + getCategoryID()
                + "\n" + "❒ Giá tiền: " + numberFormat.format(price)
                + "\n" + "❒ Số lượng đã bán: " + getPrice()
                + "\n" + "❒ Ngày thêm sản phẩm: " + dayAddProduct
                + "\n" + "❒ Mô tả: " + getDescribe()
                + "\n" + "----------------------------------------------------";

    }

}
