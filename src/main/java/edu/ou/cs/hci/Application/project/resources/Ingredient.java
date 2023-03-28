package edu.ou.cs.hci.Application.project.resources;

import java.util.Date;

public class Ingredient {
    public String name;
    public int quantity_size;
    public int quantity_type;
    public Date expiration_date;
    public int ingredient_type;

    public Ingredient(String name, int quantity_size, int quantity_type, Date expiration_date, int  ingredient_type) {
        this.name = name;
        this.quantity_size = quantity_size;
        this.quantity_type = quantity_type;
        this.expiration_date = expiration_date;
        this.ingredient_type = ingredient_type;
    }
}
