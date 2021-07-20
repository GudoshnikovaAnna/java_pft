package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreationTests() throws Exception {
        gotoContactPage();
        fillContactPage(new ContactData("Steve", "Jobes", "Apple", "California", "steve.jobes@apple.com"));
        submitContact();
        returnToContactPage();
    }

}
