package com.vanhackathon.imagefy.service.data.auth;

import java.util.ArrayList;

/**
 * Created by rodrigo on 5/22/2016.
 */
public class Wish {
    public int id;
    public ArrayList<Offer> offers;
    public User owner;
    public String tags;
    public String photo;
    public String brief;
    public String buget;
}
