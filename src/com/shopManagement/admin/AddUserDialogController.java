package com.shopManagement.admin;

/**
 * @Author Olalekan Adebari nee Sisyphus
 **/

import com.jfoenix.controls.*;
import com.shopManagement.DatabaseConnection.DatabaseConnection;
import com.shopManagement.LoginScreen.UsersOptions;
import com.shopManagement.Users.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AddUserDialogController implements Initializable{


    //new user components : this group of components represents the add new User scene or dialogBox as well as variables that concerns adding users
    @FXML
    private JFXTextField newUserID;
    @FXML
    private JFXTextField newUserFirstname;
    @FXML
    private JFXTextField newUserLastName;
    @FXML
    private JFXTextField newUserEmail;
    @FXML
    private JFXPasswordField newUserPassword;
    @FXML
    private   JFXPasswordField retypePassword;
    @FXML
    private JFXDatePicker newUserDOB;
    @FXML
    private JFXComboBox<UsersOptions> usersOptionsComboBox;
    @FXML
    private JFXTextField newUserContactNumber;
   private  User newUser;
    @FXML
    private JFXButton addUserButton;

    //////end of variable declaration for the new user\\\\\\\\\\\\\\\\\\\\\
  private  DatabaseConnection dbConn;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        this.usersOptionsComboBox.setItems(FXCollections.observableArrayList(UsersOptions.values()));

        addUserButton.setOnAction(e -> addUser());

    }
    public  void addUserToDB(User newUser) {

      //  newUser = new User("43", newUserFirstName.getText(), newUserLastName.getText(), newUserDOB.getEditor().getText(), newUserPassword.getText(), newUserEmail.getText(), newUserContactNumber.getText(), newUserType.getText());
        PreparedStatement prepState;
        // ResultSet rs = null;
        try {
            Connection conn = dbConn.getConnection();
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

    private   int passwordValidation(){
        String inputPass = newUserPassword.getText();
        int exitStatus = 0;
        String result = "password Valid";
        int lenght = 0;
        int numCount = 0;
        int capsCount = 0;

        for (int i = 0; i < inputPass.length(); i ++){
            if ((inputPass.charAt(i) >= 47) && (inputPass.charAt(i) <=58) || (inputPass.charAt(i) >= 64) && (inputPass.charAt(i) <= 91) ||
                    ( inputPass.charAt(i) >= 97) && (inputPass.charAt(i) <= 122)) { // checking if the pass includes only letters and numbers
                //keep password
            }else {
               // result= "password contains invalid characters";
                exitStatus = -1; //password contains invalid characters
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
            exitStatus = 2;
        }
        if (capsCount < 2){
            result = "not enough capital letters in the password ";
            exitStatus = 3;
        }
        if (lenght < 8){
            result = "password is too short";
            exitStatus = 4;
        }
        return exitStatus;
    }
    @FXML
    public void addUser(){
        String validatedUserPassword = null;
        String validatedUserEmail;
        String  validatedContactNumber = null;
        User newUser;
        switch (passwordValidation()){
            case 1:
                System.out.println("password contain invalid characters");
                Alert invalidPass = new Alert(Alert.AlertType.ERROR);
                invalidPass.setTitle("Password Input Dialog");
                invalidPass.setHeaderText("Input Error");
                invalidPass.setContentText("Not enough capital letters in the password");
                invalidPass.showAndWait();
            case 2:
                System.out.println("not enough numbers in the password");
                Alert limitedNumbers = new Alert(Alert.AlertType.ERROR);
                limitedNumbers.setTitle("Password Input Dialog");
                limitedNumbers.setHeaderText("Input Error");
                limitedNumbers.setContentText("Not enough Numbers in the password");
                limitedNumbers.showAndWait();
            case 3:
                System.out.println("not enough capital letters in the password");
                Alert capitalLetter = new Alert(Alert.AlertType.ERROR);
                capitalLetter.setTitle("Password Input Dialog");
                capitalLetter.setHeaderText("Input Error");
                capitalLetter.setContentText("Not enough capital letters in the password");
                capitalLetter.showAndWait();
            case 4:
                System.out.println("password is too short");
                Alert passWordTooShort = new Alert(Alert.AlertType.ERROR);
                passWordTooShort.setTitle("Password Input Dialog");
                passWordTooShort.setHeaderText("Input Error");
                passWordTooShort.setContentText("the passwords is too short");
                passWordTooShort.showAndWait();
            case 0:
                if (newUserPassword.getText().equals(retypePassword.getText())) {
                    System.out.println("password valid");
                    validatedUserPassword = newUserPassword.getText();
                }else {
                    System.out.println("the password does not match");
                    Alert passwordNotMatchError = new Alert(Alert.AlertType.ERROR);
                    passwordNotMatchError.setTitle("Password Input Dialog");
                    passwordNotMatchError.setHeaderText("Input Error");
                    passwordNotMatchError.setContentText("the passwords does not match");
                    passwordNotMatchError.showAndWait();
                }
        }
        if (emailValidation() == 0){
            System.out.println("email Correct");
            validatedUserEmail = newUserEmail.getText();

        }else{
            System.out.println("email doesnt match the regex validation");
            validatedUserEmail = "";
            Alert emailNotMatchError = new Alert(Alert.AlertType.ERROR);
            emailNotMatchError.setTitle("Email Input Dialog");
            emailNotMatchError.setHeaderText("Input Error");
            emailNotMatchError.setContentText("Email entred does not is not formed correctly");
            emailNotMatchError.showAndWait();
        }
            //perform a regex that matches the entred value with all possible phoone number combination
        if (newUserContactNumber.getText().matches("([0-9]( |-)?)?(\\(?[0-9]{3}\\)?|[0-9]{3})( |-)?([0-9]{3}( |-)?[0-9]{4}|[a-zA-Z0-9]{7,})") ){
            validatedContactNumber = newUserContactNumber.getText();
        }else{
            System.out.println("contact number either conatins invald characters or is too short");
            Alert contactNumberError = new Alert(Alert.AlertType.ERROR);
            contactNumberError.setTitle("Conatct number Input Dialog");
            contactNumberError.setHeaderText("Input Error");
            contactNumberError.setContentText("contact number is not formed corretcly");
            contactNumberError.showAndWait();
        }
        newUser = new User(this.newUserID.getText(), newUserFirstname.getText(), newUserLastName.getText(), validatedUserPassword,
                newUserDOB.getEditor().getText(), validatedUserEmail, validatedContactNumber, usersOptionsComboBox.getValue().toString());
        addUserToDB(newUser);

        System.out.println(usersOptionsComboBox.getValue().toString());
    }


    public  int emailValidation() {
        String email = this.newUserEmail.getText();
        if (email.matches("[A-Za-z\\s]+[A-Za-z0-9.%-_]+@[gmail]+\\.[A-Za-z]{2,4}")){
            System.out.println("correct");
            return 0; // means that the  email matches the regex validation and passes
        }else {
            System.out.println("email does not conform with the normal email format i.e horlahlekhon@gmail.com");
            return 1; //means otherwise
        }


    }
}
