package ggc;

import java.io.Serializable;

public class Transaction implements Serializable {
    
    private int _id;
    private static final long serialVersionUID = 202109192006L;
    private String _partnerId = "";
    private String _productId = "";
    public int _quantity;

    public Transaction(int id, String partnerId, String productId, int quantity) {
        _id = id;
        _partnerId = partnerId;
        _productId = productId;
        _quantity = quantity;
    }

    public String toString() {
        return _id + "|" + _partnerId.toUpperCase() + "|" + _productId.toUpperCase() + "|" + _quantity + "|";
    }

}
