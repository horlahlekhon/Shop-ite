package com.shopManagement.admin.newProductDialog;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.shopManagement.DatabaseConnection.DatabaseConnection;
import com.shopManagement.Products.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @Author Olalekan Adebari ( nee Sisyphus )
 **/
public class NewProductDialogController implements Initializable{

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
    @FXML
    private JFXButton addProductButton;

    DatabaseConnection dbConn;
    Product newProduct;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
            this.addProductButton.setOnAction(e -> {
                addProduct(e);
               Stage addProductStage=(Stage)  addProductButton.getScene().getWindow();
               addProductStage.close();
            });

    }


    // validat data that is inputed if it is an integer, since we retriev all data as Strings
    public boolean validateInputData() {
        String productId;
        String productname;
        String productCategory;
        String companyname;
        String stock;
        String price;
        while(newStock.getText() != null && unitPrice.getText() != null){
            if (unitPrice.getText().matches("\\d+")) {  //regex check if the entrered value only contains numbers
                price = unitPrice.getText();
                return true;
            }else{
                Alert priceNotDigits = new Alert(Alert.AlertType.ERROR);
                priceNotDigits.setTitle("product price error");
                priceNotDigits.setHeaderText("Input Error");
                priceNotDigits.setContentText("the User ID inputed is not fully numbers");
                priceNotDigits.showAndWait(); }
            if (newStock.getText().matches("\\d+")) {  //regex check if the entrered value only contains numbers
                stock = newStock.getText();
                return true;
            }else{
                Alert priceNotDigits = new Alert(Alert.AlertType.ERROR);
                priceNotDigits.setTitle("Stock Amount Input Field");
                priceNotDigits.setHeaderText("Input Error");
                priceNotDigits.setContentText("the Stock size inputed is not in Digits");
                priceNotDigits.showAndWait(); }
        }

        return false;

}

    public Product setProduct(){

       if ( validateInputData()){
           newProduct = new Product(productID.getText(),productName.getText(),companyName.getText(),productCategory.getText(),Integer.parseInt(newStock.getText()),Integer.parseInt(unitPrice.getText()));
       }
       return newProduct;
    }

    /*
    * this method will add new product into the ddatabase based on the input entred in the field*/
    @FXML
    public void addProduct(Product newProduct) {
        PreparedStatement prepState = null;
         ResultSet rs = null;
        try {

            Connection conn = this.dbConn.getConnection();

            String selectQuery = "SELECT * FROM productInventory";

           // prepState.setString(1,newProduct.getProductName());
            prepState = conn.prepareStatement(selectQuery);
            rs = prepState.executeQuery();

            System.out.println("i halted at the first try block");
            while(rs.next()/* &&  newProduct != null*/){
                System.out.println("the iF statements firstBlock");
                if (rs.getString(1).equals(newProduct.getProductID())){
                    System.out.println("there is a product wiuth the same PID ");
                    Alert sameProductID = new Alert(Alert.AlertType.WARNING);
                    sameProductID.setTitle("Product Duplicate");
                    sameProductID.setHeaderText("Input Error");
                    sameProductID.setContentText("there is a product with the same Product ID\nplease Choose another Product ID");
                    sameProductID.showAndWait();
                }
                if (rs.getString(2).equals(newProduct.getProductName())){
                    System.out.println("the iF statements secondBlock");
                    System.out.println("there is a product wiuth the same Name");
                    Alert sameProductID = new Alert(Alert.AlertType.WARNING);
                    sameProductID.setTitle("Product Duplicate");
                    sameProductID.setHeaderText("Input Error");
                    sameProductID.setContentText("there is a product with the same Name \nplease Choose another Product name to avoid conflicts");
                    sameProductID.showAndWait();
                }else{
                    System.out.println("i failed to entre the try");
                    try {
                        ///////////////////////////////////////////////////////
                        String query = "INSERT INTO productInventory (ProductID,ProductName,CompanyName, ProductCategory, Quantity, UnitPrice) VALUES(?,?,?,?,?,?)";
                        prepState = conn.prepareStatement(query);


                        prepState.setString(1, newProduct.getProductID());
                        prepState.setString(2, newProduct.getProductName());
                        prepState.setString(3, newProduct.getCompanyName());
                        prepState.setString(4, newProduct.getProductCategory());
                        prepState.setString(5, String.valueOf(newProduct.getQuantity()));
                        prepState.setString(6, String.valueOf(newProduct.getUnitPrice()));

                        prepState.executeUpdate();

                        Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        successAlert.setTitle("success!");
                        successAlert.setHeaderText("Add new product");
                        successAlert.setContentText("product Successfully added");
                        successAlert.showAndWait();

                        conn.close();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }



            }


        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void addProduct(ActionEvent event) {
        validateInputData();
        addProduct(setProduct());
    }
}