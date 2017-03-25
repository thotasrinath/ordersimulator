package com.srinath;

import au.com.bytecode.opencsv.CSVReader;
import com.srinath.mapping.OrderProducts;
import com.srinath.mapping.Orders;
import com.srinath.mapping.Products;
import com.srinath.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class SeedCreator implements Runnable {

    //Time between orders in seconds
    public static int TIME_BETWEEN_ORDERS = 60;

    //Total number of orders to take
    public static int NUMBER_OF_ORDERS = 1000;

    //Total number of products per ordder
    public static int NUMBER_OF_PRODUCTS_PER_ORDER = 10;


    public boolean isStopped = false;

    public void stopSimultator() {
        isStopped = true;
    }

    public void run() {


        try {

            insertProducts();
            generateOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public synchronized void insertProducts() throws FileNotFoundException, IOException {

        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //TODO take path from processor

        CSVReader csvReader = new CSVReader(new FileReader("/home/sthota/Software/wilpproject/ordersimulator/src/main/resources/products.csv"));

        try {
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                Products products = new Products();
                products.setProductName(nextLine[0]);
                products.setSku(nextLine[1]);
                products.setPrice(Float.parseFloat(nextLine[2]));
                session.save(products);
            }

            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }

    public synchronized void generateOrders() throws InterruptedException {

        for (int ord = 0; ord < NUMBER_OF_ORDERS; ord++) {
            SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            try {
                Criteria criteria = session.createCriteria(Products.class);
                criteria.add(Restrictions.sqlRestriction("1=1 order by random()"));
                criteria.setMaxResults(NUMBER_OF_PRODUCTS_PER_ORDER);
                List<Products> results = criteria.list();

                Orders order = new Orders();
                order.setOrderData(new Date());
                session.save(order);

                for (int i = 0; i < results.size(); i++) {

                    OrderProducts orderProducts = new OrderProducts();
                    orderProducts.setOrder(order);
                    orderProducts.setProduct(results.get(i));
                    session.save(orderProducts);

                }

                session.getTransaction().commit();
            } finally {
                session.close();
            }

            if (isStopped) {
                break;
            }


            this.wait(TIME_BETWEEN_ORDERS * 1000);
        }


    }

}