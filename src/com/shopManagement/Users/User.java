package com.shopManagement.Users;


/**
 * @Author Olalekan Adebari nee Sisyphus
 **/

public class User {


    private String userID;
    private String firstName;
    private String lastname;
    private String DOB ;
    private String passWord ;
    private String email ;
    private String contactNumber ;
    private String userType ;



    /**
     * constructor for registering new user
     * @param firstName username of the new User
     * @param lastName
     * @param DOB      date of Birth of the registerimg user
     * @param passWord  create a new Password
     * @param email        the new User's email address
     * @param contactNumber the new user's contact number
     */
    public User(String userID,String firstName,String lastName, String DOB, String passWord, String email, String contactNumber, String userType) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastname = lastName;
        this.DOB = DOB;
       this.passWord = passWord;

        this.email = email;
        this.contactNumber = contactNumber;
    }
    /**
     * constructor fo logging in to the dashboard
     * @param firstName  existing username of the user that wants to log in
     * @param passWord  password
     */
    public User(String firstName, String passWord, String userType) {
        this.firstName = firstName;
        this.passWord = passWord;
        this.userType = userType;
    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String userName) {
        this.firstName = userName;
    }
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
