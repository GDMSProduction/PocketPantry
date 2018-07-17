package com.softwaredev.groceryappv1;

import java.util.ArrayList;

public class User {
    String mUsername;
    int pantrySize;
    int grocerySize;
    int spiceSize;
    int allergySize;
    int recipeSize;
    ArrayList<Item> mPantry;
    ArrayList<Item> mGrocery;
    ArrayList<String> mSpiceList;
    ArrayList<String> mAllergyList;
    ArrayList<RecipeBase> mRecipeList;

    User()
    {
        mUsername = "";
        pantrySize = 0;
        grocerySize = 0;
        spiceSize = 0;
        allergySize = 0;
        recipeSize = 0;
        mPantry = new ArrayList<>();
        mGrocery = new ArrayList<>();
        mSpiceList = new ArrayList<>();
        mAllergyList = new ArrayList<>();
        mRecipeList = new ArrayList<>();
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
    public ArrayList<String> getSpiceList() {return mSpiceList;}
    public void setSpiceList(ArrayList<String> _spices) {mSpiceList = _spices;}
    public int getSpiceSize() {return spiceSize;}
    public void setSpiceSize (int size) {spiceSize = size;}
    public ArrayList<String> getAllergyList() {return mAllergyList;}
    public void setAllergyList(ArrayList<String> _allergies) {mAllergyList = _allergies;}
    public int getAllergySize() {return allergySize;}
    public void setAllergySize (int size) {allergySize = size;}
    public ArrayList<RecipeBase> getRecipeList() {return mRecipeList; }
    public void setRecipeList (ArrayList<RecipeBase> _recipes) {mRecipeList = _recipes;}
    public int getRecipeSize() {return recipeSize;}
    public void setRecipeSize (int size) {recipeSize = size;}

}
