package com.shopManagement.admin;

/**
 * @Author Olalekan Adebari nee Sisyphus
 **/

import com.shopManagement.LoginScreen.LoginScreenMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.stage.Modality.APPLICATION_MODAL;


public class WelcomeController extends AnchorPane implements Initializable {
    @FXML
    private ToggleGroup welcomeButtons;
    @FXML
    private Button dashBoard;
    @FXML
    private Button profile;
    @FXML
    private Button logout;
    @FXML
    private Button addUser;


    //////load dialog box to add new user\\\\\\
    ///  AddUserDialogController addUserDialogController ;


    ////////////new User adding doalog \\\\\\\\\\\\\\\

    public WelcomeController(Stage newStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../admin/adminWelcomePageFXML.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dashBoard.setOnAction((ActionEvent event) -> {
            Stage fstage = (Stage) dashBoard.getScene().getWindow();
            fstage.close();
            dashBoardLoader();
        });

        this.addUser.setOnAction(event -> {
            loadAddUserDialog();
            Stage fstage = (Stage) addUser.getScene().getWindow();
            fstage.close();

        });

        this.logout.setOnAction(e -> logout());

    }

    public void dashBoardLoader() {
        try {
            Stage dashBoard = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            BorderPane root = fxmlLoader.load(getClass().getResource("../admin/AdminFXML.fxml"));
            // fxmlLoader.setRoot(root);


            Scene scene = new Scene(root);
            dashBoard.setScene(scene);
            dashBoard.setTitle("Admin - DashBoard");
            dashBoard.setResizable(true);
            dashBoard.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void loadAddUserDialog() {
        try {
            Stage addUserStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            BorderPane root = loader.load(getClass().getResource("../admin/AddUserDialog.fxml"));
            AddUserDialogController dialogController = loader.getController();

            addUserStage.initModality(APPLICATION_MODAL);
            Scene scene = new Scene(root);
            addUserStage.setScene(scene);
            addUserStage.setTitle("Add user Dialog");
            addUserStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    @FXML
    public void logout() {
        Stage welcomeScreen = (Stage) logout.getScene().getWindow();
        welcomeScreen.close();
        Stage loginScreen = new Stage();

        LoginScreenMain main = new LoginScreenMain();
        try {
            main.start(loginScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
