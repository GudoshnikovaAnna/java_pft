package ru.stqa.pft.addressbook.model;

import java.io.File;

public class ContactData {
    private int id = Integer.MAX_VALUE;
    private String contactFirstName;
    private String contactLastName;
    private String contactCompany;
    private String contactAddress;
    private String contactEmail;
    private String contactEmail2;
    private String contactEmail3;
    private String group;
    private String homePhone;
    private String mobilePhone;
    private String workPhone;
    private String allPhones;
    private String allEmails;
    private File photo;

    public File getPhoto() {
        return photo;
    }

    public ContactData withPhoto(File photo) {
        this.photo = photo;
        return this;
    }

    public String getContactEmail2() {
        return contactEmail2;
    }

    public ContactData withEmail2(String contactEmail2) {
        this.contactEmail2 = contactEmail2;
        return this;
    }

    public String getContactEmail3() {
        return contactEmail3;
    }

    public ContactData withEmail3(String contactEmail3) {
        this.contactEmail3 = contactEmail3;
        return this;
    }


    public ContactData withId(int id) {
        this.id = id;
        return this;
    }

    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public void setAllPhones(String allPhones) {
        this.allPhones = allPhones;
    }

    public ContactData withAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }
    public String getAllEmails() {
        return allEmails;
    }
    public String getAddress() {
        return contactAddress;
    }

    public void setAllEmails(String allEmails) {
        this.allEmails = allEmails;
    }

    public ContactData withFirstName(String name) {
        this.contactFirstName = name;
        return this;
    }

    public ContactData withLastName(String name) {
        this.contactLastName = name;
        return this;
    }

    public ContactData withHomePhone(String home) {
        this.homePhone = home;
        return this;
    }

    public ContactData withMobilePhone(String mobile) {
        this.mobilePhone = mobile;
        return this;
    }

    public ContactData withWorkPhone(String work) {
        this.workPhone = work;
        return this;
    }

    public ContactData withCompany(String name) {
        this.contactCompany = name;
        return this;
    }

    public ContactData withAddress(String name) {
        this.contactAddress = name;
        return this;
    }

    public ContactData withEmail(String name) {
        this.contactEmail = name;
        return this;
    }

    public ContactData withGroup(String name) {
        this.group = name;
        return this;
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

    public String getContactWorkPhone() {
        return workPhone;
    }

    public String getContactHomePhone() {
        return homePhone;
    }

    public String getAllPhones() {
        return allPhones;
    }


    public String getContactMobilePhone() {
        return mobilePhone;
    }

    public String getGroup() {
        return group;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (contactFirstName != null ? !contactFirstName.equals(that.contactFirstName) : that.contactFirstName != null)
            return false;
        return contactLastName != null ? contactLastName.equals(that.contactLastName) : that.contactLastName == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (contactFirstName != null ? contactFirstName.hashCode() : 0);
        result = 31 * result + (contactLastName != null ? contactLastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", contactFirstName='" + contactFirstName + '\'' +
                ", contactLastName='" + contactLastName + '\'' +
                '}';
    }


}
