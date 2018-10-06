package com.lxisoft.redalert.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Friends entity.
 */
public class FriendsDTO implements Serializable {

    private Long id;

    private Boolean immediateFriend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isImmediateFriend() {
        return immediateFriend;
    }

    public void setImmediateFriend(Boolean immediateFriend) {
        this.immediateFriend = immediateFriend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FriendsDTO friendsDTO = (FriendsDTO) o;
        if (friendsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), friendsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FriendsDTO{" +
            "id=" + getId() +
            ", immediateFriend='" + isImmediateFriend() + "'" +
            "}";
    }
}
