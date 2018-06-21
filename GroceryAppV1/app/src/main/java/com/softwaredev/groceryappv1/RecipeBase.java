package com.softwaredev.groceryappv1;

import android.widget.Button;

import java.util.ArrayList;

public class RecipeBase {
    private static ArrayList<Item> ingredients = new ArrayList<>(1);

    String recipeName;
    String Utensils;
    String instructions;

    public RecipeBase(String name, String uti, String ins)
    {
        recipeName = name;
        Utensils = uti;
        instructions = ins;
    }

    public RecipeBase(String name, String ins)
    {
        recipeName = name;
        instructions = ins;
        Utensils = " ";
    }

    public RecipeBase(String name, String ins, ArrayList<Item> ings)
    {
        recipeName = name;
        instructions = ins;
        ingredients = ings;
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
    public String GetIns()
    {
        return instructions;
    }
    public void SetIns(String Ins)
    {
        instructions = Ins;
    }

    public String RecToString()
    {
        return (recipeName + "`~`" + instructions);
    }
}
