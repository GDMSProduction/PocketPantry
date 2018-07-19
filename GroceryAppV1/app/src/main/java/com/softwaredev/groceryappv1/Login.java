package com.softwaredev.groceryappv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    SharedPreferences mSharedPref;
    String mUsername;
    EditText mUsernameInput;
    static ArrayList<String> mUsernameList  = new ArrayList<>(1);
    ArrayAdapter<String> arrAdapter;
    ListView usernameLV;
    int mSize;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionbar.setTitle("Login");

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
                        else if (menuItem.toString().equals("Spice Rack"))
                        {
                            sendSpiceRack();
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
                        else if(menuItem.toString().equals("About"))
                        {
                            sendHelp();
                        }

                        return true;
                    }
                }
        );

        mUsernameList.clear();

        mSharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.username", Context.MODE_PRIVATE);

        position = mSharedPref.getInt("usernamePosition", -1);
        mUsername = mSharedPref.getString("username" + position, "");
        mSize = mSharedPref.getInt("usernameListSize", 0);
        mUsernameInput = findViewById(R.id.usernameInput);
        mUsernameInput.setText(mUsername);

        for (int i = 0; i < mSize; ++i)
        {
            mUsernameList.add(mSharedPref.getString("username" + i, ""));
        }

        arrAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mUsernameList);
        usernameLV = findViewById(R.id.usernameListView);
        usernameLV.setAdapter(arrAdapter);

        usernameLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor _editor = mSharedPref.edit();

                mUsername = mUsernameList.get(position);
                _editor.putBoolean("signedIn", true);
                _editor.putInt("usernamePosition", position);
                _editor.commit();
                finish();
            }
        });
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

    public void sendPantry()
    {

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
    public void sendGroc()
    {
        Intent grocIntent = new Intent(this, PantryUI.class);
        grocIntent.putExtra("isPantry", false);
        startActivity(grocIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void sendRecipe()
    {
        Intent recipeIntent = new Intent(this, RecipeList.class);
        startActivity(recipeIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void sendAllergies()
    {
        Intent allergyIntent = new Intent(this, AllergyUI.class);
        startActivity(allergyIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void sendHelp() {
        Intent helpIntent = new Intent(this, HelpUI.class);
        startActivity(helpIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void saveUsername(View view)
    {
        mUsername = mUsernameInput.getText().toString();
        SharedPreferences.Editor _editor = mSharedPref.edit();

        if (mUsernameInput.getText().toString().trim().length() <= 0) {
            _editor.putString("username", mUsername);
            _editor.putBoolean("signedIn", false);
            _editor.commit();
            finish();
        }
        else if (!mUsername.equals(""))
        {
            mUsernameList.add(mUsername);
            _editor.putString("username" + (mUsernameList.size() - 1), mUsername);
            _editor.putBoolean("signedIn", true);
            _editor.putInt("usernamePosition", mUsernameList.size() - 1);
            _editor.putInt("usernameListSize", mUsernameList.size());
            _editor.commit();
            finish();
        }
        else
        {
            _editor.putBoolean("signedIn", false);
            _editor.putInt("usernamePosition", - 1);
            _editor.commit();
        }
    }
}
