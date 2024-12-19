package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RestaurantReviewController {

    @FXML
    private Label restaurantNameLabel;  // Label to display restaurant name
    @FXML
    private Label restaurantCategoryLabel;  // Label to display restaurant category
    @FXML
    private Label restaurantAddressLabel;  // Label to display restaurant address

    @FXML
    private TextField reviewerNameField;

    @FXML
    private ComboBox<Integer> ratingComboBox;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private TextArea reviewsDisplayArea;

    private RestaurantWithReviews restaurant;

    // Initialize method, called when the controller is loaded
    public void initialize() {
        // Initialize the restaurant object for displaying its details
        restaurant = new RestaurantWithReviews("Sample", "123 Street", "Italian");

        try {
            // Attempt to load reviews from a file
            restaurant.loadReviewsFromFile("reviews.dat");
        } catch (Exception e) {
            showAlert("Error", "Could not load reviews. Starting with an empty list.");
        }

        // Set available ratings (1-5)
        ratingComboBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        ratingComboBox.getSelectionModel().selectFirst();

        // Display the loaded reviews
        updateReviewDisplay();

        // Display restaurant details in the labels
        setRestaurant(restaurant,"");
    }

    // Handle adding a review
    @FXML
    private void handleAddReview() {
        String reviewerName = reviewerNameField.getText();
        Integer rating = ratingComboBox.getValue();
        String comment = commentTextArea.getText();

        // Validate input
        if (reviewerName.isEmpty() || comment.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        try {
            // Add the review and save to file
            restaurant.addReview(reviewerName, rating, comment, "reviews.dat");
            updateReviewDisplay(); // Update review display
            reviewerNameField.clear(); // Clear reviewer name field
            commentTextArea.clear(); // Clear comment text area
            ratingComboBox.getSelectionModel().selectFirst(); // Reset rating combo box
            showAlert("Success", "Review added successfully.");
        } catch (Exception e) {
            showAlert("Error", "Could not add review. Please try again.");
        }
    }

    // Handle saving reviews to a file
    @FXML
    private void handleSaveReviews() {
        try {
            restaurant.saveReviewsToFile("reviews.dat");
            showAlert("Success", "Reviews saved successfully.");
        } catch (Exception e) {
            showAlert("Error", "Could not save reviews.");
        }
    }

    // Handle loading reviews from a file
    @FXML
    private void handleLoadReviews() {
        try {
            restaurant.loadReviewsFromFile("reviews.dat");
            updateReviewDisplay();
            showAlert("Success", "Reviews loaded successfully.");
        } catch (Exception e) {
            showAlert("Error", "Could not load reviews.");
        }
    }

    // Update the TextArea with the latest reviews
    private void updateReviewDisplay() {
        StringBuilder displayText = new StringBuilder();
        for (Review review : restaurant.getReviews()) {
            displayText.append(review.toString()).append("\n*=======================*\n");
        }
        reviewsDisplayArea.setText(displayText.toString());
    }

    // Show an alert with a given title and message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to set the restaurant for review page and display its details
    public void setRestaurant(Object restaurant,String name) {
            Restaurant restaurantObj = (Restaurant) restaurant;
            // Update UI elements based on Restaurant properties
            restaurantNameLabel.setText(name);
            restaurantCategoryLabel.setText("Category: " + restaurantObj.getCategory());
            restaurantAddressLabel.setText("Address: " + restaurantObj.getLocation());

    }
}
