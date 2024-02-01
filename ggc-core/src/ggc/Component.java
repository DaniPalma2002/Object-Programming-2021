package ggc;

public class Component {
    
    private String _name = "";
    private int _quantity;

    public Component(String name, int quantity) {
        _name = name;
        _quantity = quantity;
    }

    @Override
    public String toString() {
        return _name +":" + _quantity;
    }
    
}
