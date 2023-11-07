package model.DTO;

public class VariantDTO {
    private int variantID;
    private String size;
    private String color;

    public VariantDTO(String size, String color) {
        this.size = size;
        this.color = color;
    }
    public VariantDTO() {

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

    @Override
    public String toString() {
        return  "Kích thước: '" + size + ", " +"Màu sắc: '" + color + '\n';
    }
}
