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
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.softwaredev.groceryappv1.PantryUI.StoreInFirebase;
import static com.softwaredev.groceryappv1.PantryUI.isSignedIn;


public class AllergyUI extends AppCompatActivity {

    static String test;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    static ArrayList<String> allergyList  = new ArrayList<>(1);
    ArrayAdapter<String> arrAdapter;
    static SharedPreferences sharedPref;
    int mSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy_ui);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionbar.setTitle("Allergies");

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

                        if (menuItem.toString().equals("Pantry")) {
                            sendPantry();
                        } else if (menuItem.toString().equals("Grocery List")) {
                            sendGroc();
                        }
                        else if (menuItem.toString().equals("Spice Rack")) {
                            sendSpiceRack();
                        }
                        else if (menuItem.toString().equals("Recipe List")) {
                            sendRecipe();
                        }
                        else if (menuItem.toString().equals("Login")) {
                            sendLogin();
                        }
                        else if (menuItem.toString().equals("About")) {
                            sendHelp();
                        }

                        return true;
                    }
                }
        );

        FloatingActionButton fab = findViewById(R.id.addAllergyButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(AllergyUI.this, AddItem.class);
                addIntent.putExtra("allergy", true);
                startActivityForResult(addIntent, 1);
            }
        });

        arrAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allergyList);
        ListView listView = findViewById(R.id.allergyListView);
        listView.setAdapter(arrAdapter);

        if (!PantryUI.isSignedIn) {
            sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.allergy", Context.MODE_PRIVATE);

            mSize = sharedPref.getInt("size", 0);
            allergyList.clear();
            String temp;

            for (int i = 1; i < mSize + 1; ++i) {
                temp = sharedPref.getString("allergy" + i, "!null!");
                if (!temp.equals("!null!")) {
                    allergyList.add(temp);
                }
            }
        }
        else
        {
            allergyList = PantryUI.getuser().getallergyList();
            mSize = PantryUI.getuser().getallergySize();
        }
        registerForContextMenu(listView);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!PantryUI.isSignedIn) {
            sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.allergy", Context.MODE_PRIVATE);

            mSize = sharedPref.getInt("size", 0);
            allergyList.clear();
            String temp;

            for (int i = 1; i < mSize + 1; ++i) {
                temp = sharedPref.getString("allergy" + i, "!null!");
                if (!temp.equals("!null!")) {
                    allergyList.add(temp);
                }
            }
        }
        else
        {
            allergyList = PantryUI.getuser().getallergyList();
            mSize = PantryUI.getuser().getallergySize();
        }

        arrAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allergyList);
        ListView listView = findViewById(R.id.allergyListView);
        listView.setAdapter(arrAdapter);

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

        menu.findItem(R.id.remove).setTitle("Remove item from list");
        menu.findItem(R.id.removeAll).setTitle("");
        menu.findItem(R.id.addOne).setTitle("");
        menu.findItem(R.id.addAll).setTitle("");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String selectedItem = allergyList.get(acmi.position);
        if (!selectedItem.isEmpty()) {
            removeItemFromAllergy(acmi.position);
            recreate();
            return true;
        }
        else
            return super.onContextItemSelected(item);
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void sendSpiceRack() {
        Intent pantryIntent = new Intent(this, SpiceRackUI.class);
        startActivity(pantryIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void sendGroc() {
        Intent grocIntent = new Intent(this, PantryUI.class);
        grocIntent.putExtra("isPantry", false);
        startActivity(grocIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void sendRecipe() {
        Intent recipeIntent = new Intent(this, RecipeList.class);
        startActivity(recipeIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void sendLogin() {
        Intent loginIntent = new Intent(this,Login.class);
        startActivity(loginIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void sendHelp() {
        Intent helpIntent = new Intent(this, HelpUI.class);
        startActivity(helpIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public static void addAllergy(String allergy)
    {
        allergyList.add(allergy);

        if (!PantryUI.getisSignedIn()) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("allergy" + Integer.toString(allergyList.size()), allergy);
            editor.putInt("size", allergyList.size());
            editor.apply();
        }
        else {
        PantryUI.getuser().setallergyList(allergyList);
        PantryUI.getuser().setallergySize(allergyList.size());
        PantryUI.StoreInFirebase();
        }
    }
    public static boolean CheckRec()
    {
        ArrayList<String> RecListTemp = RecipeList.RecNameList;
        if(allergyList.size() != 0) {
            for (int i = 0; i < allergyList.size(); i++) {
                for (int j = 0; j < RecListTemp.size(); j++) {
                    if (allergyList.get(i).equals(RecListTemp.get(j))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void removeItemFromAllergy(int position)
    {
        if (position > -1) {
            allergyList.remove(position);

            if (!isSignedIn) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.commit();

                editor.putInt("size", allergyList.size());
                for (int i = 0; i < allergyList.size(); ++i) {
                    editor.putString("allergy" + Integer.toString(allergyList.size()), allergyList.get(i));
                }
                editor.putInt("size", allergyList.size());
                editor.commit();
            } else {
                StoreInFirebase();
            }

        }
    }
}