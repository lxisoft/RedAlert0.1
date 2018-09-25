package com.lxisoft.redalert.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.lxisoft.redalert.domain.enumeration.TypeOfReaction;

/**
 * A Reaction.
 */
@Entity
@Table(name = "reaction")
public class Reaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypeOfReaction type;

    @ManyToOne
    private Feed feed;

    @ManyToOne
    private Action action;

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

    public Reaction userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public TypeOfReaction getType() {
        return type;
    }

    public Reaction type(TypeOfReaction type) {
        this.type = type;
        return this;
    }

    public void setType(TypeOfReaction type) {
        this.type = type;
    }

    public Feed getFeed() {
        return feed;
    }

    public Reaction feed(Feed feed) {
        this.feed = feed;
        return this;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Action getAction() {
        return action;
    }

    public Reaction action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
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
        Reaction reaction = (Reaction) o;
        if (reaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reaction{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
