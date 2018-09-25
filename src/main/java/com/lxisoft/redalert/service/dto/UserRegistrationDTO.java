package com.lxisoft.redalert.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserRegistration entity.
 */
public class UserRegistrationDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private Long phone;

    private String email;

    private String bloodGroup;

    private Instant createdTime;

    private Set<ContactDTO> emergencyContacts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Set<ContactDTO> getEmergencyContacts() {
        return emergencyContacts;
    }

    public void setEmergencyContacts(Set<ContactDTO> contacts) {
        this.emergencyContacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserRegistrationDTO userRegistrationDTO = (UserRegistrationDTO) o;
        if(userRegistrationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userRegistrationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone=" + getPhone() +
            ", email='" + getEmail() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
