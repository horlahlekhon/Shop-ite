package com.shopManagement.admin;

import com.shopManagement.DatabaseConnection.DatabaseConnection;
import com.shopManagement.LoginScreen.LoginScreenController;
import com.shopManagement.Products.Product;
import com.shopManagement.Products.ProductCompany;
import com.shopManagement.Users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.gluonhq.charm.glisten.control.TextField;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button homePageButton;




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
    Product newProduct ;




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
                ProductCompany productCompany = new ProductCompany();
                productCompany.setCompanyName(rs.getString(3));
                productsInventoryHolder.add(new Product(rs.getString(1), rs.getString(2), productCompany.getCompanyName(), rs.getString(4), rs.getString(5), rs.getString(6)));
                System.out.println(rs.getString(1) + "\t " + rs.getString(2) + " \t" + productCompany.getCompanyName() + " \t" + rs.getString(4) + " \t" + rs.getString(5) + " \t" + rs.getString(6));
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
                    ProductCompany productCompany = new ProductCompany();
                    productCompany.setCompanyName(searchResultSet.getString(3));
                    productsInventoryHolder.add(new Product(searchResultSet.getString(1), searchResultSet.getString(2), productCompany.getCompanyName(), searchResultSet.getString(4), searchResultSet.getString(5), searchResultSet.getString(6)));
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


    @FXML
    public void AddMedicines(ActionEvent event) {
        newProduct = new Product(productID.getText(),productName.getText(), companyName.getText(),productCategory.getText(),quantity.getText(),unitPrice.getText());
        PreparedStatement prepState = null;
        // ResultSet rs = null;
        try {
            Connection conn = this.dbConn.getConnection();
            String query = "INSERT INTO productInventory (ProductID,ProductName,CompanyName, ProductCategory, Quantity, UnitPrice) VALUES(?,?,?,?,?,?)";
            prepState = conn.prepareStatement(query);

            prepState.setString(1, newProduct.getProductID());
            prepState.setString(2, newProduct.getProductName());
            prepState.setString(3,newProduct.getCompanyName());
            prepState.setString(4,newProduct.getProductCategory());
            prepState.setString(5,newProduct.getQuantity());
            prepState.setString(6,newProduct.getUnitPrice());

            prepState.execute();

            conn.close();

        }catch (SQLException e){
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
}
