package com.tdlrpg.osalem.tdlrpg;

public class Member {
    String name = "";
    int level = 0;
    int hp = 0;
    int sp = 0;
    String role = "";

    public Member(String n)
    {
        level = 1;
        hp = 60;
        sp = 80;
        role = "Leader";
        name = n;
    }
}
