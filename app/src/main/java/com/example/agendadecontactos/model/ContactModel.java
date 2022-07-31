package com.example.agendadecontactos.model;

import java.util.Comparator;

public class ContactModel {

    String  contactId;
    String  contactName;
    String  contactDirection;
    String  contactPhone;
    String  contactTelephone;
    String  contactEmail;
    String  contactRegistrationDate;
    String  contactUpdateDate;

    public ContactModel() {
    }

    public ContactModel(String contactId, String contactName, String contactDirection, String contactPhone, String contactTelephone, String contactEmail, String contactRegistrationDate, String contactUpdateDate) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactDirection = contactDirection;
        this.contactPhone = contactPhone;
        this.contactTelephone = contactTelephone;
        this.contactEmail = contactEmail;
        this.contactRegistrationDate = contactRegistrationDate;
        this.contactUpdateDate = contactUpdateDate;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactDirection() {
        return contactDirection;
    }

    public void setContactDirection(String contactDirection) {
        this.contactDirection = contactDirection;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }


    public String getContactRegistrationDate() {
        return contactRegistrationDate;
    }
    public void setContactRegistrationDate(String contactRegistrationDate) {
        this.contactRegistrationDate = contactRegistrationDate;
    }

    public String getContactUpdateDate() {
        return contactUpdateDate;
    }
    public void setContactUpdateDate(String contactUpdateDate) {
        this.contactUpdateDate = contactUpdateDate;
    }

    public static Comparator<ContactModel> ContactNameAZComparator = new Comparator<ContactModel>() {
        @Override
        public int compare(ContactModel contactModel1, ContactModel contactModel2) {
            return contactModel1.getContactName().compareTo(contactModel2.getContactName());
        }
    };
    public static Comparator<ContactModel> ContactNameZAComparator = new Comparator<ContactModel>() {
        @Override
        public int compare(ContactModel contactModel1, ContactModel contactModel2) {
            return contactModel2.getContactName().compareTo(contactModel1.getContactName());
        }
    };

/*
    public static Comparator<ContactModel> ContactIdAZComparator = new Comparator<ContactModel>() {
        @Override
        public int compare(ContactModel contactModel1, ContactModel contactModel2) {
            return contactModel1.getContactId() - contactModel2.getContactId();
        }
    };
*/

/*    @Override
    public String toString() {
        return "ContactModel{" +
                "contactName='" + contactName + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return "ContactModel{" +
                "contactId='" + contactId + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactDirection='" + contactDirection + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactTelephone='" + contactTelephone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                '}';
    }
}
