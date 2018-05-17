package com.softwaredev.groceryappv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RecipeExp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        intent.getStringExtra("Name");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_exp);
        DisplayRec(RecipeList.GetName());
        if(RecipeList.GetName().equals("Grilled Cheese"))
        {
            DisplayIns("Place bread butter-side-down onto skillet bottom and add 1 slice of cheese. Butter a second slice of bread on one side and place butter-side-up on top of sandwich. Grill until lightly browned and flip over; continue grilling until cheese is melted. Repeat with remaining 2 slices of bread, butter and slice of cheese.");
        }
        else if(RecipeList.GetName().equals("Lemon Pepper Chicken"))
        {
            DisplayIns("Preheat oven to 350 degrees F (175 degrees C).\n" +
                    "Place chicken in a lightly greased 9x13 inch baking dish. Season with lemon pepper, garlic powder and onion powder to taste. Bake in preheated oven for 15 minutes.\n" +
                    "Turn over chicken pieces and add more seasoning to taste. Bake for an additional 15 minutes, or until chicken is cooked through and juices run clear.");
        }
        else if(RecipeList.GetName().equals("Garlic Rosemary Lamb"))
        {
            DisplayIns("This would be way to difficult to type, we can get back to this when the tutorial system is done.");
        }
    }
    private void DisplayRec(String name)
    {
            TextView textView = (TextView) findViewById(R.id.recTitle);
            textView.setText(name);
    }
    private void DisplayIns(String Ins)
    {
            TextView insText = (TextView) findViewById(R.id.Instructions);
            insText.setText(Ins);
    }
}
