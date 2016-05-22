package com.vanhackathon.imagefy.service.data.auth;

/**
 * Created by rodrigo on 5/22/2016.
 */
public class Offer {
    public User salesman;
    public int shopify_product_id;
    public String created_at;
    public String updated_at;

    @Override
    public String toString() {
        return "Offer{" +
                "salesman=" + salesman +
                ", shopify_product_id=" + shopify_product_id +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
