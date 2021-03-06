package com.srinath.mapping;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by thota on 2/27/17.
 *
 *
 *
 CREATE TABLE orders (
 order_id serial PRIMARY KEY,
 order_date date
 );

 */
public class Orders implements Serializable {


    private long orderId;
    private Date orderData;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }




    public Date getOrderData() {
        return orderData;
    }

    public void setOrderData(Date orderData) {
        this.orderData = orderData;
    }


}
