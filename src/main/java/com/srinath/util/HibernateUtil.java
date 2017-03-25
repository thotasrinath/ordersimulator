package com.srinath.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
    private SessionFactory sessionFactory;
    private ServiceRegistry serviceRegistry;
    private static HibernateUtil hibernateUtil;

    private HibernateUtil() {


    }

    public synchronized static HibernateUtil getInstance() {

        if (null == hibernateUtil) {
            hibernateUtil = new HibernateUtil();
            hibernateUtil.configureSessionFactory();
        }

        return hibernateUtil;

    }

    public void configureSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
            hibernateUtil = null;
        }

    }
}