package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
        app.goTo().gotoAllContactsPage();
        if (!app.getContactHelper().isThereAContact()) {
            app.goTo().gotoContactAddPage();
            app.getContactHelper().createContact(new ContactData("Alla", "Pugacheva", "GalkinCompany", "villige Gryazi", "alla.pugacheva@galkin.com", "test1"));
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().initContactModification(before.get(before.size()-1).getId());
        ContactData contact = new ContactData(before.get(before.size()-1).getId(), "Steve", "Jobes");
        app.getContactHelper().fillContactPage(contact, false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size()-1);
        before.add(contact);

        Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2. getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
