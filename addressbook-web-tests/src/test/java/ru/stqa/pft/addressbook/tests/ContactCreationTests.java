package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreationTests() throws Exception {
        app.getNavigationHelper().gotoContactAddPage();
        app.getContactHelper().createContact(new ContactData("Steve", "Jobes", "Apple", "California", "steve.jobes@apple.com", "test1"));
    }

}
