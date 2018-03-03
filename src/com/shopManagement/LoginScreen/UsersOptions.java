package com.shopManagement.LoginScreen;

public enum UsersOptions {
    Admin, Nurse ;

      UsersOptions(){}

    public String value() {
        return name();
    }
        public UsersOptions fromValue(String v){
            return valueOf(v);
        }
    }
