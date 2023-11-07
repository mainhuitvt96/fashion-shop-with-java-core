package Test;

import model.service.VariantService;

import java.util.Scanner;

public class TestVariant {
    public static void main(String[] args) {
        VariantService variantService = VariantService.getInstance();

        variantService.customizeVariant();


    }
}
