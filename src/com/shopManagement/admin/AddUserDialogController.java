package com.shopManagement.admin;

import com.gluonhq.charm.glisten.control.TextField;

import com.shopManagement.DatabaseConnection.DatabaseConnection;
import com.shopManagement.LoginScreen.UsersOptions;
import com.shopManagement.Users.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AddUserDialogController extends BorderPane implements Initializable{


    //new user components : this group of components represents the add new User scene or dialogBox as well as variables that concerns adding users
    @FXML
    private  TextField newUserID;
    @FXML
    private TextField newUserFirstname;
    @FXML
    private TextField newUserLastName;
    @FXML
    private TextField newUserEmail;
    @FXML
    private static PasswordField newUserPassword;
    @FXML
    private static  PasswordField retypePassword;
    @FXML
    private DatePicker newUserDOB;
    @FXML
    private ComboBox<UsersOptions> usersOptionsComboBox;
    @FXML
    private TextField newUserContactNumber;
    User newUser;
    @FXML
    private Button addUserButton;



    //////end of variable declaration for the new user\\\\\\\\\\\\\\\\\\\\\
    DatabaseConnection dbConn;
   // private ObservableList<Product> productsInventoryHolder;









    public AddUserDialogController(Stage newStage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../admin/AddUserDialog.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {



            this.usersOptionsComboBox.setItems(FXCollections.observableArrayList(UsersOptions.values()));

    }




    public  void addUserToDB(User newUser) {

      //  newUser = new User("43", newUserFirstName.getText(), newUserLastName.getText(), newUserDOB.getEditor().getText(), newUserPassword.getText(), newUserEmail.getText(), newUserContactNumber.getText(), newUserType.getText());
        PreparedStatement prepState = null;
        // ResultSet rs = null;
        try {
            Connection conn = this.dbConn.getConnection();
            String query = " INSERT INTO usersList (userID,firstName,lastName, DOB, password, email,contactNumber,userType) VALUES(?,?,?,?,?,?,?,?) ";
            prepState = conn.prepareStatement(query);

            prepState.setString(1, newUser.getUserID());
            prepState.setString(2, newUser.getFirstName());
            prepState.setString(3,newUser.getLastname());
            prepState.setString(4,newUser.getDOB());
            prepState.setString(5,newUser.getPassWord());
            prepState.setString(6,newUser.getEmail());
            prepState.setString(7,newUser.getContactNumber());
            prepState.setString(8,newUser.getUserType());

            prepState.execute();

            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    @FXML
    public void userAdd(){
            System.out.println("this is null"+newUserPassword.getText());
            String password = null;
            String returnedString; // this string is used to translate the exitStatus to its string meaning to be used to tell user what is wrong
            //check if the two passwords are theSame
            // if (passCheck(this.newUserPassword.getText()) == 1)

            switch (passCheck(this.newUserPassword.getText())){
                case  1 :
                    returnedString = "the password you entered contains invalid characters";
                    System.out.println(returnedString +"\t" + this.newUserPassword.getText());
                case 2:
                    returnedString = "there is not enough numbers in the password entered";
                    System.out.println(returnedString + "\t" + this.newUserPassword.getText());
                case 3:
                    returnedString = "there is not enough CAPITAL letters in the password entered";
                    System.out.println(returnedString + "\t" + this.newUserPassword.getText());
                case 4 :
                    returnedString = "the password entered is too short";
                    System.out.println(returnedString + "\t" + this.newUserPassword.getText());
                case 10:
                    returnedString = "perfect passphrase";
                    System.out.println(returnedString + "\t" + this.newUserPassword.getText());
                    password = this.newUserPassword.getText();

            }
            //set the user values
            this.newUser = new User(this.newUserID.getText(), this.newUserFirstname.getText(), this.newUserLastName.getText(),
                    this.newUserDOB.getEditor().getText(), password, this.newUserEmail.getText(), this.newUserContactNumber.getText(),
                    this.usersOptionsComboBox.getValue().toString());
            System.out.println("everything went through fine");
            System.out.println("Initial password\t\t\tpassword retyped");
            System.out.println();
            System.out.println(this.newUserPassword.getText()+"\t"+this.retypePassword.getText());

            addUserToDB(this.newUser);
            //   }else {
            System.out.println("Initial password\t\tpassword retyped");
            System.out.println();
            System.out.println(this.newUserPassword.getText()+"\t\t\t"+this.retypePassword.getText());
            //  }



        }

    /**this method checks to validate passwords with the following conditions to meet:
     * password must be @least 8 characters long
     * password must contains @Least 2 numbers value
     * password must contains only letters and numbers as valid characters
     * password must contains atLeast 2 CAPITAL letters
     *
     * @param inputPass the inputed password
     * @return returns the password if it pass all the gates
     */
    private static int passCheck(String inputPass) {

        int exitStatus = -1; //means password is invalid
        String result = "password Valid";
        int lenght = 0;
        int numCount = 0;
        int capsCount = 0;

        for (int i = 0; i < inputPass.length(); i ++){
            if ((inputPass.charAt(i) >= 47) && (inputPass.charAt(i) <=58) || (inputPass.charAt(i) >= 64) && (inputPass.charAt(i) <= 91) ||
                    ( inputPass.charAt(i) >= 97) && (inputPass.charAt(i) <= 122)) { // checking if the pass includes only letters and numbers
                //keep password
            }else {
                result= "password contains invalid characters";
                exitStatus= 1;
            }
            if (inputPass.charAt(i)>= 47 && inputPass.charAt(i) <= 58){//counting the numbers of numerical values in the password
                numCount++;
            }
            if (inputPass.charAt(i) >= 64 && inputPass.charAt(i) <= 97){//counting the amount of CAPITAL letters in the password
                capsCount++;
            }
            lenght = (i + 1);
        }// forLoop ends
        if (numCount < 2){
            result = "not enough numbers in the password";
            exitStatus= 2; //means not enough numbers in the password
        }
        if (capsCount < 2){
            result = "not enough  cAPITAL letrters in the password ";
            exitStatus = 3; //means not enough  cAPITAL letrters in the password
        }
        if (lenght < 8){
            result = "password is too short";
            exitStatus = 4; // means password is too short
        }
        if (inputPass.equals(retypePassword.getText())){
            exitStatus = 10; // means that then inputed password matches perfectly with the one retyped
        }
        return exitStatus;

    }


}
