package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
        app.goTo().gotoAllContactsPage();
        if (app.contact().all().size() == 0) {
            app.contact().createContact(new ContactData()
                    .withFirstName("Alla")
                    .withLastName("Pugacheva")
                    .withCompany("GalkinCompany")
                    .withAddress("villige Gryazi")
                    .withEmail("alla.pugacheva@galkin.com")
                    .withGroup("test1")
                    .withWorkPhone("676 89 98")
                    .withHomePhone("33-33-33")
                    .withMobilePhone("+7 9345674738"));
        }
    }

    @Test
    public void testContactPhones() {
        app.goTo().gotoAllContactsPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getContactHomePhone(), equalTo(cleaned(contactInfoFromEditForm.getContactHomePhone())));
        assertThat(contact.getContactMobilePhone(), equalTo(cleaned(contactInfoFromEditForm.getContactMobilePhone())));
        assertThat(contact.getContactWorkPhone(), equalTo(cleaned(contactInfoFromEditForm.getContactWorkPhone())));
    }

    public String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]","");
    }
}
