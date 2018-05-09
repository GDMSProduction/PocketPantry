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
    private List<Item> itemList = new ArrayList<>();

    public ItemAdapter(@NonNull Context context, @LayoutRes ArrayList<Item> list)
    {
        super(context, 0, list);
        mContext = context;
        //itemList.clear();
        itemList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View listItem = null;
        if(convertView == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        Item currentItem = itemList.get(position);

        TextView name = (TextView)listItem.findViewById(R.id.itemName);;
        name.setText(currentItem.getName());

        TextView price = (TextView) listItem.findViewById(R.id.itemPrice);
        price.setText(currentItem.getPriceString());

        TextView date = (TextView)listItem.findViewById(R.id.itemDate);;
        date.setText(currentItem.getDateString());

        return listItem;
    }

    /*public static void clearList()
    {
        itemList.clear();
    }*/
}
