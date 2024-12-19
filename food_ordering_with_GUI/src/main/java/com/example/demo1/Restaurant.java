package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Restaurant implements Serializable {
    protected String name;
    protected String location;
    protected String category;
    private ArrayList<Food> menu;

    // Constructor
    public Restaurant(String name, String location, String category) {
        this.name = name;
        this.location = location;
        this.category = category;
        this.menu = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    // Add items to the restaurant's menu
    public void addMenuItem(String itemName, String itemType, double itemPrice) {
        menu.add(new Food(itemName, itemType, itemPrice));
    }

    // Display menu for debugging purposes
    public void displayMenu() {
        System.out.println("Menu for " + name + ":");
        for (Food item : menu) {
            System.out.println(item.getName() + " (" + item.getType() + "): " + item.getPrice() + " EGP");
        }
    }

    // Getters (required for the GUI)
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }


    public ArrayList<Food> getMenu() {
        return menu;
    }
    public String toString() {
        return "Restaurant: " + name + ", Location: " + location + ", Category: " + category;
    }
public static void display_restayrant(){

  ArrayList<Restaurant> restaurants = initializeRestaurants(); // All restaurants
    Stage Categories_window = new Stage();
    ObservableList<String> categories = FXCollections.observableArrayList(
            "Pizza", "Fried Chicken", "Sea Food", "Dessert", "Burgers");

    // Category ListView
    ListView<String> categoryListView = new ListView<>(categories);
    // Restaurants ListView
    ListView<String> restaurantListView = new ListView<>();
    // Handle category selection
    categoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            // Filter restaurants based on the selected category
            ObservableList<String> filteredRestaurants = FXCollections.observableArrayList(
                    restaurants.stream()
                            .filter(r -> r.getCategory().toLowerCase().contains(newValue.toLowerCase()))
                            .map(Restaurant::getName)
                            .collect(Collectors.toList())
            );
            restaurantListView.setItems(filteredRestaurants);
        }
    });

    // Layout
    VBox layout = new VBox(10, categoryListView, restaurantListView);
    Scene scene = new Scene(layout, 500, 500);
    scene.getStylesheets().add("style.css");
    Categories_window.setScene(scene);
    Categories_window.setTitle("HEllo Roqia!...");
    Categories_window.show();
}
    // Static method to initialize restaurant data
    public static ArrayList<Restaurant> initializeRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        Restaurant restaurant1 = new Restaurant("Kansas", "Cairo, Nasr City", "Fried Chicken");
        restaurant1.addMenuItem("Fried Chicken Bucket (12 pcs)", "Main Meal", 280.0);
        restaurant1.addMenuItem("Crispy Chicken Sandwich", "Main Meal", 100.0);
        restaurant1.addMenuItem("Chicken Wrap Meal", "Combo", 150.0);
        restaurant1.addMenuItem("Spicy Chicken Wings", "Appetizer", 120.0);
        restaurants.add(restaurant1);

        Restaurant restaurant2 = new Restaurant("Heart Attack","Giza, Dokki" ,"Fried Chicken");
        restaurant2.addMenuItem("Cheesy Fried Chicken", "Main Meal", 180.0);
        restaurant2.addMenuItem("Family Meal (20 pcs)", "Combo", 450.0);
        restaurant2.addMenuItem("Chicken Wrap with Fries", "Combo", 160.0);
        restaurant2.addMenuItem("Crispy Tenders", "Appetizer", 140.0);
        restaurants.add(restaurant2);

        Restaurant restaurant3 = new Restaurant("Pronto Pizza","Cairo, Heliopolis" ,"Pizza");
        restaurant3.addMenuItem("Margherita Pizza", "Main Meal", 100.0);
        restaurant3.addMenuItem("Pepperoni Pizza", "Main Meal", 150.0);
        restaurant3.addMenuItem("BBQ Chicken Pizza", "Main Meal", 160.0);
        restaurant3.addMenuItem("Vegetarian Pizza", "Main Meal", 130.0);
        restaurants.add(restaurant3);

        Restaurant restaurant4 = new Restaurant("Pizza Station", "Giza, Mohandessin", "Pizza");
        restaurant4.addMenuItem("Four-Cheese Pizza", "Main Meal", 180.0);
        restaurant4.addMenuItem("Seafood Pizza", "Main Meal", 200.0);
        restaurant4.addMenuItem("Truffle Mushroom Pizza", "Main Meal", 220.0);
        restaurant4.addMenuItem("Classic Pepperoni", "Main Meal", 160.0);
        restaurants.add(restaurant4);

        Restaurant restaurant5 = new Restaurant("Skyroof", "Alexandria, Corniche", "Sea Food");
        restaurant5.addMenuItem("Mixed Seafood Platter", "Main Meal", 450.0);
        restaurant5.addMenuItem("Shrimp Pasta", "Main Meal", 250.0);
        restaurant5.addMenuItem("Grilled Calamari", "Appetizer", 180.0);
        restaurant5.addMenuItem("Lemonade", "Drink", 25.0);
        restaurants.add(restaurant5);

        Restaurant restaurant6 = new Restaurant("Mandarine Koueider", "Cairo, Zamalek", "Dessert");
        restaurant6.addMenuItem("Kunafa with Cream", "Dessert", 90.0);
        restaurant6.addMenuItem("Chocolate Truffles", "Dessert", 150.0);
        restaurant6.addMenuItem("Ice Cream (per scoop)", "Dessert", 25.0);
        restaurants.add(restaurant6);

        Restaurant restaurant7 = new Restaurant("Cilantro Café", "Cairo, New Cairo", "Café");
        restaurant7.addMenuItem("Caramel Latte", "Beverage", 55.0);
        restaurant7.addMenuItem("Turkey & Cheese Sandwich", "Snack", 75.0);
        restaurant7.addMenuItem("Classic Cappuccino", "Beverage", 50.0);
        restaurant7.addMenuItem("Chocolate Croissant", "Pastry", 45.0);
        restaurants.add(restaurant7);

        Restaurant restaurant8 = new Restaurant("Wild Burger", "Cairo, Nasr City", "Burgers, Fried Chicken");
        restaurant8.addMenuItem("Double Cheeseburger", "Burger", 120.0);
        restaurant8.addMenuItem("Spicy Chicken Strips", "Fried Chicken", 90.0);
        restaurant8.addMenuItem("Cheesy Fries", "Side Dish", 50.0);
        restaurant8.addMenuItem("Nutella Milkshake", "Dessert", 60.0);
        restaurants.add(restaurant8);

        Restaurant restaurant9 = new Restaurant("Latino Café & Restaurant", "Alexandria, Corniche", "Pizza, Seafood, Café");
        restaurant9.addMenuItem("Margherita Pizza", "Pizza", 110.0);
        restaurant9.addMenuItem("Grilled Shrimp Platter", "Seafood", 250.0);
        restaurant9.addMenuItem("Iced Mocha", "Beverage", 60.0);
        restaurant9.addMenuItem("Cheesecake", "Dessert", 80.0);
        restaurants.add(restaurant9);

        Restaurant restaurant10 = new Restaurant("Ovio", "Cairo, New Cairo", "Café, Dessert");
        restaurant10.addMenuItem("Lava Cake with Ice Cream", "Dessert", 100.0);
        restaurant10.addMenuItem("Iced Cappuccino", "Beverage", 60.0);
        restaurants.add(restaurant10);

        // Add more restaurant initialization as per your original code
        return restaurants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public void addReview(Review review) {
        reviews.add(review);
    }

    // Get all reviews for this restaurant
    public ArrayList<Review> getReviews() {
        return reviews;
    }

    // Save reviews to file
    public void saveReviews(String filename) {
        Review.saveReviewsToFile(reviews, filename);
    }

    // Load reviews from file
    public void loadReviews(String filename) {
        reviews = Review.loadReviewsFromFile(filename);
    }
    private ArrayList<Review> reviews;
}
