package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeleteTests extends TestBase{

    @Test
    public void testContactDelete() {
        app.getNavigationHelper().gotoAllContactsPage();
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteContact();
        app.getContactHelper().acceptDeletion();
        app.getNavigationHelper().gotoAllContactsPage();
    }
}
