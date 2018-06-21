package com.softwaredev.groceryappv1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddRecipe extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        FloatingActionButton fab = findViewById(R.id.addRecipeFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEditText = findViewById(R.id.editRecNameText);
                String recipeName;
                if (nameEditText.getText().toString().trim().length() <= 0)
                    return;
                else
                    recipeName = nameEditText.getText().toString();

                EditText insEditText = findViewById(R.id.editRecDescText);
                String recipeInstructions;
                if (insEditText.getText().toString().trim().length() <= 0)
                    return;
                else
                    recipeInstructions = insEditText.getText().toString();

                RecipeList.addToRecList(new RecipeBase(recipeName, recipeInstructions));
                finish();
            }
        });
}
}
