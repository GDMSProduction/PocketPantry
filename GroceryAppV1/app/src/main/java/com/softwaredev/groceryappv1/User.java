package com.softwaredev.groceryappv1;

import java.util.ArrayList;

public class User {
    String mUsername;
    int pantrySize;
    int grocerySize;
    ArrayList<Item> mPantry;
    ArrayList<Item> mGrocery;

    User()
    {
        mUsername = "";
        pantrySize = 0;
        grocerySize = 0;
        mPantry = new ArrayList<>();
        mGrocery = new ArrayList<>();
    }

    public void setPantrySize(int size)
    {
        pantrySize = size;
    }
    public int getPantrySize()
    {
        return pantrySize;
    }
    public ArrayList<Item> getPantry()
    {
        return mPantry;
    }
    public void setPantry (ArrayList<Item> _pantry) { mPantry = _pantry; }
    public ArrayList<Item> getGrocery()
    {
        return mGrocery;
    }
    public void setGrocery (ArrayList<Item> _grocery) { mGrocery = _grocery; }
    public void setGrocerySize(int size)
    {
        grocerySize = size;
    }
    public int getGrocerySize()
    {
        return grocerySize;
    }
    public void setUsername (String _userName) { mUsername = _userName; }
    public String getUsername ()
    {
        return mUsername;
    }
}
