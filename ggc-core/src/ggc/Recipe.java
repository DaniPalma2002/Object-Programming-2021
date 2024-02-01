package ggc;

import java.util.List;

import javax.xml.namespace.QName;

import java.util.ArrayList;
import java.util.Arrays;

public class Recipe {

    private List<Component> _components = new ArrayList<>();

    public Recipe() {}

    public void splitAddComponents(String components) {
        List<String> listComponents = new ArrayList<>(Arrays.asList(components.split("#")));
        for (String i : listComponents) {
            String[] fields = i.split(":");
            Component c = new Component(fields[0], Integer.parseInt(fields[1]));
            _components.add(c);
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (Component c : _components) {
            s += c.toString();
            s += "#";
        }
        if (s != "")
            return s.substring(0, s.length() - 1);
        return s;
    }

}
