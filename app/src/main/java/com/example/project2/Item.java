package com.example.project2;

public class Item {
    private String itemName;
    private int itemID;
    private int itemQty;

    //constructor
    public Item (String name, int id, int qty) {
        this.itemName = name;  //column 0
        this.itemID = id;      //column 1
        this.itemQty = qty;    //column 2
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemID() {
        return itemID;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    public void setItemID(int id) {
        this.itemID = id;
    }

    public void setItemQty(int qty) {
        this.itemQty = qty;
    }
}
