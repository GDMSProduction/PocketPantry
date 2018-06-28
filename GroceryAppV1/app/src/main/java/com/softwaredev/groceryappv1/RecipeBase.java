package com.softwaredev.groceryappv1;

import android.widget.Button;

import java.util.ArrayList;

public class RecipeBase {

    String recipeName;
    String Utensils;
    String instructions;
    ArrayList<Item> ingredients = new ArrayList<>(1);


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
    public String GetUtensils()
    {
        return Utensils;
    }
    public void SetUtensils(String Uten)
    {
        Utensils = Uten;
    }
    public String GetIns()
    {
        return instructions;
    }
    public void SetIns(String Ins)
    {
        instructions = Ins;
    }
    public ArrayList<Item> GetIngredients() {return ingredients;}

    public String RecToString()
    {
        String recipe = recipeName + "`~`" + instructions + "`~`" + Integer.toString(ingredients.size());

        for (int i =0; i < ingredients.size(); ++i)
        {
            recipe = recipe + "`~`" + ingredients.get(i).storeIngredientString();
        }

        return (recipe);
    }
}
