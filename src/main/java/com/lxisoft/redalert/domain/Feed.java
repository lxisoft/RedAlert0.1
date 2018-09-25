package com.lxisoft.redalert.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lxisoft.redalert.domain.enumeration.Alert;

/**
 * A Feed.
 */
@Entity
@Table(name = "feed")
public class Feed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "matter")
    private String matter;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private Alert type;

    @Column(name = "comments")
    private String comments;

    @Column(name = "created_time")
    private Instant createdTime;

    @OneToMany(mappedBy = "feed")
    @JsonIgnore
    private Set<Action> actions = new HashSet<>();

    @OneToMany(mappedBy = "feed")
    @JsonIgnore
    private Set<Reaction> scopeOfFeeds = new HashSet<>();

    @OneToMany(mappedBy = "feed")
    @JsonIgnore
    private Set<File> files = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "feed_helped_friends",
               joinColumns = @JoinColumn(name="feeds_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="helped_friends_id", referencedColumnName="id"))
    private Set<Contact> helpedFriends = new HashSet<>();

    @ManyToOne
    private UserRegistration userRegistration;

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

    public Feed userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMatter() {
        return matter;
    }

    public Feed matter(String matter) {
        this.matter = matter;
        return this;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public Alert getType() {
        return type;
    }

    public Feed type(Alert type) {
        this.type = type;
        return this;
    }

    public void setType(Alert type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public Feed comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public Feed createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public Feed actions(Set<Action> actions) {
        this.actions = actions;
        return this;
    }

    public Feed addActions(Action action) {
        this.actions.add(action);
        action.setFeed(this);
        return this;
    }

    public Feed removeActions(Action action) {
        this.actions.remove(action);
        action.setFeed(null);
        return this;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public Set<Reaction> getScopeOfFeeds() {
        return scopeOfFeeds;
    }

    public Feed scopeOfFeeds(Set<Reaction> reactions) {
        this.scopeOfFeeds = reactions;
        return this;
    }

    public Feed addScopeOfFeed(Reaction reaction) {
        this.scopeOfFeeds.add(reaction);
        reaction.setFeed(this);
        return this;
    }

    public Feed removeScopeOfFeed(Reaction reaction) {
        this.scopeOfFeeds.remove(reaction);
        reaction.setFeed(null);
        return this;
    }

    public void setScopeOfFeeds(Set<Reaction> reactions) {
        this.scopeOfFeeds = reactions;
    }

    public Set<File> getFiles() {
        return files;
    }

    public Feed files(Set<File> files) {
        this.files = files;
        return this;
    }

    public Feed addFile(File file) {
        this.files.add(file);
        file.setFeed(this);
        return this;
    }

    public Feed removeFile(File file) {
        this.files.remove(file);
        file.setFeed(null);
        return this;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public Set<Contact> getHelpedFriends() {
        return helpedFriends;
    }

    public Feed helpedFriends(Set<Contact> contacts) {
        this.helpedFriends = contacts;
        return this;
    }

    public Feed addHelpedFriends(Contact contact) {
        this.helpedFriends.add(contact);
        return this;
    }

    public Feed removeHelpedFriends(Contact contact) {
        this.helpedFriends.remove(contact);
        return this;
    }

    public void setHelpedFriends(Set<Contact> contacts) {
        this.helpedFriends = contacts;
    }

    public UserRegistration getUserRegistration() {
        return userRegistration;
    }

    public Feed userRegistration(UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
        return this;
    }

    public void setUserRegistration(UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
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
        Feed feed = (Feed) o;
        if (feed.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feed.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Feed{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", matter='" + getMatter() + "'" +
            ", type='" + getType() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
