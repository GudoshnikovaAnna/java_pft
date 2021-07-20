package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver wd) {
         super(wd);
    }

    public void returnToContactPage() {
       click(By.linkText("home page"));
    }

    public void submitContact() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactPage(ContactData contactData) {
        type(By.name("firstname"),contactData.getContactFirstName());
        type(By.name("lastname"),contactData.getContactLastName());
        type(By.name("company"),contactData.getContactCompany());
        type(By.name("address"),contactData.getContactAddress());
        type(By.name("email"),contactData.getContactEmail());

    }
}
