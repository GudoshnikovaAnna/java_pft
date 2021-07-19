package ru.stqa.pft.addressbook;

public class ContactData {
    private final String сontactFirstName;
    private final String contactLastName;
    private final String contactCompany;
    private final String contactAddress;
    private final String contactEmail;

    public ContactData(String сontactFirstName, String contactLastName, String contactCompany, String contactAddress, String contactEmail) {
        this.сontactFirstName = сontactFirstName;
        this.contactLastName = contactLastName;
        this.contactCompany = contactCompany;
        this.contactAddress = contactAddress;
        this.contactEmail = contactEmail;
    }

    public String getСontactFirstName() {
        return сontactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public String getContactCompany() {
        return contactCompany;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public String getContactEmail() {
        return contactEmail;
    }
}
