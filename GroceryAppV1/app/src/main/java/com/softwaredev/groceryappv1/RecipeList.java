package com.softwaredev.groceryappv1;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RecipeList extends AppCompatActivity {
    ArrayList<String> RList;
    ArrayAdapter<String> rAdapt;
    ListView listView;
    static String RecName;
    static String RecipeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        RList = new ArrayList<>();
        Populate();
        rAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, RList);
        listView = (ListView) findViewById(R.id.dynRecipeList);
        listView.setAdapter(rAdapt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent RecIntent = new Intent(RecipeList.this, RecipeExp.class);
                RecIntent.putExtra("Exists", true);
                RecIntent.putExtra("Name", RList.get(position));
                RecipeName = RList.get(position).intern();
                startActivity(RecIntent);
            }
        });
    }
    public void Populate()
    {
            RList.add("Grilled Cheesus");
            RList.add("Lemon Pepper Chicken");
    }
    public static String GetName()
    {
        RecName = RecipeName;
        return RecName;
    }
}
