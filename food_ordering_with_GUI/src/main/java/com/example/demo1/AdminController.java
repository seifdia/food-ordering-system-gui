package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class AdminController {

    private static final String FILE_PATH = "Restaurants.txt"; // Path to save restaurant data
    private ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList();

    @FXML
    private ListView<Restaurant> restaurantListView;

    @FXML
    private TextField restaurantNameTextField;

    @FXML
    private TextField locationTextField;  // This is now for location

    @FXML
    private TextField categoryTextField;

    @FXML
    public void initialize() {
        loadRestaurantsFromFile();  // Load restaurants from file on startup
        restaurantListView.setItems(restaurantList); // Set the ListView items to the restaurant list

        // Set a custom cell factory to display restaurant details in ListView
        restaurantListView.setCellFactory(param -> new javafx.scene.control.ListCell<Restaurant>() {
            @Override
            protected void updateItem(Restaurant item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());  // Display the restaurant name, location, and category
                }
            }
        });

        // Listen for selection changes and update the TextFields
        restaurantListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Populate TextFields with the selected restaurant details
                restaurantNameTextField.setText(newValue.getName());
                locationTextField.setText(newValue.getLocation());
                categoryTextField.setText(newValue.getCategory());
            }
        });
    }


    @FXML
    public void addRestaurant() {
        String name = restaurantNameTextField.getText().trim();
        String location = locationTextField.getText().trim(); // Get location as a single field
        String category = categoryTextField.getText().trim(); // Get category

        if (name.isEmpty() || location.isEmpty() || category.isEmpty()) {
            showAlert("Error", "All fields must be filled out.");
            return;
        }

        Restaurant newRestaurant = new Restaurant(name, location, category);
        restaurantList.add(newRestaurant);

        saveRestaurantsToFile();
        clearInputFields();
    }

    @FXML
    public void removeRestaurant() {
        Restaurant selectedRestaurant = restaurantListView.getSelectionModel().getSelectedItem();

        if (selectedRestaurant != null) {
            restaurantList.remove(selectedRestaurant);
            saveRestaurantsToFile();
        } else {
            showAlert("Error", "Please select a restaurant to remove.");
        }
    }

    @FXML
    private void updateRestaurant() {
        // Get the selected restaurant
        Restaurant selectedRestaurant = restaurantListView.getSelectionModel().getSelectedItem();

        if (selectedRestaurant != null) {
            // Get updated values from TextFields
            String name = restaurantNameTextField.getText().trim();
            String location = locationTextField.getText().trim();
            String category = categoryTextField.getText().trim();

            // Check if all fields are filled out
            if (!name.isEmpty() && !location.isEmpty() && !category.isEmpty()) {
                // Update the selected restaurant with new values
                selectedRestaurant.setName(name);
                selectedRestaurant.setLocation(location);
                selectedRestaurant.setCategory(category);

                // Refresh the ListView to reflect the changes
                restaurantListView.refresh();

                // Save the updated list of restaurants to file
                saveRestaurantsToFile();

                // Clear input fields after successful update
                clearInputFields();
            } else {
                showAlert("Error", "All fields must be filled out to update.");
            }
        } else {
            showAlert("Error", "Please select a restaurant to update.");
        }
    }




    private void clearInputFields() {
        restaurantNameTextField.clear();
        locationTextField.clear();  // Clear location field
        categoryTextField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveRestaurantsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Restaurant.txt"))) {
            // Write each restaurant's data to the file
            for (Restaurant restaurant : restaurantList) {
                writer.write(restaurant.getName() + "," + restaurant.getLocation() + "," + restaurant.getCategory());
                writer.newLine();
            }
        } catch (IOException e) {
            showAlert("Error", "Could not save restaurants to file.");
        }
    }


    private void loadRestaurantsFromFile() {
        restaurantList.clear(); // Clear the existing list before loading
        try (BufferedReader reader = new BufferedReader(new FileReader("Restaurant.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    String location = parts[1].trim();
                    String category = parts[2].trim();
                    restaurantList.add(new Restaurant(name, location, category));
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Could not load restaurants from file.");
        }
    }
}
