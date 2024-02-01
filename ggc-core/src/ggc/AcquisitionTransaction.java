package ggc;

public class AcquisitionTransaction extends Transaction{

    private double _paidValue;
    private int _paymentDate;

    public AcquisitionTransaction(int id, String partnerId, String productId, int quantity,
                                 double paidValue, int paymentDay) {
        super(id, partnerId, productId, quantity);
        _paidValue = paidValue;
        _paymentDate = paymentDay;
    }

    @Override
    public String toString() {
        return "COMPRA|" + super.toString() + (int)Math.round(_paidValue) + "|" + _paymentDate;
    }
}