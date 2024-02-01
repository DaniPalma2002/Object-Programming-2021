package ggc;

import javax.swing.text.TabExpander;

public class CompoundProduct extends Product {
    
    private double _tax;
    private String _components;
    private Recipe _recipe = new Recipe(/*_components*/);

    public CompoundProduct(String idProduct, int maxPrice, int stock, double tax,
        String components) {
        super(idProduct, maxPrice, stock);
        _tax = tax;
        _components = components;
        _recipe.splitAddComponents(components);
    }
    
    @Override
    public String toString() {
        return super.toString() + "|" + _tax + "|" + _recipe.toString();
    }

    @Override
    public String toBatchString() {
        return super.toBatchString() + "|" + _tax + "|" + _recipe.toString();
    }
}
