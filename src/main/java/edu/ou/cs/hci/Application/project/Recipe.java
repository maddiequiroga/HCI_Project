package edu.ou.cs.hci.Application.project;

import javafx.beans.property.*;
import java.util.*;

public class Recipe {

    private final SimpleStringProperty              recipe_name;
    private final ArrayList<SimpleStringProperty>   recipe_ingredients;
    private final ArrayList<SimpleDoubleProperty>   recipe_amountSize;
    private final ArrayList<SimpleStringProperty>   recipe_amountType;
    private final SimpleBooleanProperty             gluten;
    private final SimpleBooleanProperty             dairy;
    private final SimpleIntegerProperty             servings;
    private final SimpleDoubleProperty              time;
    private final SimpleStringProperty              directions;

    public Recipe(List<String> item) {
        recipe_name = new SimpleStringProperty(item.get(0));

        recipe_ingredients = new ArrayList<>();
        recipe_amountSize = new ArrayList<>();
        recipe_amountType = new ArrayList<>();

        populateIngredientLists(item);

        gluten = new SimpleBooleanProperty(Boolean.parseBoolean(item.get(3)));
        dairy = new SimpleBooleanProperty(Boolean.parseBoolean(item.get(4)));
        servings = new SimpleIntegerProperty(Integer.parseInt(item.get(5)));
        time = new SimpleDoubleProperty(Double.parseDouble(item.get(6)));
        directions = new SimpleStringProperty(item.get(7));
    }

    private void populateIngredientLists(List<String> item) {
        SimpleStringProperty ing_data = new SimpleStringProperty(item.get(1));
        SimpleStringProperty amt_data = new SimpleStringProperty(item.get(2));

        String[] names_list = ing_data.get().split(",");
        String[] amt_list = amt_data.get().split(",");

        for (int i = 0; i < names_list.length; i++) {
            recipe_ingredients.add(new SimpleStringProperty(names_list[i]));
            int count = 0;

            char[] amt = amt_list[i].toCharArray();
            for (char c : amt) {
                if (c >= 46 && c <= 57 || c == 32)
                    count++;
            }
            double amt_size = Double.parseDouble(amt_list[i].substring(0, count));
            String amt_type = amt_list[i].substring(count);

            recipe_amountSize.add(new SimpleDoubleProperty(amt_size));
            recipe_amountType.add(new SimpleStringProperty(amt_type));
        }


    }


    /////////////
    // Getters //
    /////////////
    public String getRecipeName() { return recipe_name.get(); }
    public ArrayList<String> getIngredients() {
        ArrayList<String> ingredients_list = new ArrayList<>();
        for (SimpleStringProperty ing: recipe_ingredients)
            ingredients_list.add(ing.get());
        return ingredients_list;
    }
    public ArrayList<Double> getAmountSize() {
        ArrayList<Double> amountSize_list = new ArrayList<>();
        for (SimpleDoubleProperty amtSize: recipe_amountSize)
            amountSize_list.add(amtSize.get());
        return amountSize_list;
    }
    public ArrayList<String> getAmountType() {
        ArrayList<String> amountType_list = new ArrayList<>();
        for (SimpleStringProperty amtType: recipe_amountType)
            amountType_list.add(amtType.get());
        return amountType_list;
    }
    public Boolean getGluten() { return gluten.get(); }
    public Boolean getDairy() { return dairy.get(); }
    public int getServings() { return servings.get(); }
    public double getTime() { return time.get(); }
    public String getDirections() { return directions.get(); }


    /////////////
    // Setters //
    /////////////
    public void setRecipeName(String v) { recipe_name.set(v); }
    public void setRecipeIngredient(String v, int idx) { recipe_ingredients.get(idx).set(v); }
    public void setAmountSize(double v, int idx) { recipe_amountSize.get(idx).set(v); }
    public void setAmountType(String v, int idx) { recipe_amountType.get(idx).set(v); }
    public void setGluten(boolean v) { gluten.set(v); }
    public void setDairy(boolean v) { dairy.set(v); }
    public void setServings(int v) { servings.set(v); }
    public void setTime(double v) { time.set(v); }
    public void setDirections(String v) { directions.set(v); }


    //////////////////////
    // Property Methods //
    //////////////////////
    public StringProperty  recipeNameProperty() { return recipe_name; }
    public DoubleProperty  amountSizeProperty(int idx) { return recipe_amountSize.get(idx); }
    public StringProperty  amountTypeProperty(int idx) { return recipe_amountType.get(idx); }
    public StringProperty  recipeIngredientProperty(int idx) { return recipe_ingredients.get(idx); }
    public BooleanProperty glutenProperty() { return gluten; }
    public BooleanProperty dairyProperty() { return dairy; }
    public IntegerProperty servingsProperty() { return servings; }
    public DoubleProperty timeProperty() { return time; }
    public StringProperty directionsProperty() { return directions; }


    public List<String>	getAllAttributesAsStrings()
    {
        ArrayList<String>	list = new ArrayList<String>();

        list.add(getRecipeName());
        list.add(getIngredients().toString());
        list.add(getAmountSize().toString());
        list.add(getAmountType().toString());
        list.add(Boolean.toString(getGluten()));
        list.add(Boolean.toString(getDairy()));
        list.add(Integer.toString(getServings()));
        list.add(Double.toString(getTime()));
        list.add(getDirections());

        return list;
    }

}
