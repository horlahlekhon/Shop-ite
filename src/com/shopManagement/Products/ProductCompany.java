package com.shopManagement.Products;

public class ProductCompany {
private String companyID;
private String companyName;
private  String companyCountry;
private String companyEmail;
private String companyAddress;
private String companyContactNumber;

    public ProductCompany(String companyID, String companyName, String companyCountry, String companyEmail, String companyAddress, String companyContactNumber) {
        this.companyID = companyID;
        this.companyName = companyName;
        this.companyCountry = companyCountry;
        this.companyEmail = companyEmail;
        this.companyAddress = companyAddress;
        this.companyContactNumber = companyContactNumber;
    }
    public ProductCompany(){
      //  this.companyName = companyName;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyContactNumber() {
        return companyContactNumber;
    }

    public void setCompanyContactNumber(String companyContactNumber) {
        this.companyContactNumber = companyContactNumber;
    }
}
