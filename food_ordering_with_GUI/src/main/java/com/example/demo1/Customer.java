package com.example.demo1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Customer extends Person {
    static int id;
    private String f_name;
    private String l_name;
    private String email;
    private int phone_number;
    private String address;
    private String password;
    private String gender;
    boolean check = false;

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    Scanner input = new Scanner(System.in);
    Alert_window alert = new Alert_window();

    @Override
    public void logIn() {
        Stage window = new Stage();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(4, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Title
        Label title = new Label("Log In");
        title.getStyleClass().add("label-white");
        GridPane.setConstraints(title, 0, 0);
        title.setAlignment(Pos.CENTER);

        // Email
        Label email_label = new Label("Email: ");
        GridPane.setConstraints(email_label, 0, 1);
        TextField Email = new TextField();
        Email.setPromptText("Enter your email");
        GridPane.setConstraints(Email, 1, 1);

        // Password
        Label password_label = new Label("Password: ");
        GridPane.setConstraints(password_label, 0, 5);
        PasswordField Password = new PasswordField();
        Password.setPromptText("Password");
        GridPane.setConstraints(Password, 1, 5);

        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 0, 10);

        Button btn_Register = new Button("Don't have an account yet? Register");
        btn_Register.setOnAction(e -> {
            try {
                Register();
                window.close();
            } catch (Exception msg) {
                msg.getMessage();
            }
        });
        GridPane.setConstraints(btn_Register, 0, 12);
        btn_Register.getStyleClass().add("button-white");

        grid.getChildren().addAll(title, email_label, Email, password_label, Password, submit, btn_Register);
        grid.setAlignment(Pos.CENTER);
        Scene page1 = new Scene(grid, 500, 500);
        page1.getStylesheets().add("style.css");
        window.setScene(page1);
        window.setTitle("Login");
        window.show();

        submit.setOnAction(e -> {
            boolean found = false; // Track if login is successful
            try (BufferedReader reader = new BufferedReader(new FileReader("Register.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] record = line.split("/");
                    if (record.length == 6) {
                        String storedEmail = record[2].trim();
                        String storedPassword = record[3].trim();
                        if (storedEmail.equals(Email.getText().trim()) && storedPassword.equals(Password.getText().trim())) {
                            System.out.println("Login successful");
                            System.out.println("Welcome, " + record[0] + "!");
                            found = true;
                            window.close(); // Close the login window

                            // Open the Order window after successful login
                            Order order = new Order();
                            order.start();
                            break;
                        }
                    }
                }
                if (!found) {
                    alert.display("Login", "Invalid email or password. Please try again.");
                }
            } catch (IOException msg) {
                alert.display("Error", "An error occurred while accessing the file.");
                msg.printStackTrace();
            }
        });
    }

    public void Register() throws IOException {
        Stage window = new Stage();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(4, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Title
        Label title = new Label("Register");
        GridPane.setConstraints(title, 0, 0);
        title.setAlignment(Pos.CENTER);

        // First Name
        Label F_name_label = new Label("First Name: ");
        GridPane.setConstraints(F_name_label, 0, 3);
        TextField F_name = new TextField();
        F_name.setPromptText("Enter your first name");
        GridPane.setConstraints(F_name, 1, 3);

        // Last Name
        Label L_name_label = new Label("Last Name");
        GridPane.setConstraints(L_name_label, 0, 5);
        TextField L_name = new TextField();
        L_name.setPromptText("Enter your last name");
        GridPane.setConstraints(L_name, 1, 5);

        // Email
        Label email_label = new Label("Email");
        GridPane.setConstraints(email_label, 0, 7);
        TextField email = new TextField();
        email.setPromptText("Enter your email");
        email.setOnAction(e -> {
            if (!email.getText().contains("@")) {
                alert.display("Invalid Email", "Email must contain @");
            }
        });
        GridPane.setConstraints(email, 1, 7);

        // Password
        Label password_label = new Label("Password: ");
        GridPane.setConstraints(password_label, 0, 9);
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        GridPane.setConstraints(password, 1, 9);

        // Confirm password
        Label C_password_label = new Label("Confirm Password: ");
        GridPane.setConstraints(C_password_label, 0, 11);
        PasswordField C_password = new PasswordField();
        C_password.setPromptText("Confirm password");
        GridPane.setConstraints(C_password, 1, 11);
        C_password.setOnAction(e -> {
            if (!password.getText().equals(C_password.getText())) {
                alert.display("Confirm Password", "Passwords do not match.");
            }
        });

        // Gender
        ComboBox<String> comboBox1 = new ComboBox<>();
        comboBox1.getItems().addAll("Female", "Male");
        comboBox1.setPromptText("Gender");
        GridPane.setConstraints(comboBox1, 0, 13);

        // Governorate
        ComboBox<String> comboBox2 = new ComboBox<>();
        comboBox2.getItems().addAll("Cairo", "Qalyubia", "Alexandria");
        comboBox2.setPromptText("Governorate");
        GridPane.setConstraints(comboBox2, 1, 13);

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            this.f_name = F_name.getText();
            this.password = password.getText();
            this.l_name = L_name.getText();
            this.email = email.getText();

            // Writing to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Register.txt", true))) {
                writer.write(this.f_name + '/' + this.l_name + '/' + this.email + '/' + this.password + '/' + comboBox1.getValue() + '/' + comboBox2.getValue());
                writer.newLine();
                System.out.println("The data was stored successfully.");
                writer.close();
            } catch (IOException msg) {
                msg.getMessage();
            }
        });
        GridPane.setConstraints(submit, 0, 14);

        Button btn_login = new Button("Already have an account? Log In");
        btn_login.setOnAction(e -> {
            logIn();
            window.close();
        });
        GridPane.setConstraints(btn_login, 0, 15);
        btn_login.getStyleClass().add("button-white");

        grid.getChildren().addAll(title, F_name_label, F_name, L_name_label, L_name, email_label, email, password_label, password, C_password_label, C_password, comboBox1, comboBox2, submit, btn_login);
        grid.setAlignment(Pos.CENTER);
        Scene page1 = new Scene(grid, 500, 500);
        page1.getStylesheets().add("style.css");
        window.setScene(page1);
        window.setTitle("Register");
        window.show();
    }
}
