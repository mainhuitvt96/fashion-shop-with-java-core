package model.util;

import model.DTO.ProductDTO;
import model.builder.InvoiceBuilder.InvoiceConcreteBuilder;
import model.entity.Cart.Cartline;
import model.entity.Category;
import model.entity.Invoice;
import model.entity.Product;
import model.entity.User.abstraction.User;
import model.entity.Variant;
import model.service.ProductServiceDTO;
import model.service.VariantService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class FileWriterUlti {
    public static void writeFileUser(String path, User user){
        try(FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter writer = new BufferedWriter(fileWriter)){
            writer.write(user.getUserName() + "; "
                            +user.getFullName() + "; "
                            +user.getPhoneNumber() + "; "
                            +user.getEmail() + "; "
                            +user.getPassWord() + "; "
                            +user.getAddress()+ "; " +  "\n");

        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public static void writeFileVariant(String path, Variant newVariant) {
        try (FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter writer = new BufferedWriter(fileWriter)){
            writer.write(newVariant.getVariantID()+ ";"
                            + newVariant.getSize() + "; "
                            + newVariant.getColor() + "\n");
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    public static void writeFileVariantList(String path, List<Variant> variantList){
        try (FileWriter fileWriter = new FileWriter(path);
             BufferedWriter writer = new BufferedWriter(fileWriter)){
            for (Variant variant: variantList){
                writer.write(variant.getVariantID()+ "; "
                        +variant.getProductID()+ "; "
                        + variant.getSize() + "; "
                        + variant.getColor() + "; "
                        + variant.getQuantity() +"\n");
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    public static void writeFileCategoryList(String path, List<Category> categoryList){
        try (FileWriter fileWriter = new FileWriter(path);
             BufferedWriter writer = new BufferedWriter(fileWriter)){
            for (Category category: categoryList){
                writer.write(category.getIDCategory()+ "; " + category.getName() +"\n");
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    public static void writeFileProductList(String path, List<Product> productList){
        try (FileWriter fileWriter = new FileWriter(path);
             BufferedWriter writer = new BufferedWriter(fileWriter)){
            for (Product product: productList){
                writer.write(product.getProductID()+ "; "
                        + product.getCategoryID()+ "; "
                        + product.getNameProduct()+ "; "
                        + product.getSize()+ "; "
                        + product.getColor()+ "; "
                        + product.getQuantity()+ "; "
                        + product.getPrice()+ "; "
                        + product.getDescribe() +"\n");
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    //productID; categoryID; nameProduct; variantID; quantity; price; numberOfProductPurchase; dayAddProduct; describe
    public static void writeFileProductListDTO(String path, List<ProductDTO> productList){
        try (FileWriter fileWriter = new FileWriter(path);
             BufferedWriter writer = new BufferedWriter(fileWriter)){
            for (ProductDTO productDTO: productList){
                writer.write(productDTO.getProductID()+ "; "
                        + productDTO.getCategoryID()+ "; "
                        + productDTO.getNameProduct()+ "; "
                        + productDTO.getPrice()+ "; "
                        + productDTO.getNumberOfProductPurchase()+ "; "
                        + productDTO.getDayAddProduct()+ "; "
                        + productDTO.getDescribe() +"\n");
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    // cartlineID;  productID; variantID; quantitySelected; TotalPriceOfUnitProductInCartline;
    public static void writeFileCartLineList(String path, List<Cartline> cartlineList){
        try (FileWriter fileWriter = new FileWriter(path);
             BufferedWriter writer = new BufferedWriter(fileWriter)){
            for (Cartline cartline: cartlineList){
                writer.write(cartline.getCartLineID()+ "; "
                        + cartline.getProductID()+ "; "
                        + cartline.getVariantID()+ "; "
                        + cartline.getQuantitySelected()+  "\n");
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    public static void writeFileInvoice(String path, User user) {
        try (FileWriter fileWriter = new FileWriter(path);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(String.format("""
                    \t\t\t\tHÓA ĐƠN
                    %s
                    \n
                    """, user.toString()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public static void writeFileAppendInvoice(String path, List<Cartline> cartLineList, List<Variant> variantList) {
        try (FileWriter fileWriter = new FileWriter(path);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write("THÔNG TIN GIỎ HÀNG" + "\n" + "Số lượng sản phẩm đã chọn mua: " + cartLineList.size() + "\n");
            int sumOfCart = 0;
            for (int indexOfCartLine = 0; indexOfCartLine < cartLineList.size(); indexOfCartLine++){
                for (int indexOfVariantList = 0; indexOfVariantList < variantList.size(); indexOfVariantList++){
                    if (
                            (cartLineList.get(indexOfCartLine).getProductID() == variantList.get(indexOfVariantList).getProductID()) &&
                            (cartLineList.get(indexOfCartLine).getVariantID() == variantList.get(indexOfVariantList).getVariantID())
                    ){
                       ProductDTO productDTO = ProductServiceDTO.getInstance().getProductById(cartLineList.get(indexOfCartLine).getProductID());
                        int quantitySelected = cartLineList.get(indexOfCartLine).getQuantitySelected();
                        String color = variantList.get(indexOfCartLine).getColor();
                        String size = variantList.get(indexOfCartLine).getSize();
                        double price = productDTO.getPrice();
                        double totalUnitPrice = quantitySelected * price;
                       writer.write(String.format("""
                        Sản phẩm đã mua: %s
                        Số lượng sản phẩm: %d
                        Màu sắc: %s
                        Kích thước: %s
                        Giá tiền sản phẩm: %f
                        Thành tiền: %f
                        """, productDTO.getNameProduct(),quantitySelected, color, size,price,totalUnitPrice));
                        sumOfCart +=totalUnitPrice;
                    }
                }
            }
            writer.write("Tổng tiền: " + sumOfCart + " ₫");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
 //String customerName, String productName, int productPrice, String size, String color, long quantity, Date dayOfPurchase
public static void writeFileAppendInvoiceHistory(String path,User user,List<Cartline> cartlines) {
    try (FileWriter fileWriter = new FileWriter(path, true);
         BufferedWriter writer = new BufferedWriter(fileWriter)) {
        HashSet<Cartline> printedCartLine = new HashSet<>();
        for (Cartline cartline: cartlines) {
            if (!printedCartLine.contains(cartline)) {
                writer.write(user.getFullName() + "; "
                        + ProductServiceDTO.getInstance().getProductById(cartline.getProductID()).getNameProduct() + "; "
                        + ProductServiceDTO.getInstance().getProductById(cartline.getProductID()).getPrice() + "; "
                        + VariantService.getInstance().getVariantByID(cartline.getVariantID()).getSize() + "; "
                        + VariantService.getInstance().getVariantByID(cartline.getVariantID()).getColor() + "; "
                        + ProductServiceDTO.getInstance().getProductById(cartline.getProductID()).getNumberOfProductPurchase() + "; "
                        + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "\n");
                printedCartLine.add(cartline);
            }
        }
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }
}

}
