package com.softwaredev.groceryappv1;

public class Item
{
    String mName;
    float mPrice;
    int mMonth;
    int mDay;
    int mYear;
    int mQuantity;

    public Item(String name, float price, int month, int day, int year)
    {
        mName = name;
        mPrice = price;
        mMonth = month;
        mDay = day;
        mYear = year;
        mQuantity = 1;
    }

    public Item(String name, int month, int day, int year)
    {
        mName = name;
        mPrice = 0.0f;
        mMonth = month;
        mDay = day;
        mYear = year;
        mQuantity = 1;
    }

    public Item(String name, float price, int month, int day, int year, int quantity)
    {
        mName = name;
        mPrice = price;
        mMonth = month;
        mDay = day;
        mYear = year;
        mQuantity = quantity;
    }

    public Item(String name, int month, int day, int year, int quantity)
    {
        mName = name;
        mPrice = 0.0f;
        mMonth = month;
        mDay = day;
        mYear = year;
        mQuantity = quantity;
    }

    public String getName()
    {
        return mName;
    }

    public String getPriceString()
    {
        return("$" + Float.toString(mPrice));
    }

    public String getDateString()
    {
        return(Integer.toString(mMonth) + "/" + Integer.toString(mDay) + "/" + Integer.toString(mYear));
    }

    public String getQuantityString(){ return Integer.toString(mQuantity); }
}
