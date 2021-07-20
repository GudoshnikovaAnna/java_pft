package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoAllContactsPage();
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactPage(new ContactData("Steve", "Jobes", "Apple", "California 1", "steve.jobes@apple.com"));
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToContactPage();;
    }
}
