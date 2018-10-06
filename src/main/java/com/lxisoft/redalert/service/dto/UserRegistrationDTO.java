package com.lxisoft.redalert.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the UserRegistration entity.
 */
public class UserRegistrationDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private Long phone;

    private String email;

    private String password;

    private String bloodGroup;

    @Lob
    private byte[] userImage;
    private String userImageContentType;

    private Long points;

    private Instant dateOfBith;

    private Instant createdTime;

    private Set<FriendsDTO> friends = new HashSet<>();

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public String getUserImageContentType() {
        return userImageContentType;
    }

    public void setUserImageContentType(String userImageContentType) {
        this.userImageContentType = userImageContentType;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Instant getDateOfBith() {
        return dateOfBith;
    }

    public void setDateOfBith(Instant dateOfBith) {
        this.dateOfBith = dateOfBith;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Set<FriendsDTO> getFriends() {
        return friends;
    }

    public void setFriends(Set<FriendsDTO> friends) {
        this.friends = friends;
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
        if (userRegistrationDTO.getId() == null || getId() == null) {
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
            ", password='" + getPassword() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", userImage='" + getUserImage() + "'" +
            ", points=" + getPoints() +
            ", dateOfBith='" + getDateOfBith() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
