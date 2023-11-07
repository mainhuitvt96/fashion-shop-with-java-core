package model.entity;

public class Category {
    private int IDCategory;
    private String name;
    private static  int count = 0;

    public Category(){

    }

    public Category(int IDCategory, String name) {
        this.IDCategory = ++count;
        this.name = name;
    }

    public int getIDCategory() {
        return IDCategory;
    }

    public void setIDCategory(int IDCategory) {
        this.IDCategory = IDCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "ID Category: " + IDCategory + "\t name: " + name;
    }
}
