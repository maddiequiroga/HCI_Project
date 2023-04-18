package edu.ou.cs.hci.Application.project.resources;

import javafx.beans.property.*;

import java.util.*;

public class Ingredient {

    private final SimpleStringProperty ingredient_name;
    private final SimpleDoubleProperty quantity_size;
    private final SimpleStringProperty quantity_type;
    private final SimpleIntegerProperty expiration_date;
    private final SimpleIntegerProperty ingredient_type;

    public Ingredient(List<String> item) {
        ingredient_name = new SimpleStringProperty(item.get(0));
        quantity_size = new SimpleDoubleProperty(Double.parseDouble(item.get(1)));
        quantity_type = new SimpleStringProperty(item.get(2));
        expiration_date = new SimpleIntegerProperty(Integer.parseInt(item.get(3)));
        ingredient_type = new SimpleIntegerProperty(Integer.parseInt(item.get(4)));
    }

    /////////////
    // Getters //
    /////////////
    public String getIngredientName() { return ingredient_name.get(); }
    public double getQuantitySize() { return quantity_size.get(); }
    public String getQuantityType() { return quantity_type.get(); }
    public int getExpiration() { return expiration_date.get(); }
    public int getIngredientType() { return ingredient_type.get(); }

    /////////////
    // Setters //
    /////////////
    public void setIngredientName(String v) { ingredient_name.set(v); }
    public void setQuantitySize(double v) { quantity_size.set(v); }
    public void setQuantityType(String v) { quantity_type.set(v); }
    public void setExpiration(int v) { expiration_date.set(v); }
    public void setIngredientType(int v) { ingredient_type.set(v); }

    //////////////////////
    // Property Methods //
    //////////////////////
    public StringProperty nameProperty() { return ingredient_name; }
    public DoubleProperty quantitySizeProperty() { return quantity_size; }
    public StringProperty quantityTypeProperty() { return quantity_type; }
    public IntegerProperty expirationProperty() { return expiration_date; }
    public IntegerProperty ingredientTypeProperty() { return ingredient_type; }


    public List<String>	getAllAttributesAsStrings()
    {
        ArrayList<String>	list = new ArrayList<String>();

        list.add(getIngredientName());
        list.add(Double.toString(getQuantitySize()));
        list.add(getQuantityType());
        list.add(Integer.toString(getExpiration()));
        list.add(Integer.toString(getIngredientType()));

        return list;
    }
}
