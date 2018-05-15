package com.softwaredev.groceryappv1;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

public class AddItem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    int mDay = 0;
    int mMonth = 0;
    int mYear = 0;
    EditText dateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        dateEditText = (EditText) findViewById(R.id.dateInput);
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        ((EditText)findViewById(R.id.dateInput)).setText(new StringBuilder().append(mMonth + 1).append("/").append(mDay).append("/").append(mYear).append(" "));
    }

    public void buttonPressed(View view)
    {

        EditText NameEditText = (EditText) findViewById(R.id.nameInput);
        EditText PriceEditText = findViewById(R.id.priceInput);
        EditText QuantEditText = findViewById(R.id.quantityInput);

        if (NameEditText.getText().toString().trim().length() <= 0)
            return;

        String name = NameEditText.getText().toString();

        if (PriceEditText.getText().toString().trim().length() <= 0 && QuantEditText.getText().toString().trim().length() <= 0) {
            PantryUI.addToPantry((new Item(name, mMonth + 1, mDay, mYear)));
        }
        else if (QuantEditText.getText().toString().trim().length() <= 0){
            float price = Float.valueOf(PriceEditText.getText().toString());
            PantryUI.addToPantry((new Item(name, price, mMonth + 1, mDay, mYear)));
        }
        else if (PriceEditText.getText().toString().trim().length() <= 0){
            int quantity = Integer.valueOf(QuantEditText.getText().toString());
            PantryUI.addToPantry((new Item(name,mMonth + 1, mDay, mYear, quantity)));
        }
        else {
            int quantity = Integer.valueOf(QuantEditText.getText().toString());
            float price = Float.valueOf(PriceEditText.getText().toString());
            PantryUI.addToPantry((new Item(name, price, mMonth + 1, mDay, mYear, quantity)));
        }


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
