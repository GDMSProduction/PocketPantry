package com.softwaredev.groceryappv1;

public class Item
{
    String mName;
    String mUnit;
    float mPrice;
    int mMonth;
    int mDay;
    int mYear;
    int mQuantity;

    public Item()
    {

    }

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

    public Item(String name, int quantity, String unit)
    {
        mName = name;
        mPrice = 0.0f;
        mMonth = 0;
        mDay = 0;
        mYear = 0;
        mQuantity = quantity;
        mUnit = unit;
    }

    public String getName()
    {
        return mName;
    }
    public float getPrice() { return mPrice; }
    public int getMonth() { return mMonth; }
    public int getDay() { return mDay; }
    public int getYear() { return mYear; }
    public int getQuantity() { return mQuantity; }
    public void setQuantity(int quantity) {mQuantity = quantity;}

    public String getPriceString()
    {
        String price = String.format("%.02f", mPrice);
        return("$" + price);
    }

    public String getDateString()
    {
        return(Integer.toString(mMonth) + "/" + Integer.toString(mDay) + "/" + Integer.toString(mYear));
    }

    public String getQuantityString(){ return Integer.toString(mQuantity); }

    public String itemToString()
    {
        return (mName + "`~`" + Float.toString(mPrice) + "`~`" + Integer.toString(mMonth) + "`~`" + Integer.toString(mDay) + "`~`" + Integer.toString(mYear) + "`~`" + Integer.toString(mQuantity));
    }

    public String ingredientToString()
    {
        return (Integer.toString(mQuantity) + " " + mUnit + " " + mName);
    }

    public String storeIngredientString()
    {
        return (Integer.toString(mQuantity) + "`~`" + mUnit + "`~`" + mName);
    }
}
