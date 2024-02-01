package ggc;

public class SaleTransaction extends Transaction {
    
    public double _baseValue;
    public double _valuetoPay;
    public int _limitDate;
    public String _paymentDate = "";

    public SaleTransaction(int id, String partnerId, String productId, int quantity, 
        double baseValue, double valuetoPay, int limitDate, String paymentDate) {
        
        super(id, partnerId, productId, quantity);
        _baseValue = baseValue;
        _valuetoPay = valuetoPay;
        _limitDate = limitDate;
        _paymentDate = paymentDate;

    }

    @Override
    public String toString() {
        return "VENDA|" + super.toString() + (int)Math.round(_baseValue) + "|" +
        (int)Math.round(_valuetoPay) + "|" + _limitDate + _paymentDate;
    }

   
    public void setBaseValue(double baseValue) { _baseValue = baseValue; }

    public void setValueToPay(double valueToPay) { _valuetoPay = valueToPay; }

    public String getPaymentDate() { return _paymentDate; }
}
