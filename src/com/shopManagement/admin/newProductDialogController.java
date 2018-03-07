package com.shopManagement.admin;

import com.jfoenix.controls.JFXTextField;
import com.shopManagement.DatabaseConnection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * @Author Olalekan Adebari nee Sisyphus
 **/
public class newProductDialogController extends AnchorPane  {

    @FXML
    private JFXTextField productID;
    @FXML
    private JFXTextField productName;
    @FXML
    private JFXTextField productCategory;
    @FXML
    private JFXTextField companyName;
    @FXML
    private JFXTextField newStock;
    @FXML
    private JFXTextField unitPrice;

    DatabaseConnection dbConn;

}
