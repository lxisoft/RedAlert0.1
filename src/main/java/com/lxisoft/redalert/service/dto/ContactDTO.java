package com.lxisoft.redalert.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Contact entity.
 */
public class ContactDTO implements Serializable {

    private Long id;

    private String friendName;

    private String contactMail;

    private Long contactNumber;

    private Boolean immidiateFriend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Boolean isImmidiateFriend() {
        return immidiateFriend;
    }

    public void setImmidiateFriend(Boolean immidiateFriend) {
        this.immidiateFriend = immidiateFriend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContactDTO contactDTO = (ContactDTO) o;
        if(contactDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
            "id=" + getId() +
            ", friendName='" + getFriendName() + "'" +
            ", contactMail='" + getContactMail() + "'" +
            ", contactNumber=" + getContactNumber() +
            ", immidiateFriend='" + isImmidiateFriend() + "'" +
            "}";
    }
}
