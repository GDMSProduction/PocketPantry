package com.softwaredev.groceryappv1;

import java.util.ArrayList;

public class User {
    String username;
    int pantrySize;
    int grocerySize;
    int spiceSize;
    int allergySize;
    int recipeSize;
    ArrayList<Item> pantry;
    ArrayList<Item> grocery;
    ArrayList<String> spiceList;
    ArrayList<String> allergyList;
    ArrayList<RecipeBase> recipeList;

    User()
    {
        username = "";
        pantrySize = 0;
        grocerySize = 0;
        spiceSize = 0;
        allergySize = 0;
        recipeSize = 0;
        pantry = new ArrayList<>();
        grocery = new ArrayList<>();
        spiceList = new ArrayList<>();
        allergyList = new ArrayList<>();
        recipeList = new ArrayList<>();
    }

    public void setpantrySize(int size)
    {
        pantrySize = size;
    }
    public int getpantrySize()
    {
        return pantrySize;
    }
    public ArrayList<Item> getpantry()
    {
        return pantry;
    }
    public void setpantry (ArrayList<Item> _pantry) { pantry = _pantry; }
    public ArrayList<Item> getgrocery()
    {
        return grocery;
    }
    public void setgrocery (ArrayList<Item> _grocery) { grocery = _grocery; }
    public void setgrocerySize(int size)
    {
        grocerySize = size;
    }
    public int getgrocerySize()
    {
        return grocerySize;
    }
    public void setusername (String _userName) { username = _userName; }
    public String getusername ()
    {
        return username;
    }
    public ArrayList<String> getspiceList() {return spiceList;}
    public void setspiceList(ArrayList<String> _spices) {spiceList = _spices;}
    public int getspiceSize() {return spiceSize;}
    public void setspiceSize (int size) {spiceSize = size;}
    public ArrayList<String> getallergyList() {return allergyList;}
    public void setallergyList(ArrayList<String> _allergies) {allergyList = _allergies;}
    public int getallergySize() {return allergySize;}
    public void setallergySize (int size) {allergySize = size;}
    public ArrayList<RecipeBase> getrecipeList() {return recipeList; }
    public void setrecipeList (ArrayList<RecipeBase> _recipes) {recipeList = _recipes;}
    public int getrecipeSize() {return recipeSize;}
    public void setrecipeSize (int size) {recipeSize = size;}

    public int checkInPantry(Item item)
    {
        for (int i = 0; i < pantry.size(); ++i)
        {
            if (pantry.get(i).getname().toLowerCase().equals(item.getname().toLowerCase()))
                return i;
        }

        return -1;
    }

}
