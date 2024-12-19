package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    Stage window;
    Scene page1, page2;

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        window.setOnCloseRequest(e -> {
            e.consume();  // to solve the problem as it close even click on No button
            close_window();
        });

        // Create buttons
        Button button1 = new Button("Admin");
        Button button2 = new Button("User");
        Button button3 = new Button("DeliveryStaff");


        // Add CSS to buttons
        button1.getStyleClass().add("button-Background");
        button2.getStyleClass().add("button-Background");
        button3.getStyleClass().add("button-Background");


        // Admin button action
        button1.setOnAction(e -> {
            Person admin = new Admin();
            admin.logIn();
        });

        // User button action
        button2.setOnAction(e -> {
            Person customer = new Customer();
            customer.logIn();
        });

        // DeliveryStaff button action
        button3.setOnAction(e -> {
            Person delivery = new DeliveryStaff();
            delivery.logIn();
        });

        // Review Restaurant button action


        // Welcome label
        Label label = new Label("Welcome ");
        VBox vBox = new VBox(10);
        vBox.getChildren().add(label);
        vBox.setAlignment(Pos.CENTER);

        // Buttons container
        VBox center = new VBox(30);
        center.getChildren().addAll(button1, button2, button3);
        center.setAlignment(Pos.CENTER);

        // Create main layout
        BorderPane all = new BorderPane();
        all.setCenter(center);
        all.setMargin(center, new Insets(0, 0, 10, 0));
        all.setTop(vBox);
        all.getStyleClass().add("background");

        // Set Scene
        page1 = new Scene(all, 500, 500);
        page1.getStylesheets().add("style.css");
        window.setScene(page1);
        window.setTitle("Hello Roqia!");
        window.show();
    }

    // Method to load the restaurant review page (Page 2)


    // Method to handle window close confirmation
    private void close_window() {
        Confirm c = new Confirm();
        boolean confirm = c.display("Confirm", "Do you want to exit this program?");
        if (confirm)
            window.close();
    }

    public static void main(String[] args) {
        launch();
    }
}
