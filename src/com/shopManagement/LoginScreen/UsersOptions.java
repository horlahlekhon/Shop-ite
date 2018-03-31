package com.shopManagement.LoginScreen;

public enum UsersOptions {
    Admin, Nurse ;

      UsersOptions(){}


      //we can actually call the enum class name and call the method name() to access the name of the enum constant
    // but the reason we did it this way si that java will return static error when refering it directly without instantiation
    public String value() {
        return name();
    }
        public UsersOptions fromValue(String v){
            return valueOf(v);
        }
    }
