package com.shopManagement.LoginScreen;

/**
 * @Author Olalekan Adebari nee Sisyphus
 **/

import com.shopManagement.Users.User;
import com.shopManagement.admin.AdminController;
import com.shopManagement.admin.WelcomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.gluonhq.charm.glisten.control.ToggleButtonGroup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.shopManagement.LoginScreen.UsersOptions.Nurse;


public class LoginScreenController implements Initializable {

    LoginModel loginModel = new LoginModel();
    private User user ;
    UsersOptions usersOptions ;

    @FXML
    public TextField userName;
    @FXML
    private PasswordField passWord;
    @FXML
    private Button login;
    @FXML
    private ToggleButton adminRadioButton;
    @FXML
    private ToggleButton nurseRadioButton;
    @FXML
    private ToggleButtonGroup userToggleGroup;
    @FXML
    private Label dbStatus;

   public String userNameText = null;
    //AdminController adminController = new AdminController();



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        if (this.loginModel.isDbConnected()) {
            this.dbStatus.setText("connected to database");
        } else {
            this.dbStatus.setText("database not connected");
        }

        this.adminRadioButton.setUserData(UsersOptions.Admin);
        this.nurseRadioButton.setUserData(Nurse);



    }

    public static ToggleButton getSelectedButton(ToggleButtonGroup tbg) {
        for (ToggleButton tb : tbg.getToggles()) {
            if (tb.isSelected())
                return tb;

        }
        return null;

    }
    public void userValidation(){

    }

    /**
     *  this method handles the event of the login button on the userLogin stage...
     * @param event
     */
    @FXML
    public void login(ActionEvent event) {
       // System.out.println("button clicks and we registerd db "+getSelectedButton(this.userToggleGroup).getText());

            try {
                // create a user type that takes all the detaisls in the login foelds as its paramtre
                user = new User(this.userName.getText(), this.passWord.getText(), getSelectedButton(this.userToggleGroup).getText());

                // if the details that is entred into the User paramtre matches the data for login users in the databse then we are logged in
                if (this.loginModel.isLogin(user)) {
                    //close the current stage so that new one can come in
                    Stage stage = (Stage) this.login.getScene().getWindow();
                    stage.close();

                    //switch among the clickeable toggle buttons which user to log into based on the previous checks above in this method
                    switch (getSelectedButton(this.userToggleGroup).getText()) {
                        case "Admin":
                            adminLogin();
                            break;
                        case "Nurse":
                            nurseLogin();
                            break;
                    }
                } else {
               /* System.out.println(this.userName.getText());
                System.out.println(this.passWord.getText());
                System.out.println(getSelectedButton(this.userToggleGroup).getText());*/
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            /*System.out.println(this.userName.getText());
            System.out.println(this.passWord.getText());
            System.out.println(getSelectedButton(this.userToggleGroup).getText());*/
            }
       /* }else{
            System.out.println("hell we didnt pass that crazy enum test");
        }*/


      //  System.out.println(getUserName());
        userNameText = this.userName.getText();
    }

    private void nurseLogin() {
    }

    public void adminLogin() {
        Stage adminStage = new Stage();
            WelcomeController welcomeStage = new WelcomeController(adminStage);
            Scene scene = new Scene(welcomeStage);

            adminStage.setScene(scene);
            adminStage.setTitle("Admin - Welcome");
            adminStage.setResizable(false);
            adminStage.show();


    }
    public String  getUserName(){

        return userNameText;
    }
}
