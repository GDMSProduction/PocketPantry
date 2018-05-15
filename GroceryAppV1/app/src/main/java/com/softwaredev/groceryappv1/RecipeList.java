package com.softwaredev.groceryappv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewDebug;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RecipeList extends AppCompatActivity {
    ArrayList<RecipeBase> RList = new ArrayList<>();
    RecipeAdapter rAdapt = new RecipeAdapter(this, RList);
    ListView listView = (ListView) findViewById(R.id.dynRecipeList);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        listView.setAdapter(rAdapt);
        Populate();
    }
    public void Populate()
    {
        for(int i = 0; i < 30; ++i)
        {

        }
    }

}
