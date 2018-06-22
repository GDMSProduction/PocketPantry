package com.softwaredev.groceryappv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeExp extends AppCompatActivity {

    static String RecipeText;
    int position;
    boolean complete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        intent.getStringExtra("Name");
        position = intent.getIntExtra("position", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_exp);
        DisplayRec(RecipeList.GetName());
        if(RecipeList.GetName().equals("Grilled Cheese"))
        {
            RecipeText = "A golden and melted blend on a traditional grilled cheese";
                    // "Place bread butter-side-down onto skillet bottom and add 1 slice of cheese. Butter a second slice of bread on one side and place butter-side-up on top of sandwich. Grill until lightly browned and flip over; continue grilling until cheese is melted. Repeat with remaining 2 slices of bread, butter and slice of cheese.";
            DisplayIns(RecipeText);
            findViewById(R.id.StartButton).setVisibility(View.VISIBLE);
        }
        else if(RecipeList.GetName().equals("Lemon Pepper Chicken"))
        {
            RecipeText = "Preheat oven to 350 degrees F (175 degrees C).\n" +"Place chicken in a lightly greased 9x13 inch baking dish. Season with lemon pepper, garlic powder and onion powder to taste. Bake in preheated oven for 15 minutes.\n" + "Turn over chicken pieces and add more seasoning to taste. Bake for an additional 15 minutes, or until chicken is cooked through and juices run clear.";
            DisplayIns(RecipeText);
            findViewById(R.id.StartButton).setVisibility(View.INVISIBLE);
        }
        else if(RecipeList.GetName().equals("Garlic Rosemary Lamb"))
        {
            RecipeText = "This would be way to difficult to type, we can get back to this when the tutorial system is done.";
            DisplayIns(RecipeText);
            findViewById(R.id.StartButton).setVisibility(View.INVISIBLE);
        }
        else
        {
            DisplayRec(RecipeList.RecNameList.get(position));
            //RecipeText = RecipeList.GetName();
            String instructions = RecipeList.RecList.get(position).instructions;
            RecipeBase recipe = RecipeList.RecList.get(position);

            ArrayList<Item> ingredients = recipe.GetIngredients();
            String description = "Ingredients: \n";
            for (int i = 0; i < ingredients.size(); ++i)
            {
                description = description + ingredients.get(i).ingredientToString() + "\n";
            }
            description = description + "\n" + instructions;
            DisplayIns(description);
        }

    }
    private void DisplayRec(String name)
    {
            TextView textView = findViewById(R.id.recTitle);
            textView.setText(name);
    }
    private void DisplayIns(String Ins)
    {
            TextView insText = findViewById(R.id.Instructions);
            insText.setText(Ins);
    }
    public void StartRec(View v)
    {
        Intent recipeIntent = new Intent(this, RecipeTut.class);
        startActivity(recipeIntent);
    }

}
