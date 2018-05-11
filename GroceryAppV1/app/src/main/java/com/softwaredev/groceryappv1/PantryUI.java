package com.softwaredev.groceryappv1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class PantryUI extends AppCompatActivity {

    private static ArrayList<Item> pantry = new ArrayList<>(1);

    private ListView pantryLV;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_ui);
        Intent groceryList = getIntent();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.switchToInput);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PantryUI.this, AddItem.class));
            }
        });

        pantryLV = (ListView) findViewById(R.id.pantryListView);
        adapter = new ItemAdapter(this, pantry);
        pantryLV.setAdapter(adapter);

        //This is a test

    }

    @Override
    public void onResume()
    {
        super.onResume();
        pantryLV = (ListView) findViewById(R.id.pantryListView);
        adapter = new ItemAdapter(this, pantry);
        pantryLV.setAdapter(adapter);
    }

    public static void addToPantry(Item _item)
    {
        pantry.add(_item);
    }
}
