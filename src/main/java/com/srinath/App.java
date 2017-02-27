package com.srinath;

import au.com.bytecode.opencsv.CSVReader;
import com.srinath.mapping.OrderProducts;
import com.srinath.mapping.Orders;
import com.srinath.mapping.Products;
import com.srinath.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            App app = new App();
            app.insertProducts();
            app.generateOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void insertProducts() throws FileNotFoundException, IOException {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/products.csv"));

        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            Products products = new Products();
            products.setProductName(nextLine[0]);
            products.setSku(nextLine[1]);
            products.setPrice(Float.parseFloat(nextLine[2]));
            session.save(products);
        }

        session.getTransaction().commit();
        session.close();
    }

    private void generateOrders() throws InterruptedException {

        for (int ord = 0; ord < 1000; ord++) {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Query query = session.createQuery("FROM Products");
            List<Products> results = query.list();


            Orders order = new Orders();
            order.setOrderData(new Date());
            session.save(order);

            for (int i = 0; i < 10; i++) {

                int randomProd = randomWithRange(1, results.size());

                OrderProducts orderProducts = new OrderProducts();
                orderProducts.setOrder(order);
                orderProducts.setProduct(results.get(randomProd));
                session.save(orderProducts);

            }


            session.getTransaction().commit();
            session.close();

            Thread.sleep(5000);
        }


    }

    private int randomWithRange(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int) (Math.random() * range) + (min <= max ? min : max);
    }
}