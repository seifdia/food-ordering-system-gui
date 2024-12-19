package com.example.demo1;
import java.io.*;                // For file handling and serialization (ObjectOutputStream, ObjectInputStream, etc.)
import java.util.ArrayList;
public class Review implements Serializable {
    private String reviewerName;
    private int rating;
    private String comment;


    public Review(String reviewerName, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.comment = comment;

    }

    public String getReviewerName() {
        return reviewerName;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }




    @Override
    public String toString() {
        return "Reviewer: " + reviewerName + "\n" +
                "Rating: " + rating + "/5\n" +
                "Comment: " + comment + "\n" ;

    }



    public static void saveReviewsToFile(ArrayList<Review> reviews, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(reviews);
            System.out.println("Reviews saved to file: " + filename);
        } catch (IOException e) {
            System.err.println("Error saving reviews: " + e.getMessage());
        }
    }
    public static ArrayList<Review> loadReviewsFromFile(String filename) {
        ArrayList<Review> reviews = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            reviews = (ArrayList<Review>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found, starting with an empty review list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading reviews: " + e.getMessage());
        }
        return reviews;

    }

}
// Constructor, getters, setters, and toString method

