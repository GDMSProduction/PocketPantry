package com.softwaredev.groceryappv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

    }

    public void buttonPressed(View view)
    {
        EditText editText = (EditText) findViewById(R.id.nameInput);
        String name = editText.getText().toString();
        editText = findViewById(R.id.priceInput);
        float price = Float.valueOf(editText.getText().toString());

        MainActivity.addToPantry(new Item(name, price));


        finish();
    }
}
