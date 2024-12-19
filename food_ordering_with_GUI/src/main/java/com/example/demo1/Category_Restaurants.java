package com.example.demo1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Category_Restaurants extends Application {

    private ArrayList<Restaurant> restaurants = Restaurant.initializeRestaurants(); // All restaurants
    private ListView<String> categoryListView = new ListView<>();
    private ListView<String> restaurantListView = new ListView<>();
    private ListView<String> menuListView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        // Initialize data
        ObservableList<String> categories = FXCollections.observableArrayList(
                "Pizza", "Fried Chicken", "Sea Food", "Dessert", "Burgers", "Café");

        // Set category list
        categoryListView.setItems(categories);

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
                menuListView.getItems().clear(); // Clear menu when a new category is selected
            }
        });

        // Handle restaurant selection
        restaurantListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Find the selected restaurant and get its menu
                Restaurant selectedRestaurant = restaurants.stream()
                        .filter(r -> r.getName().equals(newValue))
                        .findFirst()
                        .orElse(null);

                if (selectedRestaurant != null) {
                    // Update menu ListView with items from the selected restaurant
                    ObservableList<String> menuItems = FXCollections.observableArrayList(
                            selectedRestaurant.getMenu().stream()
                                    .map(food -> food.getName() + " - " + food.getType() + " - " + food.getPrice() + " EGP")
                                    .collect(Collectors.toList())
                    );
                    menuListView.setItems(menuItems);
                }
            }
        });

        // Create Tabs
        Tab categoryTab = new Tab("Categories", new VBox(categoryListView));
        Tab restaurantTab = new Tab("Restaurants", new VBox(restaurantListView));
        Tab menuTab = new Tab("Menu", new VBox(menuListView));

        // Prevent tabs from being closed
        categoryTab.setClosable(false);
        restaurantTab.setClosable(false);
        menuTab.setClosable(false);

        // TabPane setup
        TabPane tabPane = new TabPane(categoryTab, restaurantTab, menuTab);
        Scene scene = new Scene(tabPane, 400, 600);

        // Stage setup
        primaryStage.setTitle("Category & Restaurants");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
