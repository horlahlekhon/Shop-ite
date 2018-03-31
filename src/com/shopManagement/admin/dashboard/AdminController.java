package com.shopManagement.admin.dashboard;

import com.jfoenix.controls.JFXTextField;
import com.shopManagement.DatabaseConnection.DatabaseConnection;
import com.shopManagement.LoginScreen.LoginScreenController;
import com.shopManagement.Products.Product;
import com.shopManagement.admin.newProductDialog.NewProductDialogController;
import com.shopManagement.admin.welcomePage.WelcomeController;
import com.shopManagement.shopingCart.SalesCartController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.gluonhq.charm.glisten.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @Author Olalekan Adebari ( nee Sisyphus )
 **/


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TableView<Product> medicineTable;

    @FXML
    private TableColumn<Product, String> productIDColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, String> companyNameColumn;
    @FXML
    private TableColumn<Product, String> productCategoryColumn;
    @FXML
    private TableColumn<Product, String> quantityColumn;
    @FXML
    private TableColumn<Product, String> unitPriceColumn;
    @FXML
    private Label userLoginNotif;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button homePageButton;
    @FXML
    private Button addProduct;
    @FXML
    private Button deleteProductButton;

    ///////Context Menu\\\\\\\\\\\\\\\\
    @FXML
    private  ContextMenu contextMenu;
    @FXML
    private MenuItem deleteProduct;
    @FXML
    private MenuItem editProduct;
    @FXML
    private MenuItem productDetails;
    ///////Context Menu\\\\\\\\\\\\\\\\

    ///////////Cart\\\\\\\\\\\\\\\\\\\\\
    @FXML
    private MenuButton cartMenuButton;
    @FXML
    private Button viewCartButton;
    @FXML
    private MenuItem addToCart;

    SalesCartController cartController = new SalesCartController();
    public static List<Product> productSelectedList =  new ArrayList<>();






    ////controls variable declaration for adding new medicine as well as object declarations\\\\\\\\\\\\\\\\\\\\\\
    @FXML
    private TextField productID;
    @FXML
    private TextField productName;
    @FXML
    private TextField companyName;
    @FXML
    private TextField productCategory;
    @FXML
    private TextField quantity;
    @FXML
    private TextField unitPrice;

    //////////////end of variable declaration for new medicines \\\\\\\\\\\\\\\\\\\\\\\\\
  //  Product newProduct ;




    LoginScreenController loggedInUser = new LoginScreenController();

    DatabaseConnection dbConn;
    private ObservableList<Product> productsInventoryHolder;
   WelcomeController homePage ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String name = loggedInUser.getUserName();
        userLoginNotif.setText(name);
        this.dbConn = new DatabaseConnection();

        this.searchButton.setOnAction(e -> searchForProduct(e));

        this.homePageButton.setOnAction((ActionEvent e) ->{
            Stage stageToClose = (Stage) homePageButton.getScene().getWindow();
            stageToClose.close();  //close the current Stage and
            returnToHome();}); //load a new stage to home

        this.addProduct.setOnAction(e -> AddProduct());

        this.deleteProduct.setOnAction(e -> {
            try {
                deleteProduct();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });



        MenuItem delete = new MenuItem();
        contextMenu.getItems().addAll(delete);
        deleteProduct.setOnAction(event -> {
            medicineTable.setRowFactory(
                    new Callback<TableView<Product>,TableRow<Product>>() {

                        @Override
                        public TableRow<Product> call(TableView<Product> param) {
                            System.out.println("had to stop here !");
                            TableRow<Product> row =new TableRow<>();
                            Product product = (Product) medicineTable.getSelectionModel().getSelectedItem();
                            try {
                                deleteProduct();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            medicineTable.refresh();

                            return  row;
                        }
                    }
            );

        });
        addToCart.setOnAction(event -> {
            productSelectedList =  cartMenuButtonSetup(productSelectedList);
          //  getProductList(productSelectedList);

        });




       /* medicineTable.setOnContextMenuRequested(e -> {
            contextMenu.show(medicineTable, e.getScreenX(),e.getScreenY());
        });*/



       /*medicineTable.setRowFactory(
               new Callback<TableView<Product>,TableRow<Product>>() {

                   @Override
                   public TableRow<Product> call(TableView<Product> param) {
                       final TableRow<Product> row = new TableRow<>(); // create a tablerow to hold the new row
                       ContextMenu menu = new ContextMenu();
                       MenuItem delete = new MenuItem("Delete");
                       delete.setOnAction(e -> new EventHandler<ActionEvent>(){
                           @Override
                           public void handle(ActionEvent event) {
                               Product product = (Product) medicineTable.getSelectionModel().getSelectedItem();
                               try {
                                   deleteProduct(product);
                               } catch (SQLException e) {
                                   e.printStackTrace();
                               }
                               medicineTable.refresh();
                           }


                       });
                       menu.getItems().addAll(delete);
                       menu.show(medicineTable,);
                       ///make the contextMenu show only on non-null items
                       row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(contextMenu).otherwise((ContextMenu)null));
                       return row;
                   }

               });*/

       this.viewCartButton.setOnAction(e -> loadCart());

}


    @FXML
    public void loadProductsTable() {
        PreparedStatement prepState = null;
        ResultSet rs = null;
        try {
            Connection conn = this.dbConn.getConnection();

            productsInventoryHolder = FXCollections.observableArrayList();
            String query = "SELECT * FROM ProductInventory";

            rs = conn.createStatement().executeQuery(query);

            while (rs.next()) {
              //  ProductCompany productCompany = new ProductCompany();
             //   productCompany.setCompanyName(rs.getString(3));
                productsInventoryHolder.add(new Product(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));

              //  System.out.println(rs.getString(1) + "\t " + rs.getString(2) + " \t" + rs.getString(3) + " \t" + rs.getString(4) + " \t" + rs.getString(5) + " \t" + rs.getString(6));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productID"));
        this.productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        this.companyNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("companyName"));
        this.productCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productCategory"));
        this.quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));
        this.unitPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("unitPrice"));

        this.medicineTable.setItems(null);
        this.medicineTable.setItems(this.productsInventoryHolder);
    }

    /**
     * this method handles the event of clicking the search button on the DashBoard....by searching for the inputed text in the database
     *
     * @param event
     */
    @FXML
    public void searchForProduct(javafx.event.ActionEvent event) {
        PreparedStatement preparedStatement;
        ResultSet searchResultSet;


        if (searchField.getText() != null) {
            String searchValue = this.searchField.getText();

            try {
                Connection conn = this.dbConn.getConnection();

                productsInventoryHolder = FXCollections.observableArrayList();
                String query = "SELECT * FROM ProductInventory where ProductName=?";

                preparedStatement = conn.prepareStatement(query);
                //   preparedStatement.setString(2,"1");
                preparedStatement.setString(1, searchValue);

                searchResultSet = preparedStatement.executeQuery();

                while (searchResultSet.next()) {
                 //   ProductCompany productCompany = new ProductCompany();
                   // productCompany.setCompanyName(searchResultSet.getString(3));
                    //productCompany.companyNameProperty().set(searchResultSet.getString(3));
                    productsInventoryHolder.add(new Product(searchResultSet.getString(1), searchResultSet.getString(2),
                            searchResultSet.getString(3), searchResultSet.getString(4), searchResultSet.getInt(5), searchResultSet.getInt(6)));
                    // System.out.println(rs.getString(1)+"\t "+rs.getString(2)+" \t"+productCompany.getCompanyName()+" \t"+rs.getString(4)+" \
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //associate the tablecolumn with the propertyfactory and set the type of data that will be stored in
            this.productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productID"));
            this.productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
            this.companyNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("companyName"));
            this.productCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productCategory"));
            this.quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));
            this.unitPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("unitPrice"));


            this.medicineTable.setItems(null);// set the table to empty
            //set the new items to the empty table
            this.medicineTable.setItems(productsInventoryHolder);

        } else {
            System.out.println("that shit is null " + searchField.getText());
        }
    }


    /**
     * this handles the ability to add new product to the database and load the UI dialog where user can actually enter the
     * details of the product ti be added... its controller can be found here ::: com/shopManagement/admin/NewProductDialogController.java
     */
    @FXML
    public void AddProduct() {
        Stage addProductStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            AnchorPane root = loader.load(getClass().getResource("../newProductDialog/newProductDialog.fxml"));
            NewProductDialogController dialog =loader.getController();

            Scene addProduct = new Scene(root);
            addProductStage.setScene(addProduct);
            addProductStage.initModality(Modality.APPLICATION_MODAL);
            addProductStage.setTitle("Add new Product");
            addProductStage.setResizable(false);
            addProductStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }








    }

    /**
     * this method will be the one to handle the event that pertains to returning to the home Stage from any
     * stage......
     *
     */
    public void returnToHome(){

    Stage homepage = new Stage();
    homePage = new WelcomeController(homepage);
    //FXMLLoader loader = new FXMLLoader(getClass().getResource(""))

    Scene backToHome =  new Scene(homePage);
    homepage.setScene(backToHome);
    homepage.setTitle("Home Page");
    homepage.setResizable(false);
    homepage.show();

}

    @FXML
    public void deleteProduct() throws SQLException {

        Product productToBeDeleted = medicineTable.getSelectionModel().getSelectedItem();
        System.out.println(productToBeDeleted.getProductID());
        System.out.println(productToBeDeleted.getProductName());

        PreparedStatement myState = null;
        Connection conn;
        ResultSet rs;


        Alert deletePrompt = new Alert(Alert.AlertType.CONFIRMATION);
        deletePrompt.setTitle("Delete confirmation");
        deletePrompt.setHeaderText("You are About to delete a Product from inventory!!\n");
        deletePrompt.setContentText("This Action will make the product unavailable till you add it again");
        Optional<ButtonType> result = deletePrompt.showAndWait();
        if (result.get() == ButtonType.OK){
            //if user choose ok
            try{
                conn = dbConn.getConnection();
                String deleteQuery = " DELETE FROM productInventory WHERE ProductID=? and ProductName=? ";

                myState  = conn.prepareStatement(deleteQuery);

                System.out.println(productToBeDeleted.getProductID());
                System.out.println(productToBeDeleted.getProductName());
                myState.setString(1,productToBeDeleted.getProductID());
                myState.setString(2,productToBeDeleted.getProductName());




              myState.executeUpdate();
              medicineTable.refresh();

              conn.close();

            }catch(SQLException e){
                e.printStackTrace();
            }

        }else{
            //if user closes the dialog or chooses cancel
        }
    }


    public ResultSet loadProductFromDB(){
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try{
            Connection conn = dbConn.getConnection();
            String query = " SELECT * FROM productInventory";

            preparedStatement = conn.prepareStatement(query);

            rs = preparedStatement.executeQuery();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;

    }


//////////////////////CArt\\\\\\\\\\\\\
    @FXML
    public List<Product> cartMenuButtonSetup(List<Product> listOfProductSelected){
       listOfProductSelected.add(getSelectedProduct());
        int productInCart = listOfProductSelected.size();

            for (Product p : listOfProductSelected) {
                cartMenuButton.getItems().add(new MenuItem(p.getProductName()));
                cartMenuButton.setText("items in cart :\t"+ productInCart);
            }
            return listOfProductSelected;
          //  getProductList(listOfProductSelected);
    }
    public Product getSelectedProduct(){
       return medicineTable.getFocusModel().getFocusedItem();
    }

    public static  List<Product> getProductList( ){
        return productSelectedList;
    }

    public void loadCart(){
        Stage cartStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try{
            AnchorPane root = loader.load(getClass().getResource("/com/shopManagement/shopingCart/salesCart.fxml"));
            Scene scene = new Scene(root);
            cartStage.setScene(scene);
            cartStage.initModality(Modality.APPLICATION_MODAL);
            cartStage.setTitle("Product Cart");
            cartStage.setResizable(false);
            cartStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    /////////////////////Cart\\\\\\\\\\\\\\\\\\\\
    }

