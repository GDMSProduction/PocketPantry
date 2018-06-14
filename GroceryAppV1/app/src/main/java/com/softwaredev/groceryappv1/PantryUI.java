package com.softwaredev.groceryappv1;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.NotificationChannel.DEFAULT_CHANNEL_ID;

public class PantryUI extends AppCompatActivity {

    private static ArrayList<Item> pantry = new ArrayList<>(1);

   //NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(getTaskId()))
   //        .setSmallIcon(R.drawable.shitsgoingdown)
   //        .setContentTitle("Expiration!")
   //        .setContentText("Something in your pantry is going to expire!");
//
   // NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    final Calendar calendar = Calendar.getInstance();
    int Year = calendar.get(Calendar.YEAR);
    int Month = calendar.get(Calendar.MONTH);
    int Day = calendar.get(Calendar.DAY_OF_MONTH);

    private ListView pantryLV;
    private ItemAdapter adapter;
    Context context;
    static SharedPreferences sharedPref;
    int mSize;
    boolean isPantry;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_ui);

        Intent intent = getIntent();
        isPantry = intent.getBooleanExtra("isPantry", true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        if (isPantry) {
            actionbar.setTitle("My Pantry");
        }
        else
            actionbar.setTitle("Grocery List");

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

                        if (menuItem.toString().equals("Pantry") && !isPantry)
                        {
                            sendPantry();
                        }
                        else if (menuItem.toString().equals("Grocery List") && isPantry)
                        {
                            sendGroc();
                        }
                        else if (menuItem.toString().equals("Recipe List"))
                        {
                           sendRecipe();
                        }
                        else if (menuItem.toString().equals("About"))
                        {
                            sendHelp();
                        }

                        return true;
                    }
                }
        );

        context = this;

        if (isPantry)
            sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.pantry", Context.MODE_PRIVATE);
        else {
            ((TextView)findViewById(R.id.priceAndDateColumnText)).setText("Price");
            setTitle("Grocery List");
            sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.grocery", Context.MODE_PRIVATE);
        }

        mSize = sharedPref.getInt("size", 0);
        String temp;
        String parse[];
        pantry.clear();

        for (int i = 0; i < mSize; ++i)
        {
            temp = sharedPref.getString("item" + i, "null");
            if (!temp.equals("null"))
            {
                parse = temp.split("`~`");
                pantry.add(new Item(parse[0], Float.parseFloat(parse[1]), Integer.parseInt(parse[2]), Integer.parseInt(parse[3]), Integer.parseInt(parse[4]), Integer.parseInt(parse[5])));
            }
        }

        if (!isPantry)
        {
            String price = String.format("%.02f", getTotal());
            setTitle("Grocery List" + "      Total: $" + price);
        }

        FloatingActionButton fab = findViewById(R.id.switchToInput);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(PantryUI.this, AddItem.class);
                addIntent.putExtra("inList", false);
                startActivityForResult(addIntent, 1);
            }
        });

        pantryLV = findViewById(R.id.pantryListView);
        adapter = new ItemAdapter(this, pantry, isPantry);
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

        registerForContextMenu(pantryLV);

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

        if(isPantry)
        {
            menu.findItem(R.id.addOne).setTitle("Add item to grocery list");
            menu.findItem(R.id.addAll).setTitle("Add item to grocery list and remove");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Item selectedItem = pantry.get(acmi.position);
        SharedPreferences.Editor editor = sharedPref.edit();

        switch (item.getItemId()) {
            case R.id.remove:
                removeFromList(acmi.position);
                recreate();
                return true;
            case R.id.removeAll:
                pantry.clear();
                editor.clear();
                editor.commit();
                recreate();
                return true;
            case R.id.addOne:
                if (isPantry)
                    addToGrocery(selectedItem);
                else {
                    addToPantry(selectedItem);
                    removeFromList(acmi.position);
                }
                recreate();
                return true;

            case R.id.addAll:
                if (isPantry)
                {
                    addToGrocery(selectedItem);
                    removeFromList(acmi.position);
                }
                else {
                    for (int i = 0; i < pantry.size(); ++i) {
                        addToPantry(pantry.get(i));
                    }
                    pantry.clear();
                    editor.clear();
                    editor.commit();
                }
                recreate();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        pantryLV = findViewById(R.id.pantryListView);
        adapter = new ItemAdapter(this, pantry, isPantry);
        pantryLV.setAdapter(adapter);

        if (!isPantry)
        {
            String price = String.format("%.02f", getTotal());
            setTitle("Grocery List" + "      Total: $" + price);
        }
    }

    public void sendPantry()
    {

        Intent pantryIntent = new Intent(this, PantryUI.class);
        pantryIntent.putExtra("isPantry", true);
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
    public void sendHelp()
    {
        Intent helpIntent = new Intent(this, HelpUI.class);
        startActivity(helpIntent);

    }

    public static void addToList(Item _item)
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

        if ((pantry.get(position).mQuantity + quantity) < 999999999)
            pantry.get(position).mQuantity += quantity;
        else
            pantry.get(position).mQuantity = 999999999;

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("item" + Integer.toString(position), pantry.get(position).itemToString());
        editor.commit();
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

    public void addToPantry(Item item)
    {
        SharedPreferences _sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.pantry", Context.MODE_PRIVATE);

        int size = _sharedPref.getInt("size", 0);
        String temp;
        String parse[];
        boolean isInList = false;
        ArrayList<Item> _pantry = new ArrayList<>(1);

        for (int i = 0; i < size; ++i)
        {
            temp = _sharedPref.getString("item" + i, "null");
            if (!temp.equals("null"))
            {
                parse = temp.split("`~`");
                _pantry.add(new Item(parse[0], Float.parseFloat(parse[1]), Integer.parseInt(parse[2]), Integer.parseInt(parse[3]), Integer.parseInt(parse[4]), Integer.parseInt(parse[5])));
            }
        }



        for (int i = 0; i < _pantry.size(); ++i)
        {
            if (_pantry.get(i).getName().toLowerCase().equals(item.getName().toLowerCase())) {
                _pantry.get(i).setQuantity(_pantry.get(i).getQuantity() + item.getQuantity());
                isInList = true;
                break;
            }
        }

        if (!isInList)
            _pantry.add(item);

        SharedPreferences.Editor _editor = _sharedPref.edit();
        _editor.clear();
        _editor.commit();

        _editor.putInt("size", _pantry.size());
        for (int i = 0; i < _pantry.size(); ++i)
        {
            _editor.putString("item" + Integer.toString(i), _pantry.get(i).itemToString());
        }
        _editor.commit();
    }

    public void addToGrocery(Item item)
    {
        SharedPreferences _sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.grocery", Context.MODE_PRIVATE);

        int size = _sharedPref.getInt("size", 0);
        String temp;
        String parse[];
        boolean isInList = false;
        ArrayList<Item> _pantry = new ArrayList<>(1);

        for (int i = 0; i < size; ++i)
        {
            temp = _sharedPref.getString("item" + i, "null");
            if (!temp.equals("null"))
            {
                parse = temp.split("`~`");
                _pantry.add(new Item(parse[0], Float.parseFloat(parse[1]), Integer.parseInt(parse[2]), Integer.parseInt(parse[3]), Integer.parseInt(parse[4]), Integer.parseInt(parse[5])));
            }
        }

        item.setQuantity(1);

        for (int i = 0; i < _pantry.size(); ++i)
        {
            if (_pantry.get(i).getName().toLowerCase().equals(item.getName().toLowerCase())) {
                _pantry.get(i).setQuantity(_pantry.get(i).getQuantity() + 1);
                isInList = true;
                break;
            }
        }

        if (!isInList)
            _pantry.add(item);

        SharedPreferences.Editor _editor = _sharedPref.edit();
        _editor.clear();
        _editor.commit();

        _editor.putInt("size", _pantry.size());
        for (int i = 0; i < _pantry.size(); ++i)
        {
            _editor.putString("item" + Integer.toString(i), _pantry.get(i).itemToString());
        }
        _editor.commit();
    }

    public float getTotal()
    {
        float total = 0.0f;
        for (int i = 0; i < pantry.size(); ++i)
        {
            total += pantry.get(i).getPrice();
            total *= pantry.get(i).getQuantity();
        }

        return total;
    }

    public void checkExpiration()
    {
        for (int i = 0; i < pantry.size(); ++i)
        {
            if(pantry.get(i).getYear() == Year)
            {
                if(pantry.get(i).getMonth() == Month)
                {
                    if(pantry.get(i).getDay() == Day)
                    {
                       // notificationManager.notify(0 ,mBuilder.build());
                    }
                }
            }
        }
    }

    public static Item getItem (int position)
    {
        return pantry.get(position);
    }
}
