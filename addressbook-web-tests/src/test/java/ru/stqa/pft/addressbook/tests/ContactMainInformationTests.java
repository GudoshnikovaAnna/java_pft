package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactMainInformationTests extends TestBase {

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
                    .withEmail3("maxim.galkin@galkin.com")
                    .withGroup("test1")
                    .withWorkPhone("676 89 98")
                    .withHomePhone("33-33-33")
                    .withMobilePhone("+7 9345674738"));
        }
    }

    @Test
    public void testContactMainInfo() {
        app.goTo().gotoAllContactsPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.getAddress()  + "\n", equalTo(contactInfoFromEditForm.getAddress()));

    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getContactHomePhone(), contact.getContactMobilePhone(), contact.getContactWorkPhone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactMainInformationTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getContactEmail(), contact.getContactEmail2(), contact.getContactEmail3())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactMainInformationTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]","");
    }
}
