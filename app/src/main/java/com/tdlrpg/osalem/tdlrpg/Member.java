package com.tdlrpg.osalem.tdlrpg;

import android.content.Intent;

import java.util.ArrayList;

public class Member {
    protected static String name = "";
    protected static int level = 0;
    protected static int hp = 0;
    private static int maxHp = 0;
    protected static int sp = 0;
    private static int maxSp = 0;
    protected static String role = "";
    private static int money = 0;
    private static long exp = 0;
    private static long next = 100 * (long) Math.pow(2,level);
    private static String type = "";
    private static int con = 0;
    private static int intell = 0;
    private static ArrayList<Item> inventory = new ArrayList<>();



    public Member(String n, String t)
    {
        level = 1;
        role = "Leader";
        name = n;
        assignStats(t);
        type = t;
    }

    public Member(String m)
    {
        String[] mem = m.split("\\s+");
        name = mem[0].replace("_", " ");
        level = Integer.parseInt(mem[1]);
        hp = Integer.parseInt(mem[2]);
        maxHp = Integer.parseInt(mem[3]);
        sp = Integer.parseInt(mem[4]);
        maxSp = Integer.parseInt(mem[5]);
        role = mem[6];
        money = Integer.parseInt(mem[7]);
        exp = Integer.parseInt(mem[8]);
        next = Integer.parseInt(mem[9]);
        type = mem[10];
        con = Integer.parseInt(mem[11]);
        intell = Integer.parseInt(mem[12]);
        if(level == 1)
        {
            assignStats(type);
        }

        for(int i = 13; i < mem.length-1; i+= 2)
        {
            Item item = new Item(mem[i], Integer.parseInt(mem[14]));
            inventory.add(item);
        }
    }

    public long getNext()
    {
        return next;
    }

    public void creatingParty()
    {
        if(role != "Leader")
        {
            role = "Leader";
        }
    }

    public void joiningParty()
    {
        if(role != "Party")
        {
            role = "Party";
        }
    }

    public int getHpPercent()
    {
        return hp/maxHp * 100;
    }

    public int getSpPercent()
    {
        return sp/maxSp * 100;
    }

    public void addExp(long e)
    {
        exp += e;
        while(exp >= next && level < 99)
        {
            this.levelUp();
            next = (long) (100 * Math.pow(2, level+1));
        }
    }

    private void assignStats(String t)
    {
        if(t.equals("Physical"))
        {
            con = 150;
            intell = 90;
            hp = 10 + (int)(.2*con);
            maxHp = hp;
            sp = 15 + (int)(.1*con);
            maxSp = sp;
        }

        else if(t.equals("Mental"))
        {
            con = 90;
            intell = 150;
            hp = 10 + (int)(.1*con);
            maxHp = hp;
            sp = 15 + (int)(.2*con);
            maxSp = sp;
        }

        else
        {
            con = 140;
            intell = 150;
            hp = 10 + (int)(.2*con);
            maxHp = hp;
            sp = 15 + (int)(.2*con);
            maxSp = sp;
        }
    }

    protected void restoreHp(int h)
    {
        if(hp + h > maxHp)
        {
            hp = maxHp;
            return;
        }

        hp += h;
    }

    protected void restoreSp(int s)
    {
        if(sp + s > maxSp)
        {
            sp = maxSp;
            return;
        }

        sp += s;
    }

    protected void levelUp()
    {
        level += 1;
        int oldMaxHp = maxHp;
        int oldMaxSp = maxSp;
        if(type.equals("Physical"))
        {
            con += 5;
            intell += 2;
            maxHp = 10 + (int)(.2*con);
            hp += (maxHp-oldMaxHp);
            maxSp = (int)(.1*con);
            sp += (maxSp-oldMaxSp);
        }

        else if(type.equals("Mental"))
        {
            con += 2;
            intell += 5;
            hp = 10 + (int)(.1*con);
            maxHp = hp;
            sp = 15 + (int)(.2*con);
            maxSp = sp;
        }

        else
        {
            con += 4;
            intell += 5;
            hp = 10 + (int)(.2*con);
            maxHp = hp;
            sp = 15 + (int)(.2*con);
            maxSp = sp;
        }
    }

    public static String toString(Member m)
    {
        String s =  "";
        s += m.name.replace(" ", "_") + " " + level + " " + hp + " " + maxHp + " " + sp + " " + maxSp + " " + role + " " + money + " " + exp + " " + next + " " + type + " " + con + " " + intell;
        for (Item i: inventory)
        {
            s+= i.name + " " + i.getAmount() + " ";
        }
        return s;
    }

    protected void restoreInventory(ArrayList<Item> old)
    {
        inventory = old;
    }
}
