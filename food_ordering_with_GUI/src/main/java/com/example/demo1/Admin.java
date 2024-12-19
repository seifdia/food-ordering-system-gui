package com.example.demo1;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Admin extends Person{
    public class admin {
        private String firstName;
        private String lastName;
        private String email;
        private String password;

    public admin(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

Alert_window alert=new Alert_window();
    @Override
    public void logIn() {
        Stage window = new Stage();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(4, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Title Label
        Label title = new Label("Log In As Admin");
        title.getStyleClass().add("label-white");
        GridPane.setConstraints(title, 0, 0);
        title.setAlignment(Pos.CENTER);

        // Email Field
        Label emailLabel = new Label("Email: ");
        GridPane.setConstraints(emailLabel, 0, 1);
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        GridPane.setConstraints(emailField, 1, 1);

        // Password Field
        Label passwordLabel = new Label("Password: ");
        GridPane.setConstraints(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        GridPane.setConstraints(passwordField, 1, 2);

        // Submit Button
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("Register.txt"));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] record = line.split("/");
                    String storedEmail = record[2];
                    String storedPassword = record[3];

                    if (record.length == 6 && storedEmail.equals(emailField.getText()) && storedPassword.equals(passwordField.getText())) {
                        System.out.println("Login successful");
                        System.out.println("Welcome " + record[0]);
                        window.close(); // Close the login window
                        openAdminPanel(); // Open the admin panel
                        return;
                    } else {
                        System.out.println("Invalid credentials");
                        showAlert("Log in", "Invalid email or password");
                        break;
                    }
                }
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                showAlert("Error", "Failed to read the registration file");
            }
        });
        GridPane.setConstraints(submit, 0, 4);

        // Close Button
        Button btnClose = new Button("Close");
        btnClose.setOnAction(e -> window.close());
        GridPane.setConstraints(btnClose, 1, 4);
        btnClose.getStyleClass().add("button-white");

        // Add controls to grid
        grid.getChildren().addAll(title, emailLabel, emailField, passwordLabel, passwordField, submit, btnClose);
        grid.setAlignment(Pos.CENTER);

        // Create and show the scene
        Scene scene = new Scene(grid, 500, 500);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.setTitle("Admin Login");
        window.show();
    }

    // Open the admin panel after successful login
    private void openAdminPanel() {
        try {
            // Use the correct path relative to the resources folder
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/adminPanel.fxml"));
            Scene adminScene = new Scene(loader.load());

            // Get the current stage and change the scene to the admin panel
            Stage stage = new Stage();
            stage.setScene(adminScene);
            stage.setTitle("Admin Panel");
            stage.show(); // Show the admin panel window
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load admin panel");
        }
    }

    // Show alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

