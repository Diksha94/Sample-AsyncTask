package com.example.leanstartupbar;

import java.util.Date;

/**
 * Created by diksha on 12/6/15.
 */
public class OrderList {
    int orderId=0;
    String orderName;
    Date ordertime,finishtime;

    public OrderList(int orderId,String orderName, Date ordertime,Date finishtime)
    {
        this.orderId=orderId;
        this.orderName=orderName;
        this.ordertime=ordertime;
        this.finishtime=finishtime;
    }
}
