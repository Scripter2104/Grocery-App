package com.example.blinkit;

public class GroceryModel {

    String groceryID;
    String groceryName;

    String imgURL;
    int price;
    int quantity=0;
    String unit;

    public int qty=0;

    public GroceryModel(String groceryID, String groceryName, String imgURL, int price, int quantity, String unit) {
        this.groceryID = groceryID;
        this.groceryName = groceryName;
        this.imgURL = imgURL;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getGroceryID() {
        return groceryID;
    }

    public void setGroceryID(String groceryID) {
        this.groceryID = groceryID;
    }

    public String getGroceryName() {
        return groceryName;
    }

    public void setGroceryName(String groceryName) {
        this.groceryName = groceryName;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public GroceryModel() {

    }


}
