package com.lxisoft.redalert.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.lxisoft.redalert.domain.enumeration.Alert;

/**
 * A DTO for the UserFeed entity.
 */
public class UserFeedDTO implements Serializable {

    private Long id;

    private String userName;

    private String message;

    private Alert currentType;

    private Boolean isValid;

    private String latitude;

    private String longitude;

    private Integer score;

    private Instant createdTime;

    private Set<FriendsDTO> helpedFriends = new HashSet<>();

    private String userId;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Alert getCurrentType() {
        return currentType;
    }

    public void setCurrentType(Alert currentType) {
        this.currentType = currentType;
    }

    public Boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Set<FriendsDTO> getHelpedFriends() {
        return helpedFriends;
    }

    public void setHelpedFriends(Set<FriendsDTO> friends) {
        this.helpedFriends = friends;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userRegistrationId) {
        this.userId = userRegistrationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserFeedDTO userFeedDTO = (UserFeedDTO) o;
        if (userFeedDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userFeedDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserFeedDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", message='" + getMessage() + "'" +
            ", currentType='" + getCurrentType() + "'" +
            ", isValid='" + isIsValid() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", score=" + getScore() +
            ", createdTime='" + getCreatedTime() + "'" +
            ", user='" + getUserId() + "'" +
            "}";
    }
}
