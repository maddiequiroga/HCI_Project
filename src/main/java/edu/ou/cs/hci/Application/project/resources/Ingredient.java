package edu.ou.cs.hci.Application.project.resources;

import javafx.beans.property.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Ingredient {

    private final SimpleStringProperty ingredientName;
    private final SimpleDoubleProperty quantitySize;
    private final SimpleStringProperty quantityType;
    private final SimpleStringProperty expirationDate;
    private final SimpleIntegerProperty ingredientType;

    public Ingredient(List<String> item) {
        ingredientName = new SimpleStringProperty(item.get(0));
        quantitySize = new SimpleDoubleProperty(Double.parseDouble(item.get(1)));
        quantityType = new SimpleStringProperty(item.get(2));

        expirationDate = new SimpleStringProperty(item.get(3));
        ingredientType = new SimpleIntegerProperty(Integer.parseInt(item.get(4)));
    }

    /////////////
    // Getters //
    /////////////
    public String getIngredientName() { return ingredientName.get(); }
    public double getQuantitySize() { return quantitySize.get(); }
    public String getQuantityType() { return quantityType.get(); }
    public String getExpirationDate() { return expirationDate.get(); }
    public int getIngredientType() { return ingredientType.get(); }

    /////////////
    // Setters //
    /////////////
    public void setIngredientName(String v) { ingredientName.set(v); }
    public void setQuantitySize(double v) { quantitySize.set(v); }
    public void setQuantityType(String v) { quantityType.set(v); }
    public void setExpirationDate(String v) { expirationDate.set(v); }
    public void setIngredientType(int v) { ingredientType.set(v); }

    //////////////////////
    // Property Methods //
    //////////////////////

    public StringProperty ingredientNameProperty() { return ingredientName; }
    public DoubleProperty quantitySizeProperty() { return quantitySize; }
    public StringProperty quantityTypeProperty() { return quantityType; }
    public SimpleStringProperty expirationDateProperty() { return expirationDate; }
    public IntegerProperty ingredientTypeProperty() { return ingredientType; }


    public List<String>	getAllAttributesAsStrings()
    {
        ArrayList<String>	list = new ArrayList<String>();

        list.add(getIngredientName());
        list.add(Double.toString(getQuantitySize()));
        list.add(getQuantityType());
        list.add(getExpirationDate());
        list.add(Integer.toString(getIngredientType()));

        return list;
    }

    public Date parseExpirationDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
            Date d = sdf.parse(getExpirationDate());
            return d;
        } catch (Exception e) {
            System.out.println("Error Parsing Date");
        }
        return null;
    }
}
