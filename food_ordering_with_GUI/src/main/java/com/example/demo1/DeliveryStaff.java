package com.example.demo1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DeliveryStaff extends Person{

    Alert_window alert=new Alert_window();
    @Override
    public void logIn(){

        Stage window=new Stage();

        GridPane grid=new GridPane();
        grid.setPadding(new Insets(4,10,10,10));
        grid.setHgap(10);
        grid.setVgap(10);
        //title
        Label title=new Label("Log In As Delivery");
            title.getStyleClass().add("label-white");
        GridPane.setConstraints(title,0,0);
        title.setAlignment(Pos.CENTER);

        //Email
        Label email_label=new Label("Email: ");
        GridPane.setConstraints(email_label,0,1);
        TextField Email=new TextField();
        Email.setPromptText("Enter you email");
        GridPane.setConstraints(Email,1,1);
        //password
        Label password_label=new Label("Password: ");
        GridPane.setConstraints(password_label,0,5);
        PasswordField Password=new PasswordField();
        Password.setPromptText("password");
        GridPane.setConstraints(Password,1,5);
        Button submit=new Button("Submit");
        submit.setOnAction(e->{
            try {
                BufferedReader reader = new BufferedReader(new FileReader("Register.txt"));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] record= line.split("/");
                    String storedEmail = record[2];
                    String storedPassword = record[3];
                    if (record.length == 6&&storedEmail.equals(Email.getText()) && storedPassword.equals(Password.getText())) {
                        System.out.println("login successfully"); // Credentials match
                        System.out.println("welcome "+record[0]);
                        window.close();
                        return ;

                    }else
                    {
                        System.out.println("file not found");
                        alert.display("Log in","invalid information");
                        break;

                    }
                }
                reader.close();
            } catch (IOException msg) {
                msg.getMessage();
            }
        });
        GridPane.setConstraints(submit,0,10);
        Button btn_close=new Button(" close");
        btn_close.setOnAction(e->{
           window.close();
        });
        GridPane.setConstraints(btn_close,0,12);
        btn_close.getStyleClass().add("button-white");

        grid.getChildren().addAll(title,email_label,Email,password_label,Password,submit,btn_close);
        grid.setAlignment(Pos.CENTER);
        Scene page1=new Scene(grid,500,500);
        page1.getStylesheets().add("style.css");
        window.setScene(page1);
        window.setTitle("HELLO PERSON!...");
        window.show();

    }

}

