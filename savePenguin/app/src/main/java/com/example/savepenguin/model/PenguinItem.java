package com.example.savepenguin.model;

import android.graphics.drawable.Drawable;

public class PenguinItem {
    private String itemName;
    private Drawable itemImage;
    private int itemPrice;

    public PenguinItem(String itemName, Drawable itemImage, int itemPrice) {
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemPrice = itemPrice;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Drawable getItemImage() {
        return itemImage;
    }

    public void setItemImage(Drawable itemImage) {
        this.itemImage = itemImage;
    }
}
