package model.util;

import model.DTO.ProductDTO;
import model.builder.InvoiceBuilder.InvoiceConcreteBuilder;
import model.builder.product.ProductConcreteBuilder;
import model.builder.product.ProductDTO.ProductConcreteBuilderDTO;
import model.entity.Cart.Cartline;
import model.entity.Category;
import model.entity.Invoice;
import model.entity.Product;
import model.entity.User.Customer;
import model.entity.User.Owner;
import model.entity.Variant;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUlti {

    private static List<String> readFile(String path){
        List<String> readFileList = new ArrayList<>();
        File file = new File(path);
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)){
                    String line;
                    while ((line = reader.readLine()) != null){
                        readFileList.add(line);
                    }
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
        return readFileList;
    }

    public static List<Customer> fileReadingCustomer (String path){
        List<String> propertyOfCustomerList = readFile(path);
        List<Customer> customerList = new ArrayList<>();
        final int INDEX_OF_USERNAME = 0;
        final int INDEX_OF_FULLNAME = 1;
        final int INDEX_OF_PHONE_NUMBER = 2;
        final int INDEX_OF_EMAIL = 3;
        final int INDEX_OF_PASSWORD = 4;
        final int INDEX_OF_ADDRESS = 5;

        for (String propertyOfCustomer: propertyOfCustomerList){
            if(propertyOfCustomer.equals(propertyOfCustomerList.get(0))){
                continue;
            }
            String[] properties = propertyOfCustomer.split("; ");
            customerList.add(new Customer(
                    properties[INDEX_OF_USERNAME],
                    properties[INDEX_OF_FULLNAME],
                    properties[INDEX_OF_PHONE_NUMBER],
                    properties[INDEX_OF_EMAIL],
                    properties[INDEX_OF_PASSWORD],
                    properties[INDEX_OF_ADDRESS]));
        }
        return customerList;
    }
    public static List<Owner> fileReadingOwner (String path){
        List<String> propertyOfOwnerList = readFile(path);
        List<Owner> ownerList = new ArrayList<>();
        final int INDEX_OF_USERNAME = 0;
        final int INDEX_OF_FULLNAME = 1;
        final int INDEX_OF_PHONE_NUMBER = 2;
        final int INDEX_OF_EMAIL = 3;
        final int INDEX_OF_PASSWORD = 4;
        final int INDEX_OF_ADDRESS = 5;

        for (String propertyOfOwner: propertyOfOwnerList){
            if(propertyOfOwner.equals(propertyOfOwnerList.get(0))){
                continue;
            }
            String[] properties = propertyOfOwner.split("; ");
            ownerList.add(new Owner(
                    properties[INDEX_OF_USERNAME],
                    properties[INDEX_OF_FULLNAME],
                    properties[INDEX_OF_PHONE_NUMBER],
                    properties[INDEX_OF_EMAIL],
                    properties[INDEX_OF_PASSWORD],
                    properties[INDEX_OF_ADDRESS]));
        }
        return ownerList;
    }
    public static List<Variant> fileReadingVariant(String path) throws Exception{
        List<String> propertyOfVariantList = readFile(path);
        List<Variant> variantList = new ArrayList<>();
        //int productID, String size, String color, int quantity
        final int INDEX_OF_VARIANT_ID =0;
        final int INDEX_OF_PRODUCT_ID =1;
        final  int INDEX_OF_SIZE = 2;
        final  int INDEX_OF_COLOR = 3;
        final  int INDEX_OF_QUANTITY = 4;
        for (String propertyOfVariant: propertyOfVariantList){
            String[] properties = propertyOfVariant.split("; ");
            int valueOfVariantID = Integer.parseInt(properties[INDEX_OF_VARIANT_ID]);
            int valueOfProductID = Integer.parseInt(properties[INDEX_OF_PRODUCT_ID]);
            int valueOfQuantity = Integer.parseInt(properties[INDEX_OF_QUANTITY]);
            variantList.add(new Variant(valueOfVariantID,valueOfProductID, properties[INDEX_OF_SIZE], properties[INDEX_OF_COLOR], valueOfQuantity));
        }
        return variantList;
    }
    public static List<Category> fileReadingCategory(String path) throws Exception{
        List<String> propertyOfCategoryList = readFile(path);
        List<Category> categoryList = new ArrayList<>();
        final int INDEX_OF_CATEGORY_ID = 0;
        final  int INDEX_OF_NAME_CATEGORY = 1;
        for (String propertyOfCategory: propertyOfCategoryList){
            String[] properties = propertyOfCategory.split("; ");
            int valueOfCategoryID = Integer.parseInt(properties[INDEX_OF_CATEGORY_ID]);
            categoryList.add(new Category(valueOfCategoryID, properties[INDEX_OF_NAME_CATEGORY]));
        }
        return categoryList;
    }
    // cartlineID;  productID; variantID; quantitySelected; TotalPriceOfUnitProductInCartline;
    public static List<Cartline> fileReadingCartline(String path) throws Exception{
        List<String> propertyOfCartline = readFile(path);
        List<Cartline> cartlineList = new ArrayList<>();
        final int INDEX_OF_CARTLINE_ID = 0;
        final int INDEX_OF_PRODUCT_ID = 1;
        final int INDEX_OF_VARIANT_ID = 2;
        final int INDEX_OF_QUANTITY_SELECTED = 3;
        final  int INDEX_OF_TOTAL_PRICE = 4;
        for (String propertyOfCategory: propertyOfCartline){
            String[] properties = propertyOfCategory.split("; ");
            int valueOfCartLineID = Integer.parseInt(properties[INDEX_OF_CARTLINE_ID]);
            int valueOfProductID = Integer.parseInt(properties[INDEX_OF_PRODUCT_ID]);
            int valueOfvariantID = Integer.parseInt(properties[INDEX_OF_VARIANT_ID]);
            int valueOfQuantity = Integer.parseInt(properties[INDEX_OF_QUANTITY_SELECTED]);
            cartlineList.add(new Cartline(valueOfCartLineID, valueOfProductID, valueOfvariantID, valueOfQuantity));
        }
        return cartlineList;
    }
    public static List<Product> fileReadingProduct(String path) throws Exception{
        List<String> propertyOfProductList = readFile(path);
        List<Product> productList = new ArrayList<>();

        final int INDEX_OF_PRODUCT_ID = 0;
        final int INDEX_OF_CATEGORY_ID = 1;
        final int INDEX_OF_NAME_PRODUCT =2;
        final int INDEX_OF_SIZE = 3;
        final int INDEX_OF_COLOR = 4;
        final int INDEX_OF_QUANTITY = 5;
        final int INDEX_OF_PRICE = 6;
        final int INDEX_OF_DESCRIBE = 7;
        for (String propertyOfProduct: propertyOfProductList){
            String[] properties = propertyOfProduct.split("; ");
            int valueOfproductID = Integer.parseInt(properties[INDEX_OF_PRODUCT_ID]);
            int valueOfCategoryID = Integer.parseInt(properties[INDEX_OF_CATEGORY_ID]);
            int valueOfQuantity = Integer.parseInt(properties[INDEX_OF_QUANTITY]);
            double valueOfPrice = Double.parseDouble(properties[INDEX_OF_PRICE]);
            productList.add(new ProductConcreteBuilder().setProductID(valueOfproductID)
                    .setCategoryID(valueOfCategoryID)
                    .setNameProduct(properties[INDEX_OF_NAME_PRODUCT])
                    .setSize(properties[INDEX_OF_SIZE])
                    .setColor(properties[INDEX_OF_COLOR])
                    .setQuantity(valueOfQuantity)
                    .setPrice(valueOfPrice)
                    .setDescribe(properties[INDEX_OF_DESCRIBE]).build());
        }
        return productList;
    }
    public static List<ProductDTO> fileReadingProductDTO (String path) throws ParseException {
        List<String> propertyOfProductList = readFile(path);
        List<ProductDTO> productList = new ArrayList<>();
        final int INDEX_OF_PRODUCT_ID = 0;
        final int INDEX_OF_CATEGORY_ID = 1;
        final int INDEX_OF_NAME_PRODUCT =2;
        final int INDEX_OF_PRICE = 3;
        final int INDEX_OF_PRODUCT_PURCHASE = 4;
        final int INDEX_OF_DAY_ADD_PRODUCT = 5;
        final int INDEX_OF_DESCRIBE = 6;
        for (String propertyOfProduct: propertyOfProductList){
            String[] properties = propertyOfProduct.split("; ");
            int valueOfproductID = Integer.parseInt(properties[INDEX_OF_PRODUCT_ID]);
            int valueOfCategoryID = Integer.parseInt(properties[INDEX_OF_CATEGORY_ID]);
            double valueOfPrice = Double.parseDouble(properties[INDEX_OF_PRICE]);
            int valueOfNumberOfProductPurchase = Integer.parseInt(properties[INDEX_OF_PRODUCT_PURCHASE]);
            productList.add(new ProductConcreteBuilderDTO()
                    .setProductID(valueOfproductID)
                    .setCategoryID(valueOfCategoryID)
                    .setNameProduct(properties[INDEX_OF_NAME_PRODUCT])
                    .setPrice(valueOfPrice)
                    .setNumberOfProductPurchase(valueOfNumberOfProductPurchase)
                    .setDayAddProduct(properties[INDEX_OF_DAY_ADD_PRODUCT])
                    .setDescribe(properties[INDEX_OF_DESCRIBE]).build());
        }
        return productList;
    }
    public static List<Invoice> fileReadingInvoice(String path) throws ParseException {
        List<String> propertyOfInvoiceList = readFile(path);
        List<Invoice> invoices = new ArrayList<>();
        final int INDEX_OF_CUSTOMER_NAME = 0;
        final int INDEX_OF_PRODUCT_NAME = 1;
        final int INDEX_OF_PRODUCT_PRICE = 2;
        final int INDEX_OF_SIZE = 3;
        final int INDEX_OF_COLOR = 4;
        final int INDEX_OF_QUANTITY = 5;
        final int INDEX_OF_DAYOFPURCHASE = 6;
        for (String propertyOfInvoice : propertyOfInvoiceList) {
            String[] properties = propertyOfInvoice.split("; ");
            invoices.add(new InvoiceConcreteBuilder()
                    .setNameCustomer(properties[INDEX_OF_CUSTOMER_NAME])
                    .setProductName(properties[INDEX_OF_PRODUCT_NAME])
                    .setProductPrice(Integer.parseInt(properties[INDEX_OF_PRODUCT_PRICE]))
                    .setColor(properties[INDEX_OF_COLOR])
                    .setSize(properties[INDEX_OF_SIZE])
                    .setQuantity(Long.parseLong(properties[INDEX_OF_QUANTITY]))
                    .setDayOfPurchase(properties[INDEX_OF_DAYOFPURCHASE])
                    .build());
        } return invoices;
    }
}























