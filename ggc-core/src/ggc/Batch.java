package ggc;

import java.io.Serializable;

public class Batch implements Serializable, Comparable<Batch>{

    private static final long serialVersionUID = 202109192006L;
    private Product _product;
    private String _partnerKey;
    private double _price;
    private int _stock;

    public Batch(Product product, String partnerKey, double price, int stock) {
        _product = product;
        _partnerKey = partnerKey;
        _price = price;
        _stock = stock;
    }
    
    @Override
    public String toString() {
        return _product.getIdProduct() + "|" + _partnerKey + "|" + (int)Math.round(_price) + 
         "|" + _stock + _product.toBatchString();
    }

    @Override
    public int compareTo(Batch b) {
        if (_product.getIdProduct().equalsIgnoreCase(b.getProduct().getIdProduct())) {
            if (_partnerKey.equalsIgnoreCase(b.getPartnerKey())) {
                if (_price == b.getPrice()) {
                    return Integer.compare(_stock, b.getStock());
               }
               else {
                   return Double.compare(_price, b.getPrice());
               }
            }
            else {
                return _partnerKey.compareToIgnoreCase(b.getPartnerKey());
            }
        } else {
            return _product.getIdProduct().compareToIgnoreCase(b.getProduct().getIdProduct());
        }
    }

    public Product getProduct() { return _product; }

    public String getPartnerKey() { return _partnerKey; }

    public double getPrice() { return _price; }

    public int getStock() { return _stock; }
}
