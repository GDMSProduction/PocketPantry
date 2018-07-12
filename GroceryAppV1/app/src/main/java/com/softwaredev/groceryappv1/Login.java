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
import android.widget.EditText;

public class Login extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    SharedPreferences mSharedPref;
    String mUsername;
    EditText mUsernameInput;

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

                        return true;
                    }
                }
        );

        mSharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.username", Context.MODE_PRIVATE);

        mUsername = mSharedPref.getString("username", "");
        mUsernameInput = findViewById(R.id.usernameInput);
        mUsernameInput.setText(mUsername);
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

    }
    public void sendSpiceRack() {
        Intent pantryIntent = new Intent(this, SpiceRackUI.class);
        startActivity(pantryIntent);
    }
    public void sendGroc()
    {
        Intent grocIntent = new Intent(this, PantryUI.class);
        grocIntent.putExtra("isPantry", false);
        startActivity(grocIntent);

    }
    public void sendRecipe()
    {
        Intent recipeIntent = new Intent(this, RecipeList.class);
        startActivity(recipeIntent);

    }
    public void sendAllergies()
    {
        Intent allergyIntent = new Intent(this, AllergyUI.class);
        startActivity(allergyIntent);

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
            _editor.putString("username", mUsername);
            _editor.putBoolean("signedIn", true);
            _editor.commit();
            finish();
        }
        else
        {
            _editor.putBoolean("signedIn", false);
            _editor.commit();
        }
    }
}
