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
    }

    public void buttonPressed(View view)
    {
        EditText editText = (EditText) findViewById(R.id.nameInput);
        String name = editText.getText().toString();
        editText = findViewById(R.id.priceInput);
        float price = Float.valueOf(editText.getText().toString());

        if (mMonth >= 0 && mDay > 0 && mYear > 0)
            PantryUI.addToPantry((new Item(name, price, mMonth + 1, mDay, mYear)));
        else
            PantryUI.addToPantry(new Item(name, price));


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
