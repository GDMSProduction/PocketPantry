package com.softwaredev.groceryappv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewDebug;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RecipeList extends AppCompatActivity {
    ArrayList<String> RList;
    ArrayAdapter<String> rAdapt;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        RList = new ArrayList<>();
        Populate();
        rAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, RList);
        listView = (ListView) findViewById(R.id.dynRecipeList);
        listView.setAdapter(rAdapt);
    }
    public void Populate()
    {
            RList.add("Grilled Cheesus");
            RList.add("Lemon Pepper Chicken");
    }

}
