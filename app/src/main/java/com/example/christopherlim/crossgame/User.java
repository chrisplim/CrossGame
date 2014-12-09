package com.example.christopherlim.crossgame;

/**
 * Created by Sean on 12/7/2014.
 */
public class User {
    User() {
        c_id = "";
        c_name = "";
    }
    private String c_id;
    private String c_name;
    public void setId(String i) { c_id = i;}
    public void setName(String n) { c_name = n; }
    public String getId() { return c_id; }
    public String getName() { return c_name; }
}
