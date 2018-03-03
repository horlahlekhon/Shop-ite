package com.shopManagement.admin;

import com.gluonhq.charm.glisten.control.TextField;
import com.shopManagement.DatabaseConnection.DatabaseConnection;
import com.shopManagement.Products.Product;
import com.shopManagement.Users.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AddUserDialogController extends BorderPane implements Initializable{


    //new user components : this group of components represents the add new User scene or dialogBox as well as variables that concerns adding users
    @FXML
    private TextField newUserID;
    @FXML
    private TextField newUserFirstName;
    @FXML
    private TextField newUserLastName;
    @FXML
    private TextField newUserEmail;
    @FXML
    private PasswordField newUserPassword;
    @FXML
    private DatePicker newUserDOB;
    @FXML
    private RadioButton userTypeRegular;
    @FXML
    private RadioButton UserTypeAdmin;
    @FXML
    private ToggleGroup userTypeToggleGroup;
    @FXML
    private TextField newUserContactNumber;
    User newUser;
    @FXML
    private Button addUserButton;

    //////end of variable declaration for the new user\\\\\\\\\\\\\\\\\\\\\
    DatabaseConnection dbConn;
   // private ObservableList<Product> productsInventoryHolder;









    public AddUserDialogController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../admin/AddUserDialog.fxml"));
        fxmlLoader.setRoot(this);
      ///  fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            this.addUserButton.setOnAction(event -> {
                this.newUser = new User(this.newUserID.getText(), this.newUserFirstName.getText(), this.newUserLastName.getText(), this.newUserDOB.getEditor().getText(), this.newUserPassword.getText(), this.newUserEmail.getText(), this.newUserContactNumber.getText(), this.userTypeToggleGroup.getToggles().toString()); //TODO usertype not yet set
                //User newUser = null;
                addUser(this.newUser);
            });
    }

    // add new User to the userlist
    @FXML
    public void addUser(User newUser) {

      //  newUser = new User("43", newUserFirstName.getText(), newUserLastName.getText(), newUserDOB.getEditor().getText(), newUserPassword.getText(), newUserEmail.getText(), newUserContactNumber.getText(), newUserType.getText());
        PreparedStatement prepState = null;
        // ResultSet rs = null;
        try {
            Connection conn = this.dbConn.getConnection();
            String query = " INSERT INTO usersList (userID,firstName,lastName, DOB, password, email,contactNumber,userType) VALUES(?,?,?,?,?,?,?,?) ";
            prepState = conn.prepareStatement(query);

            prepState.setString(1, newUser.getUserID());
            prepState.setString(2, newUser.getFirstName());
            prepState.setString(3,newUser.getDOB());
            prepState.setString(4,newUser.getPassWord());
            prepState.setString(5,newUser.getEmail());
            prepState.setString(6,newUser.getContactNumber());

            prepState.execute();

            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
