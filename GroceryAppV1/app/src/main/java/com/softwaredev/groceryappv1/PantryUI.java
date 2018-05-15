package com.softwaredev.groceryappv1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        //Intent groceryList = getIntent();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.switchToInput);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PantryUI.this, AddItem.class);
                intent.putExtra("inList", false);
                startActivity(intent);
            }
        });

        pantryLV = (ListView) findViewById(R.id.pantryListView);
        adapter = new ItemAdapter(this, pantry);
        pantryLV.setAdapter(adapter);

        pantryLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(PantryUI.this, AddItem.class);
            intent.putExtra("inList", true);
            intent.putExtra("name", pantry.get(position).getName());
            intent.putExtra("price", pantry.get(position).getPrice());
            intent.putExtra("month", pantry.get(position).getMonth());
            intent.putExtra("day", pantry.get(position).getDay());
            intent.putExtra("year", pantry.get(position).getYear());
            intent.putExtra("quantity", pantry.get(position).getQuantity());
            intent.putExtra("position", position);

            startActivity(intent);
            }
        });
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

    public static int checkInList(String name)
    {
        for (int i = 0; i < pantry.size(); ++i)
        {
            if (pantry.get(i).getName().toLowerCase().equals(name.toLowerCase()))
                return i;
        }

        return -1;
    }

    public static void editItem(int position, String name, float price, int month, int day, int year, int quantity)
    {
        pantry.get(position).mName = name;
        pantry.get(position).mPrice = price;
        pantry.get(position).mMonth = month;
        pantry.get(position).mDay = day;
        pantry.get(position).mYear = year;
        pantry.get(position).mQuantity = quantity;
    }

    public static void addToQuantity(int position, int quantity)
    {
        pantry.get(position).mQuantity += quantity;
    }

    public static void removeFromList(int position)
    {
        if (position > -1)
            pantry.remove(position);
    }
}
