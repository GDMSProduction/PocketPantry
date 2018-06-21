package com.softwaredev.groceryappv1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class AddIngredient extends AppCompatActivity {

    String mName;
    String mUnit;
    int mQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);


    }

    public void addButtonPressed(View view)
    {

        EditText NameEditText = findViewById(R.id.nameInput);
        EditText QuantEditText = findViewById(R.id.quantityInput);
        EditText UnitEditText = findViewById(R.id.unitInput);

        if (NameEditText.getText().toString().trim().length() <= 0)
            return;

        mName = NameEditText.getText().toString();

        if (QuantEditText.getText().toString().length() >= 10)
            mQuantity = 999999999;
        else if (QuantEditText.getText().toString().trim().length() <= 0)
            mQuantity = 1;
        else
            mQuantity = Integer.valueOf(QuantEditText.getText().toString());

        if (UnitEditText.getText().toString().trim().length() <= 0)
            mUnit = "";
        else
            mUnit = UnitEditText.getText().toString();

        AddRecipe.addIngredient(new Item(mName, mQuantity, mUnit));

        finish();
    }

}
