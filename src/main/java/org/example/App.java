package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class)
            .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

//          Remove person2 items from the database
            Person person = session.get(Person.class, 2);
            List<Item> items = person.getItems();
            // makes a SQL query
            for (Item item: items)
                session.remove(item);
            // does not make SQL query, but it is necessary for correct hibernate cash state
            person.getItems().clear();

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
