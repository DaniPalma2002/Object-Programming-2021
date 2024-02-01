package ggc;

import java.awt.Taskbar.State;
import java.io.Serializable;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.List;

import java.util.Collection;
import java.util.Collections;

public class Partner implements Serializable{

    private static final long serialVersionUID = 202109192006L;
    private String _key = "";
    private String _name = "";
    private String _address = "";
    private int _points = 0;
    private int _valueOfAcquisitions = 0;
    private int _valueOfSalesMade = 0;
    private int _valueOfPaidSales = 0;
    private List<SaleTransaction> _partnerSales = new ArrayList<>();

    private PartnerState _status = new NormalState(this);

    public Partner(String key, String name, String address) {
        _key = key;
        _name = name;
        _address = address;
    } 
    
    @Override
    public String toString() {
        return _key + "|" + _name + "|" + _address + "|" + _status.toString() + "|" + _points
        + "|" + _valueOfAcquisitions + "|" + _valueOfSalesMade + "|" + _valueOfPaidSales;
    }

    public void discount() { _status.discount(); }
    public void penalty() { _status.penalty(); }
    //public void setPoints(int n) { _status.setPoints(n); }
    public void setState(PartnerState status) { _status = status; } 

    public void addValueOfAcquisitions(double value) { 
        _valueOfAcquisitions += value;
    }

    public void addPartnerSale(SaleTransaction t) {
        _partnerSales.add(t);
    }

    public Collection<SaleTransaction> showCompletePayments() {
        List<SaleTransaction> tempTransactions = new ArrayList<>();
        for (SaleTransaction t : _partnerSales) {
            if (!t.getPaymentDate().equals(""))
                tempTransactions.add(t);
        }
        return Collections.unmodifiableCollection(tempTransactions);
    }



}
