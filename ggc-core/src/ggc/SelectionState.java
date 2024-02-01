package ggc;

public class SelectionState extends PartnerState {
    
    public SelectionState(Partner partner) {
        super(partner);
    }

    @Override
    public void discount() {}

    @Override
    public void penalty() {}

    @Override
    public String toString() {
        return "SELECTION";
    }
}
