package com.softwaredev.groceryappv1;

import android.widget.Button;

public class RecipeBase {
    String recipeName;
    String Utensils;
    String ingredient;
    String instructions;
    public RecipeBase(String name, String Uti, String ing, String ins)
    {
        recipeName = name;
        Utensils = Uti;
        ingredient = ing;
        instructions = ins;
    }
    public String GetName()
    {
        return recipeName;
    }
    public void SetName(String N)
    {
        recipeName = N;
    }
    public String GetUtiensils()
    {
        return Utensils;
    }
    public void SetUtensils(String Utien)
    {
        Utensils = Utien;
    }
    public String GetIng()
    {
        return ingredient;
    }
    public void SetIng(String Ing)
    {
        ingredient = Ing;
    }
    public String GetIns()
    {
        return instructions;
    }
    public void SetIns(String Ins)
    {
        instructions = Ins;
    }
}
