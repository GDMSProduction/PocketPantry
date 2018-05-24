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

public class ItemAdapter extends ArrayAdapter<Item> {

    private Context mContext;
    boolean isPantry;

    public ItemAdapter(@NonNull Context context, @LayoutRes ArrayList<Item> list, boolean _isPantry) {
        super(context, 0, list);
        mContext = context;
        isPantry = _isPantry;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

        Item currentItem = PantryUI.getItem(position);


        TextView name = convertView.findViewById(R.id.itemName);
        name.setText(currentItem.getName());

        TextView dateAndPrice = convertView.findViewById(R.id.itemDateAndPrice);
        if (isPantry) {
            dateAndPrice.setText(currentItem.getDateString());
        }
        else {
            dateAndPrice.setText(currentItem.getPriceString());
        }

        TextView quantity = convertView.findViewById(R.id.itemQuantity);
        quantity.setText(currentItem.getQuantityString());

        return convertView;
    }
}
