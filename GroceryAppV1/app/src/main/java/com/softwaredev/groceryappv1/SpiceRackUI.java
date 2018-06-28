package com.softwaredev.groceryappv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SpiceRackUI extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    static ArrayList<String> spiceList  = new ArrayList<>(1);
    ArrayAdapter<String> arrAdapter;
    static SharedPreferences sharedPref;
    int mSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spice_rack_ui);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionbar.setTitle("Spice Rack");

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        if (menuItem.toString().equals("Pantry"))
                        {
                            sendPantry();
                        }
                        else if (menuItem.toString().equals("Grocery List"))
                        {
                            sendGroc();
                        }
                        else if (menuItem.toString().equals("Recipe List"))
                        {
                            sendRecipe();
                        }
                        else if (menuItem.toString().equals("Allergies"))
                        {
                            sendAllergies();
                        }
                        else if (menuItem.toString().equals("About"))
                        {
                            sendHelp();
                        }

                        return true;
                    }
                }
        );

        FloatingActionButton fab = findViewById(R.id.addSpiceButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(SpiceRackUI.this, AddItem.class);
                addIntent.putExtra("spice", true);
                startActivityForResult(addIntent, 1);
            }
        });

        arrAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, spiceList);
        ListView listView = findViewById(R.id.spiceListView);
        listView.setAdapter(arrAdapter);

        sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.spices", Context.MODE_PRIVATE);

        mSize = sharedPref.getInt("size", 0);
        spiceList.clear();
        String temp;

        for (int i = 0; i < mSize; ++i)
        {
            temp = sharedPref.getString("spice" + i, "!null!");
            if (!temp.equals("!null!"))
            {
                spiceList.add(temp);
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        arrAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, spiceList);
        ListView listView = findViewById(R.id.spiceListView);
        listView.setAdapter(arrAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendPantry() {

        Intent pantryIntent = new Intent(this, PantryUI.class);
        pantryIntent.putExtra("isPantry", true);
        startActivity(pantryIntent);

    }

    public void sendGroc() {
        Intent grocIntent = new Intent(this, PantryUI.class);
        grocIntent.putExtra("isPantry", false);
        startActivity(grocIntent);

    }

    public void sendRecipe() {
        Intent recipeIntent = new Intent(this, RecipeList.class);
        startActivity(recipeIntent);

    }
    public void sendAllergies()
    {
        Intent allergyIntent = new Intent(this, AllergyUI.class);
        startActivity(allergyIntent);

    }

    public void sendHelp() {
        Intent helpIntent = new Intent(this, HelpUI.class);
        startActivity(helpIntent);

    }

    public static void addSpice(String spice)
    {
        spiceList.add(spice);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("spice" + Integer.toString(spiceList.size()), spice);
        editor.putInt("size", spiceList.size());
        editor.apply();
    }
}
