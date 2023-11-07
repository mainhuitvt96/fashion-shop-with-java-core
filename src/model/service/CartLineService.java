package model.service;

import model.entity.Cart.Cartline;
import model.util.FileReaderUlti;

import java.util.ArrayList;
import java.util.List;

public class CartLineService {
    private static final CartLineService CART_LINE_SERVICE = new CartLineService();
    private CartLineService(){

    }
    public static CartLineService getInstance(){
        return CART_LINE_SERVICE;
    }
    private static final List<Cartline> CARTLINE_LIST = new ArrayList<>();

    public static CartLineService getCartLineService() {
        return CART_LINE_SERVICE;
    }

    public static final String path = "src\\model\\data\\cartline.csv";
    static {

        try {
            List<Cartline> dataList = FileReaderUlti.fileReadingCartline(path);
            CARTLINE_LIST.addAll(dataList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
