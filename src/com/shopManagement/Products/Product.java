package com.shopManagement.Products;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
    private final StringProperty productID;
    private final StringProperty productName;
    private final StringProperty companyName;
    private final StringProperty productCategory;
    private final IntegerProperty quantity;
    private final IntegerProperty unitPrice;





    public Product (String productID, String productName, String companyName, String productCategory, int quantity, int unitPrice){
        this.productID = new SimpleStringProperty(productID);
        this.productName = new SimpleStringProperty(productName);
     //   this.companyName = new SimpleStringProperty(companyName.getCompanyName());
        this.companyName = new SimpleStringProperty(companyName);
        this.productCategory = new SimpleStringProperty(productCategory);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.unitPrice = new SimpleIntegerProperty(unitPrice);
    }

    public String getProductID() {
        return productID.get();
    }

    public StringProperty productIDProperty() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID.set(productID);
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
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

    public String getProductCategory() {
        return productCategory.get();
    }

    public StringProperty productCategoryProperty() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory.set(productCategory);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public int getUnitPrice() {
        return unitPrice.get();
    }

    public IntegerProperty unitPriceProperty() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice.set(unitPrice);
    }
}
