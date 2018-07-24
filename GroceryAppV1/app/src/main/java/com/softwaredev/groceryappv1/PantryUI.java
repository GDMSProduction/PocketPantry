package com.softwaredev.groceryappv1;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.NotificationChannel.DEFAULT_CHANNEL_ID;

public class PantryUI extends AppCompatActivity {

    private static ArrayList<Item> pantry = new ArrayList<>(1);
    private static ArrayList<Item> grocery = new ArrayList<>(1);

    private static DatabaseReference mRef;

    final Calendar calendar = Calendar.getInstance();
    int Year = calendar.get(Calendar.YEAR);
    int Month = calendar.get(Calendar.MONTH);
    int Day = calendar.get(Calendar.DAY_OF_MONTH);

    private ListView pantryLV;
    private ItemAdapter adapter;
    Context context;
    static SharedPreferences sharedPref;
    static SharedPreferences groceryPref;
    int mSize;
    int mUsernamePosition;
    static boolean isPantry;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    static String username;
    static boolean isSignedIn = false;
    static User user;

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
            //checkExpiration();
        } else
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

                        if (menuItem.toString().equals("Pantry") && !isPantry) {
                            sendPantry();
                        } else if (menuItem.toString().equals("Spice Rack")) {
                            sendSpiceRack();
                        } else if (menuItem.toString().equals("Grocery List") && isPantry) {
                            sendGroc();
                        } else if (menuItem.toString().equals("Recipe List")) {
                            sendRecipe();
                        } else if (menuItem.toString().equals("Allergies")) {
                            sendAllergies();
                        } else if (menuItem.toString().equals("Login")) {
                            sendLogin();
                        } else if (menuItem.toString().equals("About")) {
                            sendHelp();
                        }

                        return true;
                    }
                }
        );

        context = this;
        groceryPref = this.getSharedPreferences("com.softwaredev.groceryappv1.pantry", Context.MODE_PRIVATE);

        sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.username", Context.MODE_PRIVATE);

        mUsernamePosition = sharedPref.getInt("usernamePosition", -1);
        username = sharedPref.getString("username" + mUsernamePosition, "");
        isSignedIn = sharedPref.getBoolean("signedIn", false);

        if (isPantry)
            sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.pantry", Context.MODE_PRIVATE);
        else {
            sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.grocery", Context.MODE_PRIVATE);
        }

        LoadList();

        FloatingActionButton fab = findViewById(R.id.switchToInput);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkExpiration(view);
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
            intent.putExtra("name", pantry.get(position).getname());
            intent.putExtra("price", pantry.get(position).getprice());
            intent.putExtra("month", pantry.get(position).getmonth());
            intent.putExtra("day", pantry.get(position).getday());
            intent.putExtra("year", pantry.get(position).getyear());
            intent.putExtra("quantity", pantry.get(position).getquantity());
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

        if (isPantry) {
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

                if (!isSignedIn) {
                    editor.clear();
                    editor.commit();
                } else
                    StoreInFirebase();
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
                if (isPantry) {
                    addToGrocery(selectedItem);
                    removeFromList(acmi.position);
                } else {
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
    public void onResume() {
        super.onResume();

        sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.username", Context.MODE_PRIVATE);

        mUsernamePosition = sharedPref.getInt("usernamePosition", -1);
        username = sharedPref.getString("username" + mUsernamePosition, "");
        isSignedIn = sharedPref.getBoolean("signedIn", false);

        if (isPantry)
            sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.pantry", Context.MODE_PRIVATE);
        else {
            sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.grocery", Context.MODE_PRIVATE);
        }


        LoadList();

        pantryLV = findViewById(R.id.pantryListView);
        adapter = new ItemAdapter(this, pantry, isPantry);
        pantryLV.setAdapter(adapter);

        if (!isPantry) {
            String total = String.format("%.02f", getTotal());
            TextView totalText = findViewById(R.id.totalText);
            totalText.setVisibility(View.VISIBLE);
            totalText.setText("Total: $" + total);
        }

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

    public void sendAllergies() {
        Intent allergyIntent = new Intent(this, AllergyUI.class);
        startActivity(allergyIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void sendLogin() {
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void sendHelp() {
        Intent helpIntent = new Intent(this, HelpUI.class);
        startActivity(helpIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public static void addToList(Item _item) {
        pantry.add(_item);
        if (!isSignedIn) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("item" + Integer.toString(pantry.size() - 1), _item.itemToString());

            editor.putInt("size", pantry.size());
            editor.commit();
        } else {
            StoreInFirebase();
        }
    }

    public static int checkInList(String name) {
        for (int i = 0; i < pantry.size(); ++i) {
            if (pantry.get(i).getname().toLowerCase().equals(name.toLowerCase()))
                return i;
        }

        return -1;
    }

    public static void editItem(int position, String name, float price, int month, int day, int year, int quantity) {
        pantry.get(position).name = name;
        pantry.get(position).price = price;
        pantry.get(position).month = month;
        pantry.get(position).day = day;
        pantry.get(position).year = year;
        pantry.get(position).quantity = quantity;

        if (!isSignedIn) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("item" + Integer.toString(position), pantry.get(position).itemToString());
            editor.commit();
        } else {
            StoreInFirebase();
        }
    }

    public static void addToQuantity(int position, int quantity) {
        pantry.get(position).quantity += quantity;

        if ((pantry.get(position).quantity + quantity) < 999999999)
            pantry.get(position).quantity += quantity;
        else
            pantry.get(position).quantity = 999999999;

        if (!isSignedIn) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("item" + Integer.toString(position), pantry.get(position).itemToString());
            editor.commit();
        } else
            StoreInFirebase();
    }

    public static void removeFromList(int position) {
        if (position > -1) {
            pantry.remove(position);

            if (!isSignedIn) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.commit();

                editor.putInt("size", pantry.size());
                for (int i = 0; i < pantry.size(); ++i) {
                    editor.putString("item" + Integer.toString(i), pantry.get(i).itemToString());
                }
                editor.commit();
            } else {
                StoreInFirebase();
            }

        }
    }

    public void addToPantry(Item item) {
        boolean isInList = false;
        if (!isSignedIn) {
            SharedPreferences _sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.pantry", Context.MODE_PRIVATE);

            int size = _sharedPref.getInt("size", 0);
            String temp;
            String parse[];
            ArrayList<Item> _pantry = new ArrayList<>(1);

            for (int i = 0; i < size; ++i) {
                temp = _sharedPref.getString("item" + i, "null");
                if (!temp.equals("null")) {
                    parse = temp.split("`~`");
                    _pantry.add(new Item(parse[0], Float.parseFloat(parse[1]), Integer.parseInt(parse[2]), Integer.parseInt(parse[3]), Integer.parseInt(parse[4]), Integer.parseInt(parse[5])));
                }
            }


            for (int i = 0; i < _pantry.size(); ++i) {
                if (_pantry.get(i).getname().toLowerCase().equals(item.getname().toLowerCase())) {
                    _pantry.get(i).setquantity(_pantry.get(i).getquantity() + item.getquantity());
                    isInList = true;
                    break;
                }

                if (!isInList)
                    _pantry.add(item);

                SharedPreferences.Editor _editor = _sharedPref.edit();
                _editor.clear();
                _editor.commit();

                _editor.putInt("size", _pantry.size());
                for (int j = 0; i < _pantry.size(); ++j) {
                    _editor.putString("item" + Integer.toString(j), _pantry.get(j).itemToString());
                }
                _editor.commit();
            }
        } else {
            for (int i = 0; i < user.getgrocery().size(); ++i) {
                if (user.getgrocery().get(i).getname().toLowerCase().equals(item.getname().toLowerCase())) {
                    grocery.get(i).setquantity(user.getgrocery().get(i).getquantity() + 1);
                    isInList = true;
                }
                if (isInList)
                    user.getgrocery().get(i).setquantity(user.getgrocery().get(i).getquantity() + item.getquantity());
                break;
            }
        }
        if (!isInList) {
            user.getgrocery().add(item);
        }
        StoreInFirebase();
    }


    public static void addToGrocery(Item item)
    {
        boolean isInList = false;

        if (!isSignedIn) {
            int size = groceryPref.getInt("size", 0);
            String temp;
            String parse[];
            ArrayList<Item> _pantry = new ArrayList<>(1);

            for (int i = 0; i < size; ++i) {
                temp = groceryPref.getString("item" + i, "null");
                if (!temp.equals("null")) {
                    parse = temp.split("`~`");
                    _pantry.add(new Item(parse[0], Float.parseFloat(parse[1]), Integer.parseInt(parse[2]), Integer.parseInt(parse[3]), Integer.parseInt(parse[4]), Integer.parseInt(parse[5])));
                }
            }

            item.setquantity(1);

            for (int i = 0; i < _pantry.size(); ++i) {
                if (_pantry.get(i).getname().toLowerCase().equals(item.getname().toLowerCase())) {
                    _pantry.get(i).setquantity(_pantry.get(i).getquantity() + 1);
                    isInList = true;
                    break;
                }
            }

            if (!isInList)
                _pantry.add(item);

            SharedPreferences.Editor _editor = groceryPref.edit();
            _editor.clear();
            _editor.commit();

            _editor.putInt("size", _pantry.size());
            for (int i = 0; i < _pantry.size(); ++i) {
                _editor.putString("item" + Integer.toString(i), _pantry.get(i).itemToString());
            }
            _editor.commit();
        }
        else
        {
            for (int i = 0; i < user.getgrocery().size(); ++i) {
                if (user.getgrocery().get(i).getname().toLowerCase().equals(item.getname().toLowerCase())) {
                    grocery.get(i).setquantity(user.getgrocery().get(i).getquantity() + 1);
                    isInList = true;
                }
                    if (isInList)
                        user.getgrocery().get(i).setquantity(user.getgrocery().get(i).getquantity() + item.getquantity());
                    break;
                }
            }
            if (!isInList)
            {
                user.getgrocery().add(item);
            }
            StoreInFirebase();
        }

    public float getTotal()
    {
        float total = 0.0f;

        if (!isSignedIn) {
            for (int i = 0; i < pantry.size(); ++i) {
                total += (pantry.get(i).getprice() * pantry.get(i).getquantity());
            }
        }
        else {
            for (int i = 0; i < user.getgrocery().size(); ++i) {
                total += (user.getgrocery().get(i).getprice() * user.getgrocery().get(i).getquantity());
            }
        }

        return total;
    }

    public static boolean getisSignedIn()
    {
        return isSignedIn;
    }

    public static User getuser()
    {
        return user;
    }

    public void checkExpiration(View view)
    {
        for (int i = 0; i < pantry.size(); ++i)
        {
            if(pantry.get(i).getyear() == Year)
            {
                if(pantry.get(i).getmonth() == Month+1)
                {
                    if(pantry.get(i).getday() == Day)
                    {
                        sendNotification(view);
                    }
                }
            }
        }
    }
    public void sendNotification(View view) {
        CharSequence name = "my_channel";
        String CHANNEL_ID = "my_channel_01";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);

        Intent intent = new Intent(this, PantryUI.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);
        mBuilder.setSmallIcon(R.drawable.shitsgoingdown);
        mBuilder.setContentTitle("Expirations");
        mBuilder.setContentText("Something in your pantry is expired!");

        notificationManager.notify((int)(System.currentTimeMillis()/1000), mBuilder.build());
    }


    public static Item getItem (int position)
    {
        return pantry.get(position);
    }

    public void LoadList () {
        if (!isSignedIn) {

            if (isPantry)
                sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.pantry", Context.MODE_PRIVATE);
            else {
                ((TextView) findViewById(R.id.priceAndDateColumnText)).setText("Price");
                setTitle("Grocery List");
                sharedPref = this.getSharedPreferences("com.softwaredev.groceryappv1.grocery", Context.MODE_PRIVATE);
            }

            mSize = sharedPref.getInt("size", 0);
            String temp;
            String parse[];
            pantry.clear();

            for (int i = 0; i < mSize; ++i) {
                temp = sharedPref.getString("item" + i, "!null!");
                if (!temp.equals("!null!")) {
                    parse = temp.split("`~`");
                    pantry.add(new Item(parse[0], Float.parseFloat(parse[1]), Integer.parseInt(parse[2]), Integer.parseInt(parse[3]), Integer.parseInt(parse[4]), Integer.parseInt(parse[5])));
                }
            }
        } else {
            mRef = FirebaseDatabase.getInstance().getReference();

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    user = new User();

                    if (dataSnapshot.hasChild(username)) {
                        user.setpantrySize(dataSnapshot.child(username).getValue(User.class).getpantrySize());
                        user.setpantry(dataSnapshot.child(username).getValue(User.class).getpantry());
                        user.setgrocerySize(dataSnapshot.child(username).getValue(User.class).getgrocerySize());
                        user.setgrocery(dataSnapshot.child(username).getValue(User.class).getgrocery());
                        user.setusername(dataSnapshot.child(username).getValue(User.class).getusername());
                        user.setspiceList(dataSnapshot.child(username).getValue(User.class).getspiceList());
                        user.setallergyList(dataSnapshot.child(username).getValue(User.class).getallergyList());
                        user.setrecipeList(dataSnapshot.child(username).getValue(User.class).getrecipeList());
                        user.setspiceSize(dataSnapshot.child(username).getValue(User.class).getspiceSize());
                        user.setallergySize(dataSnapshot.child(username).getValue(User.class).getallergySize());
                        user.setrecipeSize(dataSnapshot.child(username).getValue(User.class).getrecipeSize());

                        if (isPantry) {
                            pantry = user.getpantry();
                            grocery = user.getgrocery();
                            mSize = user.getpantrySize();
                        } else {
                            pantry = user.getgrocery();
                            grocery = user.getgrocery();
                            mSize = user.getgrocerySize();
                        }
                        username = user.getusername();
                    }
                    else
                        StoreInFirebase();

                    pantryLV = findViewById(R.id.pantryListView);
                    adapter = new ItemAdapter(context, pantry, isPantry);
                    pantryLV.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        pantryLV = findViewById(R.id.pantryListView);
        adapter = new ItemAdapter(this, pantry, isPantry);
        pantryLV.setAdapter(adapter);

            if (!isPantry) {
                String total = String.format("%.02f", getTotal());
                TextView totalText = findViewById(R.id.totalText);
                totalText.setVisibility(View.VISIBLE);
                totalText.setText("Total: $" + total);
            }

    }

    public static void StoreInFirebase () {
        mRef = FirebaseDatabase.getInstance().getReference();

        if (isPantry) {
            user.setpantry(pantry);
            user.setpantrySize(pantry.size());
        }
        else {
            user.setgrocery(pantry);
            user.setgrocerySize(pantry.size());
        }
        user.setusername(username);
        mRef.child(username).setValue(user);
    }
}

