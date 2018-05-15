package com.softwaredev.groceryappv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RecipeExp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        intent.getStringExtra("Name");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_exp);
        DisplayRec(RecipeList.GetName());
    }
    private void DisplayRec(String name)
    {
            TextView textView = (TextView) findViewById(R.id.recTitle);
            textView.setText(name);
    }
}
