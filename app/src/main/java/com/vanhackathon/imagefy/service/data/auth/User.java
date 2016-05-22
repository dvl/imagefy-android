package com.vanhackathon.imagefy.service.data.auth;

/**
 * Created by rodrigo on 5/22/2016.
 */
public class User {
    public String first_name;
    public String last_name;
    public String email;
    public Profile profile;

    @Override
    public String toString() {
        return "User{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", profile=" + profile +
                '}';
    }
}
