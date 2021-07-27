package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String contactFirstName;
    private final String contactLastName;
    private final String contactCompany;
    private final String contactAddress;
    private final String contactEmail;
    private String group;

    public ContactData(String contactFirstName, String contactLastName, String contactCompany, String contactAddress, String contactEmail, String group) {
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactCompany = contactCompany;
        this.contactAddress = contactAddress;
        this.contactEmail = contactEmail;
        this.group = group;
    }

    public String getContactFirstName() {
        return contactFirstName;
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

    public String getGroup() {
        return group;
    }
}
