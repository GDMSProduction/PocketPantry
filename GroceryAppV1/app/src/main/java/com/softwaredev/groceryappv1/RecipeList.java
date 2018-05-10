package com.softwaredev.groceryappv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewDebug;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RecipeList extends AppCompatActivity {
    List<String> RList = new ArrayList<>(31);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ListView listView = (ListView) findViewById(R.id.dynRecipeList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, RList);
        listView.setAdapter(adapter);
        Populate();
    }
    public void Populate()
    {
        for(int i = 0; i < 30; ++i)
        {
            RList.add(new String("Test"));
        }
    }

}
