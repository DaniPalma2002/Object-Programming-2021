package ggc;

public class NormalState extends PartnerState {
    
    public NormalState(Partner partner) {
        super(partner);
    }

    @Override
    public void discount() {}

    @Override
    public void penalty() {}

    @Override
    public String toString() {
        return "NORMAL";
    }



}
