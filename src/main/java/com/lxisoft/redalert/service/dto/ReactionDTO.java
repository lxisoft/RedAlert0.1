package com.lxisoft.redalert.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.lxisoft.redalert.domain.enumeration.TypeOfReaction;

/**
 * A DTO for the Reaction entity.
 */
public class ReactionDTO implements Serializable {

    private Long id;

    private String userName;

    private TypeOfReaction type;

    private Long feedId;

    private Long actionId;

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

    public TypeOfReaction getType() {
        return type;
    }

    public void setType(TypeOfReaction type) {
        this.type = type;
    }

    public Long getFeedId() {
        return feedId;
    }

    public void setFeedId(Long userFeedId) {
        this.feedId = userFeedId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReactionDTO reactionDTO = (ReactionDTO) o;
        if (reactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReactionDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", type='" + getType() + "'" +
            ", feed=" + getFeedId() +
            ", action=" + getActionId() +
            "}";
    }
}
