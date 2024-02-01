package ggc;

public class EliteState extends PartnerState {
    
    public EliteState(Partner partner) {
        super(partner);
    }

    @Override
    public void discount() {}

    @Override
    public void penalty() {}

    @Override
    public String toString() {
        return "ELITE";
    }
}
