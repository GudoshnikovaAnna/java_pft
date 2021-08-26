package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
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
                        .withEmail("alla.pugacheva@galkin.com"));
                        //.withGroup("test1"));
            }
        }
    }

    @Test
    public void testContactModification() {
        app.goTo().gotoAllContactsPage();
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        File photo = new File(String.format("src/test/resources/%s", app.properties.getProperty("web.contactPhoto")));
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId())
                .withFirstName("Steve")
                .withLastName("Jobes")
                .withAddress(modifiedContact.getAddress())
                .withEmail(modifiedContact.getContactEmail())
                .withCompany(modifiedContact.getContactCompany())
                .withPhoto(photo);
        app.contact().modify(contact);
        assertEquals(app.group().count(), before.size());
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.withoutAdded(modifiedContact).withAdded(contact)));
    }
}
