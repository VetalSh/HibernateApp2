package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

//          Add item to the database
            Person person = session.get(Person.class, 2);
            Item newItem = new Item("Item from Hibernate", person);
//          for hibernate cash
            person.getItems().add(newItem);
//          makes SQL request
            session.save(newItem);

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
