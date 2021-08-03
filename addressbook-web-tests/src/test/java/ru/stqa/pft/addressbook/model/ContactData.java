package ru.stqa.pft.addressbook.model;

public class ContactData {
    private int id;
    private final String contactFirstName;
    private final String contactLastName;
    private String contactCompany;
    private String contactAddress;
    private String contactEmail;
    private String group;

    public ContactData(String contactFirstName, String contactLastName, String contactCompany, String contactAddress, String contactEmail, String group) {
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactCompany = contactCompany;
        this.contactAddress = contactAddress;
        this.contactEmail = contactEmail;
        this.group = group;
        this.id = Integer.MAX_VALUE;
    }

    public ContactData(int id, String contactFirstName, String contactLastName) {
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.id = id;
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
