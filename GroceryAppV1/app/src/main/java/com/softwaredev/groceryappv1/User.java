package com.softwaredev.groceryappv1;

import java.util.ArrayList;

public class User {
    String mUsername;
    int pantrySize;
    ArrayList<Item> mPantry;

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
    public void setPantry (ArrayList<Item> _pantry)
    {
        mPantry = _pantry;
    }
    public void setUsername (String _userName)
    {
        mUsername = _userName;
    }
    public String getUsername ()
    {
        return mUsername;
    }
}
