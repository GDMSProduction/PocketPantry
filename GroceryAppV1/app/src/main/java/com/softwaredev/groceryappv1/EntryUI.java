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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void sendGroc(View v)
    {
        Intent grocIntent = new Intent(this, PantryUI.class);
        startActivity(grocIntent);
    }
    public void sendRecipe(View v)
    {
        Intent RecipeIntent = new Intent(this, RecipeList.class);
        startActivity(RecipeIntent);
    }

}
