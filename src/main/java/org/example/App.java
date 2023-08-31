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

//          Add new Person and new item to the database
            Person person = new Person("Test person", 30);
            Item newItem = new Item("Item2 from Hibernate", person);
//          for hibernate cash
            person.setItems(new ArrayList<>(Collections.singletonList(newItem)));
//          makes SQL request
            session.save(person);
            session.save(newItem);

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
