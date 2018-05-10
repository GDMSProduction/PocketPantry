package com.softwaredev.groceryappv1;

public class RecipeBase {
    String recipeName;
    int quantity;
    String ingredient;
    String instructions;
    String explain;
    public RecipeBase(String name, int q, String ing, String ins, String Ex)
    {
        recipeName = name;
        quantity = q;
        ingredient = ing;
        instructions = ins;
        explain = Ex;
    }
    public String GetName()
    {
        return recipeName;
    }
}
