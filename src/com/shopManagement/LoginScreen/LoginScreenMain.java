package com.shopManagement.LoginScreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginScreenMain extends Application {

    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../LoginScreen/LoginScreen.fxml"));
        window.setTitle("Medical Management System - Log In Screen");
        window.setScene(new Scene(root, 1280, 800));
        window.setResizable(false);

        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
