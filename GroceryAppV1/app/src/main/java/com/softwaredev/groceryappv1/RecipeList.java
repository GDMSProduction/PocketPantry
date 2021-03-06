package com.softwaredev.groceryappv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeList extends AppCompatActivity {
    static ArrayList<String> RecNameList  = new ArrayList<>(1);
    static ArrayList<RecipeBase> RecList = new ArrayList<>(1);
    ArrayList<RecipeBase> RecTut;
    ArrayAdapter<String> rAdapt;
    ListView listView;
    static String RecName;
    static String RecipeName;
    static SharedPreferences recSharedPref;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    int listSize;
    static User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recSharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.recipeList", Context.MODE_PRIVATE);

        listView = findViewById(R.id.dynRecipeList);

        if (AllergyUI.CheckRec())
        {
            startActivity(new Intent(RecipeList.this,popup.class));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionbar.setTitle("Recipe List");

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
                        else if (menuItem.toString().equals("Allergies"))
                        {
                            sendAllergies();
                        }
                        else if (menuItem.toString().equals("Login"))
                        {
                            sendLogin();
                        }
                        else if (menuItem.toString().equals("About"))
                        {
                            sendHelp();
                        }

                        return true;
                    }
                }
        );


        if (!PantryUI.getisSignedIn()) {
            listSize = recSharedPref.getInt("size", 0);
            String temp;
            String parse[];
            RecList.clear();
            RecNameList.clear();

            for (int i = 0; i < listSize; ++i) {
                temp = recSharedPref.getString("recipe" + i, "null");
                if (!temp.equals("null")) {
                    parse = temp.split("`~`");
                    int numIngredients = Integer.parseInt(parse[2]);
                    ArrayList<Item> ingredients = new ArrayList<>();

                    for (int j = 3; j < numIngredients * 3 + 3; j += 3) {
                        ingredients.add(new Item(parse[j + 2], Integer.parseInt(parse[j]), parse[j + 1]));
                    }

                    RecList.add(new RecipeBase(parse[0], parse[1], ingredients));
                    RecNameList.add(parse[0]);
                }
            }
        }
        else {
            RecList.clear();
            RecNameList.clear();
            mUser = PantryUI.getuser();
            RecList = PantryUI.getuser().getrecipeList();
            listSize = PantryUI.getuser().getrecipeSize();

            for (int i = 0; i < listSize; ++i)
            {
                RecNameList.add(RecList.get(i).recipeName);
            }
        }

        //RecNameList = new ArrayList<>();
        //Populate();
        rAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, RecNameList);
        listView.setAdapter(rAdapt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent RecIntent = new Intent(RecipeList.this, RecipeExp.class);
                RecIntent.putExtra("Exists", true);
                RecIntent.putExtra("Name", RecNameList.get(position));
                RecIntent.putExtra("position", position);
                RecipeName = RecNameList.get(position);
                startActivity(RecIntent);

            }
        });

        FloatingActionButton fab = findViewById(R.id.switchToAddRecipeFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRecipeIntent = new Intent(RecipeList.this, AddRecipe.class);
                startActivityForResult(addRecipeIntent, 1);

            }
        });

        registerForContextMenu(listView);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

        menu.findItem(R.id.removeAll).setTitle("Edit recipe");
        menu.findItem(R.id.addOne).setTitle("Add missing ingredients to grocery list");
        menu.findItem(R.id.addAll).setTitle("Add all ingredients to grocery list");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (acmi.position < RecList.size()) {
            RecipeBase selectedItem = RecList.get(acmi.position);
            SharedPreferences.Editor recEditor = recSharedPref.edit();

            switch (item.getItemId()) {
                case R.id.remove:
                    removeRecipe(acmi.position);
                    recreate();
                    return true;
                case R.id.removeAll: //Edit recipe
                    Intent intent = new Intent(RecipeList.this, AddRecipe.class);
                    intent.putExtra("position", acmi.position);
                    intent.putExtra("name", RecList.get(acmi.position).GetName());
                    intent.putExtra("instructions", RecList.get(acmi.position).GetIns());
                    //intent.putExtra("ingredients", RecList.get(acmi.position).GetIngredients());
                    intent.putExtra("inList", true);

                    startActivity(intent);
                    return true;
                case R.id.addOne: //Add missing ingredients to grocery list
                    for (int i = 0; i < selectedItem.GetIngredients().size(); ++i)
                    {
                        int index = PantryUI.getuser().checkInPantry(selectedItem.GetIngredients().get(i));
                        if(index < 0)
                        {
                            PantryUI.addToGrocery(selectedItem.ingredients.get(i));
                        }
                        else if(selectedItem.GetIngredients().get(i).getquantity() > PantryUI.getuser().getpantry().get(index).getquantity())
                        {
                            selectedItem.GetIngredients().get(i).setquantity(selectedItem.GetIngredients().get(i).getquantity() - PantryUI.getuser().getpantry().get(index).getquantity());
                            PantryUI.addToGrocery(selectedItem.GetIngredients().get(i));
                        }
                    }
                    if(PantryUI.getisSignedIn())
                        PantryUI.StoreInFirebase();

                    sendGroc();
                    return true;

                case R.id.addAll: //Add all ingredients to grocery list

                    for (int i = 0; i < selectedItem.GetIngredients().size(); ++i)
                    {
                        PantryUI.addToGrocery(selectedItem.ingredients.get(i));
                    }
                    if(PantryUI.getisSignedIn())
                        PantryUI.StoreInFirebase();

                    sendGroc();
                    return true;

                default:
                    return super.onContextItemSelected(item);
            }
        }
        else
            return super.onContextItemSelected(item);
    }

    public void Populate() {
        RecNameList.add("Grilled Cheese");
        RecNameList.add("Lemon Pepper Chicken");
    }

    public static String GetName() {
        RecName = RecipeName;
        return RecName;
    }
    public void sendAddRec(View v)
    {
        Intent addRecIntent = new Intent(this, AddRecipe.class);
        startActivity(addRecIntent);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        listView = findViewById(R.id.dynRecipeList);
        rAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, RecNameList);
        listView.setAdapter(rAdapt);
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
    public void sendAllergies()
    {
        Intent allergyIntent = new Intent(this, AllergyUI.class);
        startActivity(allergyIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void sendLogin()
    {
        Intent loginIntent = new Intent(this,Login.class);
        startActivity(loginIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void sendHelp()
    {
        Intent helpIntent = new Intent(this, HelpUI.class);
        startActivity(helpIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public static void addToRecList(RecipeBase recipe)
    {
        RecList.add(recipe);
        RecNameList.add((recipe.recipeName));

        if (!PantryUI.getisSignedIn()) {
            SharedPreferences.Editor editor = recSharedPref.edit();
            editor.putString("recipe" + Integer.toString(RecList.size() - 1), recipe.RecToString());

            editor.putInt("size", RecList.size());
            editor.commit();
        }
        else {
            PantryUI.getuser().setrecipeList(RecList);
            PantryUI.getuser().setrecipeSize(RecList.size());
            PantryUI.StoreInFirebase();
        }
    }

    public static void editRecipe (RecipeBase recipe, int position)
    {
        RecList.get(position).recipeName = recipe.GetName();
        RecList.get(position).instructions = recipe.GetIns();
        RecList.get(position).ingredients = recipe.GetIngredients();

        PantryUI.StoreInFirebase();
    }

    public void removeRecipe(int position)
    {
        if (position > -1) {
            RecList.remove(position);
            RecNameList.remove(position);

            if (!PantryUI.getisSignedIn()) {
                SharedPreferences.Editor editor = recSharedPref.edit();
                editor.clear();
                editor.commit();

                editor.putInt("size", RecList.size());
                for (int i = 0; i < RecList.size(); ++i) {
                    editor.putString("recipe" + Integer.toString(i), RecList.get(i).RecToString());
                }
                editor.commit();
            }
            else {
                PantryUI.getuser().setrecipeList(RecList);
                PantryUI.getuser().setrecipeSize(RecList.size());
                PantryUI.StoreInFirebase();
            }
            recreate();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
