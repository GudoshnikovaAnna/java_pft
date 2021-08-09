package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactDeleteTests extends TestBase{

    @Test
    public void testContactDelete() {
        app.goTo().groupPage();
        if (!app.group().isThereAGroup()) {
            app.group().create(new GroupData().withName("test1"));
        }
        app.goTo().gotoAllContactsPage();
        if (!app.getContactHelper().isThereAContact()) {
            app.goTo().gotoContactAddPage();
            app.getContactHelper().createContact(new ContactData("Alla", "Pugacheva", "GalkinCompany", "villige Gryazi", "alla.pugacheva@galkin.com", "test1"));
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().deleteContact();
        app.getContactHelper().acceptDeletion();
        app.goTo().gotoAllContactsPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size() - 1);
        Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2. getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
