package com.shopManagement.Products;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductCompany {
private StringProperty companyID;
private StringProperty companyName;
private StringProperty companyCountry;
private StringProperty companyEmail;
private StringProperty companyAddress;
private StringProperty companyContactNumber;

    public ProductCompany(String companyID, String companyName, String companyCountry, String companyEmail, String companyAddress, String companyContactNumber) {
        this.companyID =new SimpleStringProperty(companyID);
        this.companyName = new SimpleStringProperty(companyName);
        this.companyCountry = new SimpleStringProperty(companyCountry);
        this.companyEmail = new SimpleStringProperty(companyEmail);
        this.companyAddress = new SimpleStringProperty( companyAddress);
        this.companyContactNumber = new SimpleStringProperty( companyContactNumber);
    }
   /* public ProductCompany(){
      //  this.companyName = companyName;
    }*/

    public String getCompanyID() {
        return companyID.get();
    }

    public StringProperty companyIDProperty() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID.set(companyID);
    }

    public String getCompanyName() {
        return companyName.getName();

    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getCompanyCountry() {
        return companyCountry.get();
    }

    public StringProperty companyCountryProperty() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry.set(companyCountry);
    }

    public String getCompanyEmail() {
        return companyEmail.get();
    }

    public StringProperty companyEmailProperty() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail.set(companyEmail);
    }

    public String getCompanyAddress() {
        return companyAddress.get();
    }

    public StringProperty companyAddressProperty() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress.set(companyAddress);
    }

    public String getCompanyContactNumber() {
        return companyContactNumber.get();
    }

    public StringProperty companyContactNumberProperty() {
        return companyContactNumber;
    }

    public void setCompanyContactNumber(String companyContactNumber) {
        this.companyContactNumber.set(companyContactNumber);
    }
}
