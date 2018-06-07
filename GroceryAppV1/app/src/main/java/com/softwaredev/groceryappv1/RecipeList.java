package com.softwaredev.groceryappv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    static ArrayList<String> RecNameList  = new ArrayList<>(1);;
    static ArrayList<RecipeBase> RecList = new ArrayList<>(1);
    ArrayList<RecipeBase> RecTut;
    ArrayAdapter<String> rAdapt;
    ListView listView;
    static String RecName;
    static String RecipeName;
    static SharedPreferences recSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recSharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.recipeList", Context.MODE_PRIVATE);


        int listSize = recSharedPref.getInt("size", 0);
        String temp;
        String parse[];
        RecList.clear();
        RecNameList.clear();

        for (int i = 0; i < listSize; ++i)
        {
            temp = recSharedPref.getString("recipe" + i, "null");
            if (!temp.equals("null"))
            {
                parse = temp.split(",");
                RecList.add(new RecipeBase(parse[0], parse[1]));
                RecNameList.add(parse[0]);
            }
        }

        //RecNameList = new ArrayList<>();
        Populate();
        rAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, RecNameList);
        listView = findViewById(R.id.dynRecipeList);
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

                    recreate();
                    return true;
                case R.id.addOne: //Add missing ingredients to grocery list

                    recreate();
                    return true;

                case R.id.addAll: //Add all ingredients to grocery list

                    recreate();
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
        RecNameList.add("Garlic Rosemary Lamb");
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

        int listSize = recSharedPref.getInt("size", 0);
        String temp;
        String parse[];
        RecList.clear();
        RecNameList.clear();

        for (int i = 0; i < listSize; ++i)
        {
            temp = recSharedPref.getString("recipe" + i, "null");
            if (!temp.equals("null"))
            {
                parse = temp.split(",");
                RecList.add(new RecipeBase(parse[0], parse[1]));
                RecNameList.add(parse[0]);
            }
        }
        Populate();
    }

    public static void addToRecList(RecipeBase recipe)
    {
        SharedPreferences.Editor editor = recSharedPref.edit();
        editor.putString("recipe" + Integer.toString(RecList.size()), recipe.RecToString());

        RecList.add(recipe);
        RecNameList.add((recipe.recipeName));
        editor.putInt("size", RecList.size());
        editor.commit();
    }

    public static void removeRecipe(int position)
    {
        if (position > -1) {
            RecList.remove(position);
            RecNameList.remove(position);

            SharedPreferences.Editor editor = recSharedPref.edit();
            editor.clear();
            editor.commit();

            editor.putInt("size", RecList.size());
            for (int i = 0; i < RecList.size(); ++i)
            {
                editor.putString("recipe" + Integer.toString(i), RecList.get(i).RecToString());
            }
            editor.commit();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
