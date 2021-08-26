package ru.stqa.pft.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteContactFromGroupTests extends TestBase {

    private SessionFactory sessionFactory;
    public File photo;

    @BeforeMethod
    public void ensurePreconditions() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
        photo = new File(String.format("src/test/resources/%s", app.properties.getProperty("web.contactPhoto")));
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
        if (app.db().contacts().size() == 0) {
            app.goTo().gotoAllContactsPage();
            app.contact().createContact(new ContactData()
                    .withFirstName("Alla")
                    .withLastName("Pugacheva")
                    .withCompany("GalkinCompany")
                    .withAddress("villige Gryazi")
                    .withEmail("alla.pugacheva@galkin.com")
                    .withPhoto(photo));
        }
    }

    @Test
    public void deleteContactFromGroup() {
        app.goTo().gotoAllContactsPage();
        //Выбрали контакт
        ContactData contact = app.db().contacts().iterator().next();
        //Получили список контактов, ассоциированных с группой
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
        session.getTransaction().commit();
        session.close();
        //Выбираем группу, в которой состоит контакт
        if (contact.getGroups().size() != 0) {
            ContactData finalContact = contact;
            GroupData deleteGroup = app.db().groups().stream().filter((g) -> finalContact.getGroups().contains(g)).collect(Collectors.toList()).iterator().next();
            deleteContact(contact, deleteGroup);
        }
        else {
            //добавляем контакт в группу, а затем удаляем из группы
            app.goTo().gotoAllContactsPage();
            GroupData deleteGroup = app.db().groups().iterator().next();
            app.contact().addContactInGroup(deleteGroup, contact);
            deleteContact(contact,deleteGroup);
        }

    }

    private void deleteContact(ContactData contact, GroupData deleteGroup) {
        app.goTo().gotoAllContactsPage();
        app.contact().selectGroupView(deleteGroup);
        app.contact().selectContactById(contact.getId());
        app.contact().deleteContactFromGroup(contact, deleteGroup);
        app.contact().checkDeletionCompleted();
        app.goTo().gotoAllContactsPage();
        app.contact().checkContactNotInGroup(contact, deleteGroup);
    }

}
