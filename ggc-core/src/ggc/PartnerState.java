package ggc;

import java.io.Serializable;

public abstract class PartnerState implements Serializable{
    
    private static final long serialVersionUID = 202109192006L;
    private Partner _partner;
    private int _points = 0;
    private double _discount;
    private double _penalty;

    public PartnerState(Partner partner) {
        _partner = partner;
    }

    public Partner getPartner() {
        return _partner;
    }

    public abstract void discount();

    public abstract void penalty();

    @Override
    public String toString() {return "/something is wrong/";}

}
