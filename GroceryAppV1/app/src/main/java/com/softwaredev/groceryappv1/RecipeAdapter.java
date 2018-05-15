package com.softwaredev.groceryappv1;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends ArrayAdapter<RecipeBase> {
    public Context mContext;
    public List<RecipeBase> rList = new ArrayList<>();

    public RecipeAdapter(@NonNull Context context, ArrayList<RecipeBase> list)
    {
        super(context, 0, list);
        mContext = context;
        rList = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        RecipeBase recipe = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_item,parent,false);
        }
        TextView recipeName = (TextView) convertView.findViewById(R.id.recipeName);
        TextView ingredient = (TextView) convertView.findViewById(R.id.ingredient);
        TextView instructions = (TextView) convertView.findViewById(R.id.instructions);
        TextView explain = (TextView) convertView.findViewById(R.id.explain);
        TextView quantity = (TextView) convertView.findViewById(R.id.quantity);

        recipeName.setText(recipe.recipeName);
        ingredient.setText(recipe.ingredient);
        instructions.setText(recipe.instructions);
        explain.setText(recipe.explain);
        quantity.setText(recipe.quantity);
        return convertView;
    }
}
