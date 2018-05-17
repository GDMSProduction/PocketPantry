package com.softwaredev.groceryappv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    Context context;
    static SharedPreferences sharedPref;
    int mSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_ui);

        context = this;
        sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.pantry", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        mSize = sharedPref.getInt("size", 0);
        String temp;
        String parse[];
        pantry.clear();

        for (int i = 0; i < mSize; ++i)
        {
            temp = sharedPref.getString("item" + i, "null");
            if (!temp.equals("null"))
            {
                parse = temp.split(",");
                pantry.add(new Item(parse[0], Float.parseFloat(parse[1]), Integer.parseInt(parse[2]), Integer.parseInt(parse[3]), Integer.parseInt(parse[4]), Integer.parseInt(parse[5])));
            }
        }

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
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("item" + Integer.toString(pantry.size()), _item.itemToString());

        pantry.add(_item);
        editor.putInt("size", pantry.size());
        editor.commit();
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
        if (position > -1) {
            pantry.remove(position);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.commit();

            editor.putInt("size", pantry.size());
            for (int i = 0; i < pantry.size(); ++i)
            {
                editor.putString("item" + Integer.toString(i), pantry.get(i).itemToString());
            }
            editor.commit();
        }
    }
}
