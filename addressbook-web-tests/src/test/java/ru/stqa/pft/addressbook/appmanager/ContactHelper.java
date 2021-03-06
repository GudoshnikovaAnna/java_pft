package ru.stqa.pft.addressbook.appmanager;

import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToContactPage() {
        click(By.linkText("home page"));
    }

    public void submitContact() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void fillContactPage(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getContactFirstName());
        type(By.name("lastname"), contactData.getContactLastName());
        type(By.name("company"), contactData.getContactCompany());
        type(By.name("address"), contactData.getContactAddress());
        type(By.name("email"), contactData.getContactEmail());
        type(By.name("home"), contactData.getContactHomePhone());
        type(By.name("work"), contactData.getContactWorkPhone());
        type(By.name("mobile"), contactData.getContactMobilePhone());
        attach(By.name("photo"), contactData.getPhoto());

        if (creation) {
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void acceptDeletion() {
        wd.switchTo().alert().accept();
    }

    public void initContactModificationById(int index) {
        click(By.xpath((String.format("//a[@href='edit.php?id=%s']", index))));
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void createContact(ContactData contact) {
        initContactAddition();
        fillContactPage(contact, true);
        submitContact();
        returnToContactPage();
    }

    public void initContactAddition() {
        click(By.linkText("add new"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("entry"));
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            String lastName = element.findElements(By.tagName("td")).get(1).getText();
            String firstName = element.findElements(By.tagName("td")).get(2).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName));
        }
        return contacts;
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            String lastName = element.findElements(By.tagName("td")).get(1).getText();
            String firstName = element.findElements(By.tagName("td")).get(2).getText();
            String allPhones = element.findElements(By.tagName("td")).get(5).getText();
            String allEmails = element.findElements(By.tagName("td")).get(4).getText();
            String address = element.findElements(By.tagName("td")).get(3).getText();

            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contacts.add(new ContactData()
                    .withId(id)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withAddress(address)
                    .withAllPhones(allPhones)
                    .withAllEmails(allEmails));
        }
        return contacts;
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
        acceptDeletion();
    }

    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        initContactModificationById(contact.getId());
        fillContactPage(contact, false);
        submitContactModification();
        returnToContactPage();
    }

    public void addInGroup(ContactData contact, GroupData group) {
        selectContactById(contact.getId());
        selectGroupByIdOnMainPage(group.getId());
        submitAddContactToGroup();
    }

    public void addContactInGroup(GroupData addedGroup, ContactData newContact) {
        addInGroup(newContact, addedGroup);
        checkAdditionCompleted();
    }



    public void checkAdditionCompleted() {
        Assert.assertTrue(wd.findElement(By.xpath("//div[@id='content']/div[@class='msgbox']")).getText().contains("Users added"));
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData()
                .withId(contact.getId())
                .withFirstName(firstName)
                .withLastName(lastName)
                .withAddress(address)
                .withEmail(email)
                .withEmail2(email2)
                .withEmail3(email3)
                .withHomePhone(home)
                .withMobilePhone(mobile)
                .withWorkPhone(work);
    }

    private void selectGroupByIdOnMainPage(int id) {
        wd.findElement(By.xpath("//select[@name='to_group']/option[@value='" + id + "']")).click();
    }

    private void submitAddContactToGroup() {
        wd.findElement(By.xpath("//input[@name='add']")).click();
    }

    private void selectGroupOnMainPage(GroupData group) {
        wd.findElement(By.xpath("//select[@name='group']/option[@value='" + group.getId() + "']")).click();
    }
    private void checkContactVisibleInGroup(ContactData contact) {
        wd.findElement(By.xpath("//input[@id='" + contact.getId() +"']")).isDisplayed();
    }
    public void checkContactInGroup (ContactData contact, GroupData group) {
        selectGroupOnMainPage(group);
        checkContactVisibleInGroup(contact);
    }

    public void selectGroupView(GroupData group) {
        wd.findElement(By.xpath("//select[@name='group']/option[@value='"+group.getId() +"']")).click();
    }

    public void deleteContactFromGroup(ContactData contact, GroupData deleteGroup) {
        wd.findElement(By.name("remove")).click();
    }

    public void checkDeletionCompleted() {
        Assert.assertTrue(wd.findElement(By.xpath("//div[@id='content']/div[@class='msgbox']")).getText().contains("Users removed."));
    }

    public void checkContactNotInGroup (ContactData contact, GroupData group) {
        selectGroupOnMainPage(group);
        Assert.assertTrue(checkContactNotVisibleInGroup(contact));
    }

    private boolean checkContactNotVisibleInGroup(ContactData contact) {
        return wd.findElements(By.xpath("//input[@id='" + contact.getId() + "']")).isEmpty();
    }
}