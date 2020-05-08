package com.company;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            String path = args[0];
            Storage storage = new Storage();
            storage.jaxbXmlFileToObject(path);
            if (storage != null) {


                ArrayList<Box> boxes = storage.getAllBoxes();
                if (boxes != null) {
                    SessionFactory sessionFactory = new org.hibernate.cfg.Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
                    Session session = sessionFactory.openSession();
                    session.beginTransaction();
                    for (int i = 0; i < boxes.size(); i++) {
                        if (boxes.get(i) != null) {
                            session.saveOrUpdate(boxes.get(i));
                        }
                    }
                    if (storage.getItems() != null) {
                        for (int j = 0; j < storage.getItems().size(); j++) {
                            if (storage.getItems().get(j) != null) {
                                session.saveOrUpdate(storage.getItems().get(j));
                            }
                        }
                    }
                    session.getTransaction().commit();
                    session.close();
                    sessionFactory.close();
                }
            }
        } else {
            System.out.println("Укажите путь к файлу");
        }

    }

}
