package com.tdlrpg.osalem.tdlrpg;

public class Item {
    String name = "";
    double multiplier = 0.0;
    int amount = 0;

    public Item(double mult, String n,int a)
    {
        name = n;
        amount = a;
        multiplier = mult;
    }
}
