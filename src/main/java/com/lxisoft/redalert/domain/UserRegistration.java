package com.lxisoft.redalert.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserRegistration.
 */
@Entity
@Table(name = "user_registration")
public class UserRegistration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "email")
    private String email;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Lob
    @Column(name = "user_image")
    private byte[] userImage;

    @Column(name = "user_image_content_type")
    private String userImageContentType;

    @Column(name = "points")
    private Long points;

    @Column(name = "date_of_bith")
    private Instant dateOfBith;

    @Column(name = "created_time")
    private Instant createdTime;

    @OneToMany(mappedBy = "user")
    private Set<UserFeed> messages = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_registration_friends",
               joinColumns = @JoinColumn(name = "user_registrations_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "friends_id", referencedColumnName = "id"))
    private Set<Friends> friends = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegistration firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegistration lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhone() {
        return phone;
    }

    public UserRegistration phone(Long phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistration email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistration password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public UserRegistration bloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
        return this;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public UserRegistration userImage(byte[] userImage) {
        this.userImage = userImage;
        return this;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public String getUserImageContentType() {
        return userImageContentType;
    }

    public UserRegistration userImageContentType(String userImageContentType) {
        this.userImageContentType = userImageContentType;
        return this;
    }

    public void setUserImageContentType(String userImageContentType) {
        this.userImageContentType = userImageContentType;
    }

    public Long getPoints() {
        return points;
    }

    public UserRegistration points(Long points) {
        this.points = points;
        return this;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Instant getDateOfBith() {
        return dateOfBith;
    }

    public UserRegistration dateOfBith(Instant dateOfBith) {
        this.dateOfBith = dateOfBith;
        return this;
    }

    public void setDateOfBith(Instant dateOfBith) {
        this.dateOfBith = dateOfBith;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public UserRegistration createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Set<UserFeed> getMessages() {
        return messages;
    }

    public UserRegistration messages(Set<UserFeed> userFeeds) {
        this.messages = userFeeds;
        return this;
    }

    public UserRegistration addMessage(UserFeed userFeed) {
        this.messages.add(userFeed);
        userFeed.setUser(this);
        return this;
    }

    public UserRegistration removeMessage(UserFeed userFeed) {
        this.messages.remove(userFeed);
        userFeed.setUser(null);
        return this;
    }

    public void setMessages(Set<UserFeed> userFeeds) {
        this.messages = userFeeds;
    }

    public Set<Friends> getFriends() {
        return friends;
    }

    public UserRegistration friends(Set<Friends> friends) {
        this.friends = friends;
        return this;
    }

    public UserRegistration addFriends(Friends friends) {
        this.friends.add(friends);
        friends.getUsers().add(this);
        return this;
    }

    public UserRegistration removeFriends(Friends friends) {
        this.friends.remove(friends);
        friends.getUsers().remove(this);
        return this;
    }

    public void setFriends(Set<Friends> friends) {
        this.friends = friends;
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
        UserRegistration userRegistration = (UserRegistration) o;
        if (userRegistration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userRegistration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserRegistration{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone=" + getPhone() +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", userImage='" + getUserImage() + "'" +
            ", userImageContentType='" + getUserImageContentType() + "'" +
            ", points=" + getPoints() +
            ", dateOfBith='" + getDateOfBith() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
