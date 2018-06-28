package com.softwaredev.groceryappv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeExp extends AppCompatActivity {

    static String RecipeText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        intent.getStringExtra("Name");
        position = intent.getIntExtra("position", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_exp);
        DisplayRec(RecipeList.GetName());
        if(RecipeList.GetName().equals("Grilled Cheese"))
        {
            RecipeText = "A golden and melted blend on a traditional grilled cheese";
            DisplayIns(RecipeText);
            findViewById(R.id.StartButton).setVisibility(View.VISIBLE);
        }
        else if(RecipeList.GetName().equals("Lemon Pepper Chicken"))
        {
            RecipeText = "A crisp yet juicy chicken breast with a zest of lemon.";
            DisplayIns(RecipeText);
            findViewById(R.id.StartButton).setVisibility(View.VISIBLE);
        }
        else
        {
            DisplayRec(RecipeList.RecNameList.get(position));
            String instructions = RecipeList.RecList.get(position).instructions;
            RecipeBase recipe = RecipeList.RecList.get(position);

            ArrayList<Item> ingredients = recipe.GetIngredients();
            String description = "Ingredients: \n";
            for (int i = 0; i < ingredients.size(); ++i)
            {
                description = description + ingredients.get(i).ingredientToString() + "\n";
            }
            description = description + "\n" + instructions;
            DisplayIns(description);
        }

    }
    private void DisplayRec(String name)
    {
            TextView textView = findViewById(R.id.recTitle);
            textView.setText(name);
    }
    private void DisplayIns(String Ins)
    {
            TextView insText = findViewById(R.id.Instructions);
            insText.setText(Ins);
    }
    public void StartRec(View v)
    {
        Intent recipeIntent = new Intent(this, RecipeTut.class);
        startActivity(recipeIntent);
    }
    public void LoadIngredients()
    {
        RecipeBase recipe = RecipeList.RecList.get(position);
        ArrayList<Item> ingredients = recipe.GetIngredients();
        String description = "Ingredients: \n";
        for (int i = 0; i < ingredients.size(); ++i)
        {
            description = description + ingredients.get(i).ingredientToString() + "\n";
        }
    }
}
