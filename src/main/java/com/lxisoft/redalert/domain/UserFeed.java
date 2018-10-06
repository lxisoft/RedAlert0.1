package com.lxisoft.redalert.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lxisoft.redalert.domain.enumeration.Alert;

/**
 * A UserFeed.
 */
@Entity
@Table(name = "user_feed")
public class UserFeed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_type")
    private Alert currentType;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "score")
    private Integer score;

    @Column(name = "created_time")
    private Instant createdTime;

    @OneToMany(mappedBy = "user")
    private Set<Action> actions = new HashSet<>();

    @OneToMany(mappedBy = "feed")
    private Set<Reaction> scopeOfFeeds = new HashSet<>();

    @OneToMany(mappedBy = "userFeed")
    private Set<File> files = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_feed_helped_friends",
               joinColumns = @JoinColumn(name = "user_feeds_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "helped_friends_id", referencedColumnName = "id"))
    private Set<Friends> helpedFriends = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("messages")
    private UserRegistration user;

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

    public UserFeed userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public UserFeed message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Alert getCurrentType() {
        return currentType;
    }

    public UserFeed currentType(Alert currentType) {
        this.currentType = currentType;
        return this;
    }

    public void setCurrentType(Alert currentType) {
        this.currentType = currentType;
    }

    public Boolean isIsValid() {
        return isValid;
    }

    public UserFeed isValid(Boolean isValid) {
        this.isValid = isValid;
        return this;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getLatitude() {
        return latitude;
    }

    public UserFeed latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public UserFeed longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getScore() {
        return score;
    }

    public UserFeed score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public UserFeed createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public UserFeed actions(Set<Action> actions) {
        this.actions = actions;
        return this;
    }

    public UserFeed addActions(Action action) {
        this.actions.add(action);
        action.setUser(this);
        return this;
    }

    public UserFeed removeActions(Action action) {
        this.actions.remove(action);
        action.setUser(null);
        return this;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public Set<Reaction> getScopeOfFeeds() {
        return scopeOfFeeds;
    }

    public UserFeed scopeOfFeeds(Set<Reaction> reactions) {
        this.scopeOfFeeds = reactions;
        return this;
    }

    public UserFeed addScopeOfFeed(Reaction reaction) {
        this.scopeOfFeeds.add(reaction);
        reaction.setFeed(this);
        return this;
    }

    public UserFeed removeScopeOfFeed(Reaction reaction) {
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

    public UserFeed files(Set<File> files) {
        this.files = files;
        return this;
    }

    public UserFeed addFile(File file) {
        this.files.add(file);
        file.setUserFeed(this);
        return this;
    }

    public UserFeed removeFile(File file) {
        this.files.remove(file);
        file.setUserFeed(null);
        return this;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public Set<Friends> getHelpedFriends() {
        return helpedFriends;
    }

    public UserFeed helpedFriends(Set<Friends> friends) {
        this.helpedFriends = friends;
        return this;
    }

    public UserFeed addHelpedFriends(Friends friends) {
        this.helpedFriends.add(friends);
        return this;
    }

    public UserFeed removeHelpedFriends(Friends friends) {
        this.helpedFriends.remove(friends);
        return this;
    }

    public void setHelpedFriends(Set<Friends> friends) {
        this.helpedFriends = friends;
    }

    public UserRegistration getUser() {
        return user;
    }

    public UserFeed user(UserRegistration userRegistration) {
        this.user = userRegistration;
        return this;
    }

    public void setUser(UserRegistration userRegistration) {
        this.user = userRegistration;
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
        UserFeed userFeed = (UserFeed) o;
        if (userFeed.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userFeed.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserFeed{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", message='" + getMessage() + "'" +
            ", currentType='" + getCurrentType() + "'" +
            ", isValid='" + isIsValid() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", score=" + getScore() +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
