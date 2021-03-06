package com.softwaredev.groceryappv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class EntryUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_ui);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void sendPantry(View v)
    {

        Intent pantryIntent = new Intent(this, PantryUI.class);
        pantryIntent.putExtra("isPantry", true);
        startActivity(pantryIntent);

    }
    public void sendGroc(View v)
    {
        Intent grocIntent = new Intent(this, PantryUI.class);
        grocIntent.putExtra("isPantry", false);
        startActivity(grocIntent);

    }
    public void sendRecipe(View v)
    {
        Intent recipeIntent = new Intent(this, RecipeList.class);
        startActivity(recipeIntent);

    }
    public void sendHelp(View v)
    {
        Intent helpIntent = new Intent(this, HelpUI.class);
        startActivity(helpIntent);

    }

}
