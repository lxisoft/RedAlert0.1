package com.lxisoft.redalert.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.lxisoft.redalert.domain.enumeration.ActionType;

/**
 * A DTO for the Action entity.
 */
public class ActionDTO implements Serializable {

    private Long id;

    private String userName;

    private String takenAction;

    private ActionType type;

    private Boolean requestApproval;

    private Instant createdTime;

    private Long feedId;

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

    public String getTakenAction() {
        return takenAction;
    }

    public void setTakenAction(String takenAction) {
        this.takenAction = takenAction;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public Boolean isRequestApproval() {
        return requestApproval;
    }

    public void setRequestApproval(Boolean requestApproval) {
        this.requestApproval = requestApproval;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Long getFeedId() {
        return feedId;
    }

    public void setFeedId(Long feedId) {
        this.feedId = feedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActionDTO actionDTO = (ActionDTO) o;
        if(actionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActionDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", takenAction='" + getTakenAction() + "'" +
            ", type='" + getType() + "'" +
            ", requestApproval='" + isRequestApproval() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
