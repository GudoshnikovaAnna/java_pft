package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeleteTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
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
                    .withGroup("test1"));
        }
    }

    @Test
    public void testContactDelete() {
        app.goTo().gotoAllContactsPage();
        Contacts before = app.db().contacts();
        ContactData deletedGroup = before.iterator().next();
        app.contact().delete(deletedGroup);
        app.goTo().gotoAllContactsPage();
        Contacts after = app.db().contacts();
        assertEquals(after.size(), before.size() - 1);
        assertThat(after, equalTo(before.withoutAdded(deletedGroup)));
    }
}
