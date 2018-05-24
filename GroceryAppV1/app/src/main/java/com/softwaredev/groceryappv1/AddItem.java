package com.softwaredev.groceryappv1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

public class AddItem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String mName;
    float mPrice = 0.0f;
    int mDay = 0;
    int mMonth = 0;
    int mYear = 0;
    int mQuantity = 1;
    int mPosition = -1;
    boolean isEditing = false;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Intent intent = getIntent();
        isEditing = intent.getBooleanExtra("inList", false);

        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        if (isEditing) {
            Button button = (Button) findViewById(R.id.removeButton);
            button.setVisibility(View.VISIBLE);
            button = (Button) findViewById(R.id.addItemButton);
            button.setText("Edit Item");

            mName = intent.getStringExtra("name");
            mPrice = intent.getFloatExtra("price", mPrice);
            mMonth = intent.getIntExtra("month", mMonth) - 1;
            mDay = intent.getIntExtra("day", mDay);
            mYear = intent.getIntExtra("year", mYear);
            mQuantity = intent.getIntExtra("quantity", mQuantity);
            mPosition = intent.getIntExtra("position", -1);

            editText = (EditText) findViewById(R.id.nameInput);
            editText.setText(mName);

            editText = (EditText) findViewById(R.id.priceInput);
            editText.setText(String.valueOf(mPrice));

            editText = (EditText) findViewById(R.id.dateInput);
            ((EditText)findViewById(R.id.dateInput)).setText(new StringBuilder().append(mMonth + 1).append("/").append(mDay).append("/").append(mYear).append(" "));

            editText = (EditText) findViewById(R.id.quantityInput);
            editText.setText(String.valueOf(mQuantity));
        }
        else {
            editText = (EditText) findViewById(R.id.dateInput);
            editText.setText(new StringBuilder().append(mMonth + 1).append("/").append(mDay).append("/").append(mYear).append(" "));
        }
    }

    public void addButtonPressed(View view)
    {

        EditText NameEditText = (EditText) findViewById(R.id.nameInput);
        EditText PriceEditText = findViewById(R.id.priceInput);
        EditText QuantEditText = findViewById(R.id.quantityInput);

        if (NameEditText.getText().toString().trim().length() <= 0)
            return;

        mName = NameEditText.getText().toString();

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
            }
            else
            {
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
        }
        else {
            if (PriceEditText.getText().toString().trim().length() <= 0 && QuantEditText.getText().toString().trim().length() <= 0) {
                PantryUI.addToList((new Item(mName, mMonth + 1, mDay, mYear)));
            } else if (QuantEditText.getText().toString().trim().length() <= 0) {
                mPrice = Float.valueOf(PriceEditText.getText().toString());
                PantryUI.addToList((new Item(mName, mPrice, mMonth + 1, mDay, mYear)));
            } else if (PriceEditText.getText().toString().trim().length() <= 0) {
                if (QuantEditText.getText().toString().length() < 10)
                    mQuantity = Integer.valueOf(QuantEditText.getText().toString());
                else
                    mQuantity = 999999999;
            } else {
                if (QuantEditText.getText().toString().length() < 10)
                    mQuantity = Integer.valueOf(QuantEditText.getText().toString());
                else
                    mQuantity = 999999999;
                mPrice = Float.valueOf(PriceEditText.getText().toString());
                PantryUI.addToList((new Item(mName, mPrice, mMonth + 1, mDay, mYear, mQuantity)));
            }
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
