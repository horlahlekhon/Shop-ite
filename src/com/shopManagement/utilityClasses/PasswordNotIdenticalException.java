package com.shopManagement.utilityClasses;

public class PasswordNotIdenticalException extends Exception {


    /**
     * we create a custom exception in other to give us a clear API of the problem that wentb wrong in pur code
     and it add extra layer of readbalility to our code. we make the constructor to take the class Throwable as a param
     so that we can easily throw the exceptio out of ,main and catcht another exception which gives clearer view of the
     problem
     * @param message message to print to the console
     * @param causeOfException the cause of the problem
     */
    public PasswordNotIdenticalException(String message, Throwable causeOfException){
        super(message, causeOfException);

    }
}
