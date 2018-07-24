package com.softwaredev.groceryappv1;

public class Item
{
    String name;
    String unit;
    float price = 0.0f;
    int month;
    int day;
    int year;
    int quantity;

    public Item()
    {

    }

    public Item(String _name, float _price, int _month, int _day, int _year)
    {
        name = _name;
        price = _price;
        month = _month;
        day = _day;
        year = _year;
        quantity = 1;
    }

    public Item(String _name, int _month, int _day, int _year)
    {
        name = _name;
        price = 0.0f;
        month = _month;
        day = _day;
        year = _year;
        quantity = 1;
    }

    public Item(String _name, float _price, int _month, int _day, int _year, int _quantity)
    {
        name = _name;
        price = _price;
        month = _month;
        day = _day;
        year = _year;
        quantity = _quantity;
    }

    public Item(String _name, int _month, int _day, int _year, int _quantity)
    {
        name = _name;
        price = 0.0f;
        month = _month;
        day = _day;
        year = _year;
        quantity = _quantity;
    }

    public Item(String _name, int _quantity, String _unit)
    {
        name = _name;
        price = 0.0f;
        month = 0;
        day = 0;
        year = 0;
        quantity = _quantity;
        unit = _unit;
    }

    public String getname()
    {
        return name;
    }
    public float getprice() { return price; }
    public int getmonth() { return month; }
    public int getday() { return day; }
    public int getyear() { return year; }
    public int getquantity() { return quantity; }
    public void setquantity(int _quantity) {quantity = _quantity;}

    public String getPriceString()
    {
        String priceString = String.format("%.02f", price);
        return("$" + priceString);
    }

    public String getDateString()
    {
        return(Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year));
    }

    public String getQuantityString(){ return Integer.toString(quantity); }

    public String itemToString()
    {
        return (name + "`~`" + Float.toString(price) + "`~`" + Integer.toString(month) + "`~`" + Integer.toString(day) + "`~`" + Integer.toString(year) + "`~`" + Integer.toString(quantity));
    }

    public String ingredientToString()
    {
        return (Integer.toString(quantity) + " " + unit + " " + name);
    }

    public String storeIngredientString()
    {
        return (Integer.toString(quantity) + "`~`" + unit + "`~`" + name);
    }
}
