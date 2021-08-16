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

public class ContactCreationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testContactCreationTests()  {
        app.goTo().gotoAllContactsPage();
        Contacts before = app.contact().all();
        File photo = new File("src/test/resources/testPic.png");
        ContactData contact = new ContactData()
                .withFirstName("Alla")
                .withLastName("Pugacheva")
                .withCompany("GalkinCompany")
                .withAddress("villige Gryazi")
                .withEmail("alla.pugacheva@galkin.com")
                .withGroup("test1")
                .withPhoto(photo);
        app.contact().createContact(contact);
        app.goTo().gotoAllContactsPage();
        Contacts after = app.contact().all();
        assertEquals(after.size(), before.size() + 1);
        assertThat(after, equalTo(
                before.withAdded(
                        contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

}
