package ru.stqa.pft.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.mantis.model.User;
import ru.stqa.pft.mantis.model.Users;

import java.util.List;

public class DbHelper {

    private final SessionFactory sessionFactory;
    private ApplicationManager app;

    public DbHelper(ApplicationManager app) {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        this.app = app;
    }



    public Users users() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> result = session.createQuery( "from User" ).list();
        session.getTransaction().commit();
        session.close();
        return new Users(result);
    }



}
