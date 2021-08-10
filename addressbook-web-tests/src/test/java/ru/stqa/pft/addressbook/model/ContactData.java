package ru.stqa.pft.addressbook.model;

public class ContactData {
    private int id = Integer.MAX_VALUE;
    private String contactFirstName;
    private String contactLastName;
    private String contactCompany;
    private String contactAddress;
    private String contactEmail;
    private String group;
    private String homePhone;
    private String mobilePhone;
    private String workPhone;

    public ContactData withId(int id) {
        this.id = id;
        return this;
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
    public String getContactMobilePhone() {
        return mobilePhone;
    }

    public String getGroup() {
        return group;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() { return id; }

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
