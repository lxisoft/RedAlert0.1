package com.lxisoft.redalert.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lxisoft.redalert.domain.enumeration.ActionType;

/**
 * A Action.
 */
@Entity
@Table(name = "action")
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "taken_action")
    private String takenAction;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private ActionType type;

    @Column(name = "request_approval")
    private Boolean requestApproval;

    @Column(name = "created_time")
    private Instant createdTime;

    @OneToMany(mappedBy = "action")
    private Set<Reaction> scopeOfActions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("actions")
    private UserFeed user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public Action userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTakenAction() {
        return takenAction;
    }

    public Action takenAction(String takenAction) {
        this.takenAction = takenAction;
        return this;
    }

    public void setTakenAction(String takenAction) {
        this.takenAction = takenAction;
    }

    public ActionType getType() {
        return type;
    }

    public Action type(ActionType type) {
        this.type = type;
        return this;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public Boolean isRequestApproval() {
        return requestApproval;
    }

    public Action requestApproval(Boolean requestApproval) {
        this.requestApproval = requestApproval;
        return this;
    }

    public void setRequestApproval(Boolean requestApproval) {
        this.requestApproval = requestApproval;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public Action createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Set<Reaction> getScopeOfActions() {
        return scopeOfActions;
    }

    public Action scopeOfActions(Set<Reaction> reactions) {
        this.scopeOfActions = reactions;
        return this;
    }

    public Action addScopeOfActions(Reaction reaction) {
        this.scopeOfActions.add(reaction);
        reaction.setAction(this);
        return this;
    }

    public Action removeScopeOfActions(Reaction reaction) {
        this.scopeOfActions.remove(reaction);
        reaction.setAction(null);
        return this;
    }

    public void setScopeOfActions(Set<Reaction> reactions) {
        this.scopeOfActions = reactions;
    }

    public UserFeed getUser() {
        return user;
    }

    public Action user(UserFeed userFeed) {
        this.user = userFeed;
        return this;
    }

    public void setUser(UserFeed userFeed) {
        this.user = userFeed;
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
        Action action = (Action) o;
        if (action.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Action{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", takenAction='" + getTakenAction() + "'" +
            ", type='" + getType() + "'" +
            ", requestApproval='" + isRequestApproval() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
