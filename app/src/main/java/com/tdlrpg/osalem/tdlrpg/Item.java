package com.tdlrpg.osalem.tdlrpg;

public class Item {
    protected String name = "";
    protected double multiplier = 0.0;
    protected int amount = 0;
    private int hpRest = 0;
    private int spRest = 0;


    public Item(double mult, String n, String t, int a)
    {
        name = n;
        amount = a;
        multiplier = mult;
        if(t == "hp")
        {
            hpRest = 30;
        }

        else if(t == "sp")
        {
            spRest = 50;
        }
    }

    public Item(String n, int a)
    {
        if(n.equals("Potion"))
        {
            name = "Potion";
            multiplier = 1.0;
            hpRest = 30;
        }

        else if(n.equals("Ether"))
        {
            name = "Ether";
            multiplier = 1.0;
            spRest = 50;
        }

        else if(n.equals("Experience Token"))
        {
            name = "Experience Token";
            multiplier = 1.5;
        }

        else
        {
            name = "Experience Badge";
            multiplier = 2.0;
        }

        amount = a;
    }

    public void add(int n)
    {
        amount += n;
    }

    public void use(Member m)
    {
        amount -= 1;
        if(hpRest != 0 || spRest != 0)
        {
            m.restoreHp(hpRest);
            m.restoreSp(spRest);
        }
    }

    public int getHpRest()
    {
        return hpRest;
    }

    public int getSpRest() {
        return spRest;
    }

    public int getAmount()
    {
        return amount;
    }

    public double getMultiplier()
    {
        return multiplier;
    }
}
