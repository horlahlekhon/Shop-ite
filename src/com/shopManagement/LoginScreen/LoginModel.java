package com.shopManagement.LoginScreen;

import com.shopManagement.DatabaseConnection.DatabaseConnection;
import com.shopManagement.Users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    private Connection conn;

    public LoginModel (){
        try{
            conn = DatabaseConnection.getConnection();

        }catch (Exception e){
            e.printStackTrace();
        }
        if (this.conn == null){
            System.exit(1);
        }
    }
    /**
     * checks dataase connection if valid
     * @return return a boolean result of the corresponding connection validity
     */
    public boolean isDbConnected(){
        return this.conn != null ;
    }
   // String userName, String passWord, String userOption

    public  boolean isLogin(User user){
        String sql = "SELECT * FROM userlogin where userName=? and passWord=? and division=?";
        PreparedStatement prepState = null;
        ResultSet myRs  = null;
        try{
             prepState = this.conn.prepareStatement(sql);
            prepState.setString(1, user.getFirstName());
            prepState.setString(2,user.getPassWord());
            prepState.setString(3, user.getUserType());

             myRs = prepState.executeQuery();

            if (myRs.next()){
                return  true;
            }else
                return  false;


        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                myRs.close();
                prepState.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
