package com.softwaredev.groceryappv1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<Item> pantry = new ArrayList<>(1);

    private ListView pantryLV;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pantry.add(new Item("Apple", 0.98f, 5, 10,2018));
        pantry.add(new Item("Orange", 2.00f, 5,15,2018));
        pantry.add(new Item("Banana", 0.50f, 5,5,2018));

       /* TextView textView = (TextView) findViewById(R.id.item1);
        textView.setText(pantry.get(0).mName);

        textView = (TextView) findViewById(R.id.item1Price);
        textView.setText("$" + Float.toString(pantry.get(0).mPrice));

        textView = (TextView) findViewById(R.id.item1Exp);
        textView.setText(Integer.toString(pantry.get(0).mMonth) + "/" + Integer.toString(pantry.get(0).mDay) + "/" + Integer.toString(pantry.get(0).mYear));

        textView = (TextView) findViewById(R.id.item2);
        textView.setText(pantry.get(1).mName);

        textView = (TextView) findViewById(R.id.item2Price);
        textView.setText("$" + Float.toString(pantry.get(1).mPrice));

        textView = (TextView) findViewById(R.id.item2Exp);
        textView.setText(Integer.toString(pantry.get(1).mMonth) + "/" + Integer.toString(pantry.get(1).mDay) + "/" + Integer.toString(pantry.get(1).mYear));

        textView = (TextView) findViewById(R.id.item3);
        textView.setText(pantry.get(2).mName);

        textView = (TextView) findViewById(R.id.item3Price);
        textView.setText("$" + Float.toString(pantry.get(2).mPrice));

        textView = (TextView) findViewById(R.id.item3Exp);
        textView.setText(Integer.toString(pantry.get(2).mMonth) + "/" + Integer.toString(pantry.get(2).mDay) + "/" + Integer.toString(pantry.get(2).mYear));*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.switchToInput);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddItem.class));
            }
        });

        pantryLV = (ListView) findViewById(R.id.pantryListView);
        //ArrayAdapter<Item> arrayAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_expandable_list_item_1, pantry);
        adapter = new ItemAdapter(this, pantry);
        pantryLV.setAdapter(adapter);

    }

    public static void addToPantry(Item _item)
    {
        pantry.add(_item);
    }
}
