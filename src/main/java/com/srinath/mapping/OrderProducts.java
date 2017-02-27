package com.srinath.mapping;

import java.io.Serializable;

/**
 * Created by thota on 2/27/17.
 * <p>
 * <p>
 * CREATE TABLE order_products (
 * order_product_id serial PRIMARY KEY,
 * order_id int,
 * product_id int,
 * FOREIGN KEY (order_id) REFERENCES orders (order_id),
 * FOREIGN KEY (product_id) REFERENCES products (product_id)
 * <p>
 * );
 */
public class OrderProducts implements Serializable {

    private long orderProductId;
    private Orders order;
    private Products product;

    public long getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(long orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }




}
