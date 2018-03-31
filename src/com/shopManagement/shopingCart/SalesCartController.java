package com.shopManagement.shopingCart;
/**
 * @Author Olalekan Adebari ( nee Sisyphus )
 **/

import com.shopManagement.Products.Product;
import com.shopManagement.admin.dashboard.AdminController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SalesCartController implements Initializable {


    private AdminController dashBoardController;

    @FXML
    private VBox productsPane;  // this is the GridPane ; the main window that hoises the hbox that houses the products

    @FXML
    private Label totalMoney;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*for (Product p: dashBoardController.getProductList()) {
            System.out.println(p.toString());
        }*/


        addProductToCart(dashBoardController.getProductList());
    }

    @FXML
    public void addProductToCart(List<Product> productsSelected) {

        int columIndex = 0; //this value will always be zero, since we are not moving forward but downward
        int rowIndex = 0;  // this will increment, and make the nrxt values jump into the next row as the current one is added.
        for (Product selectedProduct : productsSelected) {
                HBox productList = new HBox();
                productList.setSpacing(70);

                productList.setPrefSize(737,8);

                Label productName = new Label(selectedProduct.getProductName());
               productName.setPrefSize(120,10);
               productList.setMargin(productName, new Insets(13,70,10,10));

                productList.getChildren().add(productName);

                TextField quantity = new TextField(String.valueOf(selectedProduct.getQuantity()));
                //quantity.setPadding(new Insets(10,20,20,50));
               // quantity.setAlignment(Pos.CENTER);
                quantity.setPrefSize(50,10);
                productList.setMargin(quantity,new Insets(13,90,10,70));
                productList.getChildren().add(quantity);
               // productList.setma

                Label productPrice = new Label(String.valueOf(selectedProduct.getUnitPrice()));
                productPrice.setPadding(new Insets(10,0,20,10));
              //   productPrice.setAlignment(Pos.CENTER_RIGHT);
                 productList.setMargin(productPrice,new Insets(10,10,10,85));
                productList.getChildren().add(productPrice);
                //productList.setAlignment(Pos.BASELINE_RIGHT);

                /*productsPane.setRowIndex(productList, rowIndex);
                productsPane.setColumnIndex(productList, columIndex);*/

              productsPane.getChildren().add(productList);

                rowIndex++;
        }
        totalMoney.setText(String.valueOf(computeTotalMoney(productsSelected)));
    }


    public int computeTotalMoney(List<Product> productList){
            int totalProductMoney = 0;
        for (Product selectedProduct:productList) {
            totalProductMoney += selectedProduct.getUnitPrice();
        }

        return totalProductMoney;
    }
}
