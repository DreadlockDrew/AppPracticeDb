package com.example.dbpractice1.DataIntegration;

public class Item {

    private String itemName;
    private int itemQuantity;
    private int itemID;

    public Item(int itemID, String itemName, int itemQuantity) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public int getItemID() {
        return itemID;
    }
}

