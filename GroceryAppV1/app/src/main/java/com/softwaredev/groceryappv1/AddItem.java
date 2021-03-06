package com.softwaredev.groceryappv1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class AddItem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String mSpice;
    String mAllergy;
    String mName;
    float mPrice = 0.0f;
    int mDay = 0;
    int mMonth = 0;
    int mYear = 0;
    int mQuantity = 1;
    int mPosition = -1;
    boolean isEditing = false;
    boolean isAllergy = false;
    boolean isSpice;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Spinner dropdown = findViewById(R.id.SpiceSpinner);

        Intent intent = getIntent();
        isEditing = intent.getBooleanExtra("inList", false);
        isAllergy = intent.getBooleanExtra("allergy", false);
        isSpice = intent.getBooleanExtra("spice", false);

        if(!isAllergy && !isSpice) {

            final Calendar cal = Calendar.getInstance();
            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);

            if (isEditing) {
                dropdown.setVisibility(View.INVISIBLE);
                Button button = findViewById(R.id.removeButton);
                button.setVisibility(View.VISIBLE);
                button = findViewById(R.id.addItemButton);
                button.setText("Edit Item");

                mName = intent.getStringExtra("name");
                mPrice = intent.getFloatExtra("price", mPrice);
                mMonth = intent.getIntExtra("month", mMonth);
                mDay = intent.getIntExtra("day", mDay);
                mYear = intent.getIntExtra("year", mYear);
                mQuantity = intent.getIntExtra("quantity", mQuantity);
                mPosition = intent.getIntExtra("position", -1);

                editText = findViewById(R.id.nameInput);
                editText.setText(mName);

                editText = findViewById(R.id.priceInput);
                editText.setText(String.valueOf(mPrice));

                editText = findViewById(R.id.dateInput);
                ((EditText) findViewById(R.id.dateInput)).setText(new StringBuilder().append(mMonth).append("/").append(mDay).append("/").append(mYear).append(" "));

                editText = findViewById(R.id.quantityInput);
                editText.setText(String.valueOf(mQuantity));
            }
            else {
                dropdown.setVisibility(View.INVISIBLE);
                editText = findViewById(R.id.dateInput);
                editText.setText(new StringBuilder().append(mMonth + 1).append("/").append(mDay).append("/").append(mYear).append(" "));
            }
        }
        else{
            dropdown.setVisibility(View.INVISIBLE);
            TextView textView;
            Button button = findViewById(R.id.addItemButton);

            textView = findViewById(R.id.priceLabel);
            textView.setVisibility(View.INVISIBLE);
            textView = findViewById(R.id.dollarSign);
            textView.setVisibility(View.INVISIBLE);
            editText = findViewById(R.id.priceInput);
            editText.setVisibility(View.INVISIBLE);

            textView = findViewById(R.id.dateLabel);
            textView.setVisibility(View.INVISIBLE);
            editText = findViewById(R.id.dateInput);
            editText.setVisibility(View.INVISIBLE);

            textView = findViewById(R.id.quantityLabel);
            textView.setVisibility(View.INVISIBLE);
            editText = findViewById(R.id.quantityInput);
            editText.setVisibility(View.INVISIBLE);

            if (isAllergy) {
                dropdown.setVisibility(View.VISIBLE);
                editText = findViewById(R.id.nameInput);
                editText.setVisibility(View.INVISIBLE);
                textView = findViewById(R.id.nameLabel);
                textView.setVisibility(View.INVISIBLE);
                String[] allergies = new String[]{"Dairy","Eggs","Tree Nuts", "Peanuts", "Shellfish", "Wheat", "Soy", "Fish", "Apples", "Alcohol", "Cinnamon", "Garlic", "Citrus", "Grilled Cheese"};
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, allergies);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(adapter);
                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object allergy = adapter.getItem(position);
                        if (allergy != null)
                        {
                            mAllergy = allergy.toString();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                button.setText("Add allergy");
            }
            else if (isSpice) {
                dropdown.setVisibility(View.VISIBLE);
                editText = findViewById(R.id.nameInput);
                editText.setVisibility(View.INVISIBLE);
                textView = findViewById(R.id.nameLabel);
                textView.setVisibility(View.INVISIBLE);
                String[] spices = new String[]{"Black Pepper","Cayanne Pepper","Chili Powder", "Cinnamon", "Cumin", "Curry Powder", "Garlic Powder", "Ginger", "Salt", "Oregano", "Paprika", "Rosemary", "Thyme", "Vanilla"};
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spices);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(adapter);
                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object spice = adapter.getItem(position);
                        if (spice != null)
                        {
                            mSpice = spice.toString();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                button.setText("Add spice");
            }

        }
    }

    public void addButtonPressed(View view)
    {

        EditText NameEditText = findViewById(R.id.nameInput);
        if (isSpice)
        {
        SpiceRackUI.addSpice(mSpice);
        finish();
        }
    else if(isAllergy)
        {
            AllergyUI.addAllergy(mAllergy);
            finish();
        }

        if (NameEditText.getText().toString().trim().length() <= 0)
            return;

        mName = NameEditText.getText().toString();


        EditText PriceEditText = findViewById(R.id.priceInput);
        EditText QuantEditText = findViewById(R.id.quantityInput);

        if (!isAllergy && !isSpice) {
            if (!isEditing)
                mPosition = PantryUI.checkInList(mName);

            if (mPosition > -1) {
                if (isEditing) {
                    mPrice = Float.valueOf(PriceEditText.getText().toString());
                    if (QuantEditText.getText().toString().length() < 10)
                        mQuantity = Integer.valueOf(QuantEditText.getText().toString());
                    else
                        mQuantity = 999999999;
                    PantryUI.editItem(mPosition, mName, mPrice, mMonth, mDay, mYear, mQuantity);
                } else {
                    if (QuantEditText.getText().toString().trim().length() <= 0)
                        mQuantity = 1;
                    else {
                        if (QuantEditText.getText().toString().length() < 10)
                            mQuantity = Integer.valueOf(QuantEditText.getText().toString());
                        else
                            mQuantity = 999999999;
                    }

                    PantryUI.addToQuantity(mPosition, mQuantity);
                }
            } else {
                if (PriceEditText.getText().toString().trim().length() <= 0) {
                    mPrice = 0f;
                } else {
                    mPrice = Float.valueOf(PriceEditText.getText().toString());
                }
                if (QuantEditText.getText().toString().trim().length() <= 0) {
                    mQuantity = 1;
                } else {
                    if (QuantEditText.getText().toString().length() < 10)
                        mQuantity = Integer.valueOf(QuantEditText.getText().toString());
                    else
                        mQuantity = 999999999;
                }

                PantryUI.addToList((new Item(mName, mPrice, mMonth + 1, mDay, mYear, mQuantity)));
            }
        }
        else if (isAllergy)
        {
            AllergyUI.addAllergy(mName);
        }

        finish();
    }

    public void removeButtonPressed(View view)
    {
        PantryUI.removeFromList(mPosition);

        finish();
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        mMonth = month;
        mDay = day;
        mYear = year;
        updateDate();
    }

    private void updateDate()
    {
        ((EditText)findViewById(R.id.dateInput)).setText(new StringBuilder().append(mMonth + 1).append("/").append(mDay).append("/").append(mYear).append(" "));
    }

    public void datePicker(View view){
        EditTextDatePicker picker = new EditTextDatePicker();
        picker.show(getFragmentManager(), "date");
    }
}
