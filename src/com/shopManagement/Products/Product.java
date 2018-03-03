package com.shopManagement.Products;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
    private final StringProperty productID;
    private final StringProperty productName;
    private final StringProperty companyName;
    private  final StringProperty productCategory;
    private final StringProperty quantity;
    private final StringProperty unitPrice;

    public Product (String productID, String productName, /*ProductCompany*/String companyName, String productCategory,String quantity, String unitPrice){
        this.productID = new SimpleStringProperty(productID);
        this.productName = new SimpleStringProperty(productName);
     //   this.companyName = new SimpleStringProperty(companyName.getCompanyName());
        this.companyName = new SimpleStringProperty(companyName);
        this.productCategory = new SimpleStringProperty(productCategory);
        this.quantity = new SimpleStringProperty(quantity);
        this.unitPrice = new SimpleStringProperty(unitPrice);
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
        return companyName.get();
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

    public String getQuantity() {
        return quantity.get();
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getUnitPrice() {
        return unitPrice.get();
    }

    public StringProperty unitPriceProperty() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice.set(unitPrice);
    }
}
