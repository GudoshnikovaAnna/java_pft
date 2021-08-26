package ru.stqa.pft.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;


import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AddContactInGroupTests extends TestBase {

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

    /**Тест проверяет добавление контакта в группу,
     * при этом игнорируется ситуация:
     * если выбранный контакт состоит во всех группах,
     * мы не переходим к другому контакту, а создаем новый
     * и добавляем в выбранную группу */
    @Test
    public void addContactInGroup() {
        app.goTo().gotoAllContactsPage();
        //Выбрали контакт
        ContactData contact = app.db().contacts().iterator().next();
        //Получили список контактов, ассоциированных с группой
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
        session.getTransaction().commit();
        session.close();
        //выбрали группу. куда будем добавлять контакт
        GroupData addedGroup = app.db().groups().iterator().next();
        //Если контакт содержится во всех группах
        if (contact.getGroups().equals(app.db().groups())) {
            //создаем новый контакт и добавляем в выбранную группу
            String newContactName = String.valueOf(new Random(Integer.MAX_VALUE));
            ContactData newContact = new ContactData()
                    .withFirstName(newContactName)
                    .withLastName("test1")
                    .withAddress("test1")
                    .withEmail("test1")
                    .withPhoto(photo);
            app.goTo().gotoAllContactsPage();
            app.contact().createContact(newContact);
            //узнаем индекс, с которым контакт добавился в базу
            int index = findIndexOfContactByName(newContactName);
            newContact.setId(index);
            app.goTo().gotoAllContactsPage();
            app.contact().addContactInGroup(addedGroup, newContact);
            app.goTo().gotoAllContactsPage();
        }
        //если вообще контакт состоит не во всех группах, тогда надо выбрать группу, в которой он не состоит
        else {
            ContactData finalContact = contact;
            GroupData newGroup = app.db().groups().stream().filter((g) -> !finalContact.getGroups().contains(g)).collect(Collectors.toList()).iterator().next();
            app.contact().addContactInGroup(newGroup, contact);
            app.goTo().gotoAllContactsPage();
        }
        app.contact().checkContactInGroup(contact, addedGroup);
    }



    public int findIndexOfContactByName(String nameOfContact) {
        Contacts contacts = app.db().contacts();
        for (ContactData contact : contacts) {
            if (contact.getContactFirstName().equals(nameOfContact)) return contact.getId();
        }
        return -1;
    }


}
