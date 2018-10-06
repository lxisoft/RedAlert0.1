package com.lxisoft.redalert.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Friends.
 */
@Entity
@Table(name = "friends")
public class Friends implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "immediate_friend")
    private Boolean immediateFriend;

    @ManyToMany(mappedBy = "friends")
    @JsonIgnore
    private Set<UserRegistration> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isImmediateFriend() {
        return immediateFriend;
    }

    public Friends immediateFriend(Boolean immediateFriend) {
        this.immediateFriend = immediateFriend;
        return this;
    }

    public void setImmediateFriend(Boolean immediateFriend) {
        this.immediateFriend = immediateFriend;
    }

    public Set<UserRegistration> getUsers() {
        return users;
    }

    public Friends users(Set<UserRegistration> userRegistrations) {
        this.users = userRegistrations;
        return this;
    }

    public Friends addUsers(UserRegistration userRegistration) {
        this.users.add(userRegistration);
        userRegistration.getFriends().add(this);
        return this;
    }

    public Friends removeUsers(UserRegistration userRegistration) {
        this.users.remove(userRegistration);
        userRegistration.getFriends().remove(this);
        return this;
    }

    public void setUsers(Set<UserRegistration> userRegistrations) {
        this.users = userRegistrations;
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
        Friends friends = (Friends) o;
        if (friends.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), friends.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Friends{" +
            "id=" + getId() +
            ", immediateFriend='" + isImmediateFriend() + "'" +
            "}";
    }
}
