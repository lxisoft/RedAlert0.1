package com.lxisoft.redalert.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.lxisoft.redalert.domain.enumeration.Alert;

/**
 * A DTO for the Feed entity.
 */
public class FeedDTO implements Serializable {

    private Long id;

    private String userName;

    private String matter;

    private Alert type;

    private String comments;

    private Instant createdTime;

    private Set<ContactDTO> helpedFriends = new HashSet<>();

    private Long userRegistrationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public Alert getType() {
        return type;
    }

    public void setType(Alert type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Set<ContactDTO> getHelpedFriends() {
        return helpedFriends;
    }

    public void setHelpedFriends(Set<ContactDTO> contacts) {
        this.helpedFriends = contacts;
    }

    public Long getUserRegistrationId() {
        return userRegistrationId;
    }

    public void setUserRegistrationId(Long userRegistrationId) {
        this.userRegistrationId = userRegistrationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeedDTO feedDTO = (FeedDTO) o;
        if(feedDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feedDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeedDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", matter='" + getMatter() + "'" +
            ", type='" + getType() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
