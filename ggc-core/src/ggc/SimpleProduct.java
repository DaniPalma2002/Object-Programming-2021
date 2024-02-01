package ggc;

public class SimpleProduct extends Product {

    public SimpleProduct(String idProduct, int maxPrice, int stock) {
        super(idProduct, maxPrice, stock);
    }

    @Override
    public String toString() {
        return super.toString(); 
    }

    @Override
    public String toBatchString() {
        return super.toBatchString();
    }

}
