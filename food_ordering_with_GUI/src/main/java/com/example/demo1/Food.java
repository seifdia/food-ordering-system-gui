package com.example.demo1;

import java.io.Serializable;

public class Food implements Serializable {
    private String name;
    private String type; // e.g., Main Meal, Side Dish, Drink, etc.
    private double price;

    // Constructor
    public Food(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for price
    public double getPrice() {
        return price;
    }

    // Getter for type (if needed)
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " (" + type + ") - " + price + " EGP";
    }
}