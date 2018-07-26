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
    boolean isEditing = false;
    String mName = "";
    String mInstructions = "";
    int mPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        Intent intent = getIntent();
        isEditing = intent.getBooleanExtra("inList", false);

        if (isEditing) {
            mPosition = intent.getIntExtra("position", -1);
            mName = intent.getStringExtra("name");
            mInstructions = intent.getStringExtra("instructions");
            ingredients = PantryUI.getuser().getrecipeList().get(mPosition).GetIngredients();

            for (int i = 0; i < PantryUI.getuser().getrecipeList().get(mPosition).GetIngredients().size(); ++i)
            {
                ingredientsString.add(PantryUI.getuser().getrecipeList().get(mPosition).GetIngredients().get(i).getname());
            }

            EditText editText = findViewById(R.id.editRecNameText);
            editText.setText(mName);

            editText = findViewById(R.id.editRecDescText);
            editText.setText(mInstructions);
        }

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

                if (!isEditing) {
                    RecipeList.addToRecList(new RecipeBase(recipeName, recipeInstructions, ingredients));
                }
                else
                {
                    RecipeList.editRecipe(new RecipeBase(recipeName, recipeInstructions, ingredients), mPosition);
                }
                ingredients.clear();
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
