package com.example.demo1;
import java.io.*;                // For file handling and serialization
import java.util.ArrayList;
public class RestaurantWithReviews extends Restaurant {
    private ArrayList<Review> reviews;

    public RestaurantWithReviews(String name, String location, String category) {
        super(name, location, category);  // Calls the constructor of the parent Restaurant class
        this.reviews = new ArrayList<>();
    }

    public void addReview(String reviewerName, int rating, String comment, String filename) {
        Review review = new Review(reviewerName, rating, comment);
        this.reviews.add(review);
        saveReviewsToFile(filename); // Save reviews to file
    }

    public void saveReviewsToFile(String filename) {
        // Save reviews list to file
        Review.saveReviewsToFile(this.reviews, filename);
    }

    public void loadReviewsFromFile(String filename) {
        // Load reviews from file and populate the reviews list
        this.reviews = Review.loadReviewsFromFile(filename);
    }

    public ArrayList<Review> getReviews() {
        return this.reviews;
    }

    public void displayReviews() {
        // Method to display reviews in the console (for debugging or testing purposes)
        for (Review review : reviews) {
            System.out.println(review);
        }
    }
}
