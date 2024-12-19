package com.example.demo1;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Confirm {

   static boolean confirm;
    static boolean display(String title,String msg){
        Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("title");
        window.setMinWidth(250);
        Label label=new Label();
        label.setText(msg);
        Button yes=new Button("Yes");
        Button no=new Button("No");
        yes.setOnAction(e->{
            confirm=true;
            window.close();
        });

        no.setOnAction(e->{
            confirm=false;
            window.close();
        });
        HBox side=new HBox(60);
        side.getChildren().addAll(yes,no);
        side.setAlignment(Pos.CENTER);
        VBox layout=new VBox(10);
        layout.getChildren().addAll(label,side);
        layout.setAlignment(Pos.CENTER);

        Scene alert_scene=new Scene(layout);
        window.setScene(alert_scene);
        window.showAndWait();
      return confirm;
    }


}
//VBox left=new VBox(50);
//Button a=new Button("A");
//Button b=new Button("B");
//Button c=new Button("C");
//        left.getChildren().addAll(a,b,c);
//
//VBox right=new VBox(50);
//Button aaa=new Button("A");
//Button bbb=new Button("B");
//Button ccc=new Button("C");
//        right.getChildren().addAll(aaa,bbb,ccc);
//
//HBox top=new HBox(50);
//Button aa=new Button("Home");
//Button bb=new Button("About");
//Button cc=new Button("log out");
//        top.getChildren().addAll(aa,bb,cc);
//        top.setAlignment(Pos.CENTER);
//
//HBox bottom=new HBox(50);
//Button aaaa=new Button("Home");
//Button bbbb=new Button("About");
//Button cccc=new Button("log out");
//        bottom.getChildren().addAll(aaaa,bbbb,cccc);
//        bottom.setAlignment(Pos.CENTER);
//
//BorderPane all=new BorderPane();
//        all.setTop(top);
//        all.setLeft(left);
//        all.setRight(right);
//        all.setBottom(bottom);