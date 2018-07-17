package com.softwaredev.groceryappv1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {

    String recipeName;
    String recipeInstructions;
    private static ArrayList<Item> ingredients = new ArrayList<>(1);
    private static ArrayList<String> ingredientsString = new ArrayList<> (1);
    ArrayAdapter<String> ingAdapt;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        ingAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientsString);
        listView = findViewById(R.id.ingredientsListView);
        listView.setAdapter(ingAdapt);

        Button fab = findViewById(R.id.addrecbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEditText = findViewById(R.id.editRecNameText);

                if (nameEditText.getText().toString().trim().length() <= 0)
                    return;
                else
                    recipeName = nameEditText.getText().toString();

                EditText insEditText = findViewById(R.id.editRecDescText);
                if (insEditText.getText().toString().trim().length() <= 0)
                    return;
                else
                    recipeInstructions = insEditText.getText().toString();

                RecipeList.addToRecList(new RecipeBase(recipeName, recipeInstructions, ingredients));
                finish();
            }
        });
}

    @Override
    public void onResume() {
        super.onResume();

        ingAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientsString);
        listView = findViewById(R.id.ingredientsListView);
        listView.setAdapter(ingAdapt);
    }

    public void sendAddIngredient(View view)
    {
        Intent ingredientIntent = new Intent(this, AddIngredient.class);
        startActivity(ingredientIntent);
    }

    public static void addIngredient (Item ingredient)
    {
        ingredients.add(ingredient);
        ingredientsString.add(ingredient.ingredientToString());
    }
    public static ArrayList<String> getIngredients()
    {
        return ingredientsString;
    }
}
