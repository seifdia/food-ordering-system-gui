package com.example.demo1;

import com.example.demo1.Food;
import com.example.demo1.Restaurant;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Order {

    private ArrayList<Restaurant> restaurants = Restaurant.initializeRestaurants(); // All restaurants
    private ComboBox<String> categoryComboBox = new ComboBox<>();
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ComboBox<String> menuComboBox = new ComboBox<>();
    private ListView<String> orderListView = new ListView<>();
    private Label totalPriceLabel = new Label("Total Price: 0 EGP");

    private List<Food> selectedItems = new ArrayList<>();
    private double totalPrice = 0.0;

    // Method to get the last used Order ID
    private int getLastOrderId() {
        int lastOrderId = 0;
        File orderIdFile = new File("order_id.txt");

        if (orderIdFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(orderIdFile))) {
                String line = reader.readLine();
                if (line != null) {
                    lastOrderId = Integer.parseInt(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading order ID file: " + e.getMessage());
            }
        }

        return lastOrderId;
    }

    // Method to save the new Order ID to the file
    private void saveNewOrderId(int newOrderId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("order_id.txt"))) {
            writer.write(String.valueOf(newOrderId));
        } catch (IOException e) {
            System.out.println("Error writing new order ID: " + e.getMessage());
        }
    }

    // Method to save the order to a file
    private void saveOrderToFile(int orderId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("order.txt", true))) {
            // Write the order ID and details to the file
            writer.write("Order ID: " + orderId + "\n");
            writer.write("Order Details:\n");
            for (Food item : selectedItems) {
                writer.write(item.getName() + " - " + item.getPrice() + " EGP\n");
            }
            writer.write("\nTotal Price: " + totalPrice + " EGP\n");
            writer.write("--------------------------\n");
            writer.write("Order Created at: " + java.time.LocalDateTime.now() + "\n");
            writer.write("==========================\n\n");

            System.out.println("Order saved to order.txt successfully.");
        } catch (IOException e) {
            showAlert("Error", "Failed to save the order to file.");
        }
    }

    // Updated createOrder method
    private void createOrder() {
        if (selectedItems.isEmpty()) {
            showAlert("No items selected", "Please select items to create an order.");
        } else {
            // Get the last used order ID and increment it
            int lastOrderId = getLastOrderId();
            int newOrderId = lastOrderId + 1;

            // Save the new order ID to the file
            saveNewOrderId(newOrderId);

            // Save the order to the file with the new order ID
            saveOrderToFile(newOrderId);

            // Show success alert with the order ID
            showAlert("Order Created", "Your order has been created and saved with Order ID: " + newOrderId);

            // Optionally clear the order list after saving
            selectedItems.clear();
            orderListView.getItems().clear();
            totalPrice = 0.0;
            totalPriceLabel.setText("Total Price: 0 EGP");
        }
    }

    // Remove item from the order
    private void removeItemFromOrder() {
        String selectedItem = orderListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String[] parts = selectedItem.split(" - ");
            String itemName = parts[0];
            double itemPrice = Double.parseDouble(parts[1].replace(" EGP", ""));
            selectedItems.removeIf(item -> item.getName().equals(itemName));
            orderListView.getItems().remove(selectedItem);
            totalPrice -= itemPrice;
            totalPriceLabel.setText("Total Price: " + totalPrice + " EGP");
        } else {
            showAlert("No item selected", "Please select an item to remove.");
        }
    }

    // Show alert method
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to load the restaurant review page
    private void loadRestaurantReviewPage() {
        String selectedRestaurantName = restaurantComboBox.getValue();
        if (selectedRestaurantName != null) {
            // Find the selected restaurant object
            Restaurant selectedRestaurant = restaurants.stream()
                    .filter(r -> r.getName().equals(selectedRestaurantName))
                    .findFirst()
                    .orElse(null);

            if (selectedRestaurant != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("RestaurantReview.fxml"));
                    Scene reviewScene = new Scene(loader.load(), 600, 600);

                    // Get the controller for the review page
                    RestaurantReviewController controller = loader.getController();
                    controller.setRestaurant(selectedRestaurant,selectedRestaurantName);  // Pass the selected restaurant

                    Stage window = new Stage();
                    window.setScene(reviewScene); // Switch to the review page
                    window.setTitle("Restaurant Review");
                    window.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Restaurant not found", "The selected restaurant could not be found.");
            }
        } else {
            showAlert("No restaurant selected", "Please select a restaurant before adding a review.");
        }
    }

    public void start() {
        Stage primaryStage = new Stage();
        ObservableList<String> categories = FXCollections.observableArrayList(
                "Pizza", "Fried Chicken", "Sea Food", "Dessert", "Burgers", "Café");

        categoryComboBox.setItems(categories);

        categoryComboBox.setOnAction(e -> updateRestaurantsByCategory());
        restaurantComboBox.setOnAction(e -> updateMenuByRestaurant());
        menuComboBox.setOnAction(e -> addToOrder());

        // Create order button
        Button createOrderButton = new Button("Create Order");
        createOrderButton.setOnAction(e -> createOrder());

        // Remove item button
        Button removeItemButton = new Button("Remove Selected Item");
        removeItemButton.setOnAction(e -> removeItemFromOrder());

        // Add Review button
        Button addReviewButton = new Button("Add Review");
        addReviewButton.setOnAction(e -> loadRestaurantReviewPage()); // Open the review page

        VBox categoryBox = new VBox(new Label("Select Category:"), categoryComboBox);
        categoryBox.setSpacing(10);
        categoryBox.setPadding(new Insets(10));

        VBox restaurantBox = new VBox(new Label("Select Restaurant:"), restaurantComboBox);
        restaurantBox.setSpacing(10);
        restaurantBox.setPadding(new Insets(10));

        VBox menuBox = new VBox(new Label("Select Menu Item:"), menuComboBox);
        menuBox.setSpacing(10);
        menuBox.setPadding(new Insets(10));

        // VBox for the order details and the ListView
        VBox orderBox = new VBox(new Label("Your Order:"), orderListView, totalPriceLabel);
        orderBox.setSpacing(10);
        orderBox.setPadding(new Insets(10));

        // Buttons for actions
        HBox buttonBox = new HBox(createOrderButton, removeItemButton, addReviewButton);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(categoryBox, restaurantBox, menuBox, orderBox, buttonBox);
        mainLayout.setSpacing(20);
        mainLayout.setPadding(new Insets(20));

        Scene scene = new Scene(mainLayout, 500, 500);
        scene.getStylesheets().add("style.css");
        primaryStage.setTitle("Order Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateRestaurantsByCategory() {
        String selectedCategory = categoryComboBox.getValue();
        if (selectedCategory != null) {
            ObservableList<String> filteredRestaurants = FXCollections.observableArrayList(
                    restaurants.stream()
                            .filter(r -> r.getCategory().toLowerCase().contains(selectedCategory.toLowerCase()))
                            .map(Restaurant::getName)
                            .collect(Collectors.toList())
            );
            restaurantComboBox.setItems(filteredRestaurants);
            menuComboBox.getItems().clear();
        }
    }

    private void updateMenuByRestaurant() {
        String selectedRestaurantName = restaurantComboBox.getValue();
        if (selectedRestaurantName != null) {
            // Find the selected restaurant and get its menu
            Restaurant selectedRestaurant = restaurants.stream()
                    .filter(r -> r.getName().equals(selectedRestaurantName))
                    .findFirst()
                    .orElse(null);

            if (selectedRestaurant != null) {
                ObservableList<String> menuItems = FXCollections.observableArrayList(
                        selectedRestaurant.getMenu().stream()
                                .map(food -> food.getName() + " - " + food.getType() + " - " + food.getPrice() + " EGP")
                                .collect(Collectors.toList())
                );
                menuComboBox.setItems(menuItems);
            }
        }
    }

    private void addToOrder() {
        String selectedMenuItem = menuComboBox.getValue();
        if (selectedMenuItem != null) {
            String[] parts = selectedMenuItem.split(" - ");
            String itemName = parts[0];
            String itemType = parts[1];
            double itemPrice = Double.parseDouble(parts[2].replace(" EGP", ""));
            Food selectedFood = new Food(itemName, itemType, itemPrice);
            selectedItems.add(selectedFood);
            orderListView.getItems().add(itemName + " - " + itemPrice + " EGP");
            totalPrice += itemPrice;
            totalPriceLabel.setText("Total Price: " + totalPrice + " EGP");
        }
    }

    public static void main(String[] args) {
        Order order = new Order();
        order.start();
    }
}
