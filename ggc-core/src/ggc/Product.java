package ggc;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = 202109192006L;
    private String _idProduct = "";
    private int _maxPrice;
    private int _allStock;

    public Product(String idProduct, int maxPrice, int stock) {
        _idProduct = idProduct;
        _maxPrice = maxPrice;
        _allStock = stock;
    }

    @Override
    public String toString() {
        return _idProduct + "|" + _maxPrice + "|" + _allStock; 
    }
    
    public String toBatchString() {
        return "";
    }

    public String getIdProduct() { return _idProduct; }
    
    public int getMaxPrice() { return _maxPrice; }
    public void setMaxPrice(int maxPrice) { _maxPrice = maxPrice; }
    
    public int getStock() { return _allStock; }
    public void setStock(int stock) { _allStock = stock; }
    public void addStock(int n) { _allStock += n; }
}
