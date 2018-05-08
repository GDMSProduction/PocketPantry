package com.softwaredev.groceryappv1;

public class Item
{
    String mName;
    float mPrice;
    int mMonth;
    int mDay;
    int mYear;

    public Item(String name, float price, int month, int day, int year)
    {
        mName = name;
        mPrice = price;
        mMonth = month;
        mDay = day;
        mYear = year;
    }

    public Item(String name, float price)
    {
        mName = name;
        mPrice = price;
        mMonth = 1;
        mDay = 1;
        mYear = 2018;
    }

    public Item(String name, int month, int day, int year)
    {
        mName = name;
        mPrice = 0.0f;
        mMonth = month;
        mDay = day;
        mYear = year;
    }

    public Item(String name)
    {
        mName = name;
        mPrice = 0.0f;
        mMonth = 1;
        mDay = 1;
        mYear = 2018;
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
}
