package com.lxisoft.redalert.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "friend_name")
    private String friendName;

    @Column(name = "contact_mail")
    private String contactMail;

    @Column(name = "contact_number")
    private Long contactNumber;

    @Column(name = "immidiate_friend")
    private Boolean immidiateFriend;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFriendName() {
        return friendName;
    }

    public Contact friendName(String friendName) {
        this.friendName = friendName;
        return this;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getContactMail() {
        return contactMail;
    }

    public Contact contactMail(String contactMail) {
        this.contactMail = contactMail;
        return this;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public Contact contactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Boolean isImmidiateFriend() {
        return immidiateFriend;
    }

    public Contact immidiateFriend(Boolean immidiateFriend) {
        this.immidiateFriend = immidiateFriend;
        return this;
    }

    public void setImmidiateFriend(Boolean immidiateFriend) {
        this.immidiateFriend = immidiateFriend;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", friendName='" + getFriendName() + "'" +
            ", contactMail='" + getContactMail() + "'" +
            ", contactNumber=" + getContactNumber() +
            ", immidiateFriend='" + isImmidiateFriend() + "'" +
            "}";
    }
}
