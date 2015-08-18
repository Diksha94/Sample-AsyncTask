package com.example.leanstartupbar;

import android.text.format.Time;

import java.util.Date;

/**
 * Created by diksha on 11/6/15.
 */
public class Order {
    int orderId=0,orItemId;
    Date ordertime;

    //orItemId : order - item id

    public Order(int orderId,int orItemId, Date ordertime)
    {
        this.orderId=orderId;
        this.orItemId=orItemId;
        this.ordertime=ordertime;
    }
}
