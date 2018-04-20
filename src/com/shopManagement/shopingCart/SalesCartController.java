package com.shopManagement.shopingCart;
/**
 * @Author Olalekan Adebari ( nee Sisyphus )
 **/

import com.jfoenix.controls.JFXMasonryPane;
import com.shopManagement.Products.Product;
import com.shopManagement.admin.dashboard.AdminController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class SalesCartController implements Initializable {


    private AdminController dashBoardController;

    @FXML
    private JFXMasonryPane productsMasonryPane;  // this is the  main window that hoises the hbox that houses the products

    @FXML
    private Label totalMoney;

     private Label productName;

     private Label productID;

     private TextField quantity;

    private  Label productPrice;

    private static ContextMenu contextMenu;


    int value;
    Map<TextField,Integer> priceQuantityMapping = new HashMap<TextField, Integer>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*for (Product p: dashBoardController.getProductList()) {
            System.out.println(p.toString());
        }*/


        addProductToCart(dashBoardController.getProductList());
    }

    @FXML
    public void addProductToCart(List<Product> productsSelected) {

        Random rand = new Random();
        for (Product selectedProduct : productsSelected) {
            AnchorPane productList = new AnchorPane();
            productList.setPrefSize(126,137);

            productList.setStyle("-fx-background-color:rgb("+rand.nextInt(255)+","+rand.nextInt(255)+","+rand.nextInt(255)+");");
            int latestValue = 0;

            //productList.setPrefSize(737, 8);

            productID =  new Label(selectedProduct.getProductID());
            productID.setPrefSize(124,32);
            productID.setAlignment(Pos.CENTER);
            productList.setTopAnchor(productID, (double) 2);
            productList.setLeftAnchor(productID,(double)40);
            productList.getChildren().add(productID);

            productName = new Label(selectedProduct.getProductName());
            productName.setPrefSize(124, 32);
            productList.setTopAnchor(productName,(double)25);
            productName.setAlignment(Pos.CENTER);
            productList.getChildren().add(productName);
            //productList.setMargin(productName, new Insets(13, 70, 10, 10));


            productPrice = new Label(String.valueOf(selectedProduct.getUnitPrice()));
            productPrice.setPrefSize(124,32);
            productList.setTopAnchor(productPrice, (double) 50);
            productPrice.setAlignment(Pos.CENTER);
        //    productPrice.setPadding(new Insets(10, 0, 20, 10));
           // productList.setMargin(productPrice, new Insets(10, 10, 10, 85)); //set appropriate margin to position the nodes in relation to other nodes.
            productList.getChildren().add(productPrice);

            quantity = new TextField();
            quantity.setPrefSize(150, 26);
           // productList.setMargin(quantity, new Insets(13, 90, 10, 70));
            productList.setTopAnchor(quantity,(double) 100);
            productList.getChildren().add(quantity);





            productsMasonryPane.getChildren().add(productList);

            contextMenu = new ContextMenu();
            MenuItem  delete = new MenuItem("Remove from cart");
             delete.setOnAction(e -> deleteProduct(productsSelected,selectedProduct));




            MenuItem productDetails = new MenuItem("Details");
           // productDetails.setOnAction();
            contextMenu.getItems().addAll(delete, productDetails);
            productList.setOnContextMenuRequested(event -> contextMenu.show(productList, event.getScreenX(), event.getScreenY()));


            priceQuantityMapping.put(quantity, selectedProduct.getUnitPrice());
        }
        for (Map.Entry<TextField, Integer> entry : priceQuantityMapping.entrySet()) {
            entry.getKey().setText(String.valueOf(0));

            /*
            productQuantityMapping
            * here i map the quantity textField with the price so that i can calculate prices and quantity while adding them horizontally in the  loop
            * to keep it in sync with the label that shows total shopping money
            * i also try to monitor changes in the valuues enetred into the textField by adding a changeListener class to monitor the change in any of the text Fiedl
            * then i will recompute the values from scratch by re-calling the computeotalMoney method()*/
            entry.getKey().textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    //in case the user delete the value inside the text field and leave it empty; this will make the computeTotalMoney() to return numberFormatException
                    //so i handle it by inserting 0 whenever the value is empty string
                        totalMoney.setText(String.valueOf("$" + computeTotalMoney(priceQuantityMapping))); // this should probably be a double; also it will need to add the comma at the coreesponding
                        //intervals, an algorithmic method will do to populate the label TODO

                    }
            });
        }
    }
    /*@param Map that is genersated by mapping the price and quantity togerher to determine price
    @return Int price
    *
    * */
    private int computeTotalMoney(Map<TextField, Integer> priceAndQuantityMapping) {

        int singleProduct;
        int totalMoney = 0;
        for (Map.Entry<TextField, Integer> entry : priceQuantityMapping.entrySet()) {
            if (entry.getKey().getText().equalsIgnoreCase("")) {
                singleProduct = 0;
                totalMoney += singleProduct;
            }else{
                singleProduct = Integer.parseInt(entry.getKey().getText()) * entry.getValue();
                totalMoney += singleProduct;
            }
        }
        return totalMoney;
    }

    /*
    * this method will attempt to formAT THE TOtal money of the sales , by adding neccessary comma at intervals where prices are more than a thoudsnd*/
    private String labelPriceShowingFormat(Double totalMoney){   //TODO finish this freaking method! you lazy cretin

        String money = String.valueOf(totalMoney);
        StringBuilder stringB = new StringBuilder(money);
        if (totalMoney > Double.MIN_VALUE){
            if ((totalMoney >= 1000) && (totalMoney < 10000) ){
            stringB.insert(0,',');

            }
        }
        return money;
    }

   /* public Map<TextField,Integer> deleteProductFromCart(Product toBeDeleted){



    }*/



   private List<Product> deleteProduct( List<Product> selectedProducts, Product productToBeDeleted ){

       for (Product p: selectedProducts) {
           if (p.getProductName().equals(productToBeDeleted.getProductName())){
               selectedProducts.remove(p);

           }

       }
       return selectedProducts;

   }
    }


