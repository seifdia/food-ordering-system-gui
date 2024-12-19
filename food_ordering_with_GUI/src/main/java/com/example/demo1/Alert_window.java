package com.example.demo1;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Alert_window {
   public  Alert_window(){}

     static void display(String title,String msg){
        Stage alert=new Stage();
         alert.initModality(Modality.APPLICATION_MODAL);
         alert.setTitle("title");
         alert.setMinWidth(300);
         Label label=new Label();
         label.setText(msg);
         Button button=new Button("close this window");
         button.setOnAction(e-> alert.close());
         VBox layout=new VBox(50);
         layout.getChildren().addAll(label,button);
         layout.setAlignment(Pos.CENTER);

         Scene alert_scene=new Scene(layout);
         alert.setScene(alert_scene);
         alert.showAndWait();

     }
}
