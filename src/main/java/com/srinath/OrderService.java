package com.srinath;

import com.srinath.mapping.OrderProducts;
import com.srinath.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by sthota on 3/26/17.
 */
public class OrderService {

    public List<OrderProducts> getOrdersAboveOrderNumber(long orderNumber) {

        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session hibernateSession = sessionFactory.openSession();
        hibernateSession.beginTransaction();

        try {
            Criteria criteria = hibernateSession.createCriteria(OrderProducts.class);
            criteria.add(Restrictions.ge("order.orderId", 0L));
            List<OrderProducts> results = criteria.list();
            return results;


        } finally {
            // hibernateUtil.closeSessionFactory();
            hibernateSession.close();
        }

    }
}
