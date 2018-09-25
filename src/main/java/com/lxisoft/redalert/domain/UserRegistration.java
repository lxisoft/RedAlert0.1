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

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "created_time")
    private Instant createdTime;

    @OneToMany(mappedBy = "userRegistration")
    @JsonIgnore
    private Set<Feed> comments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_registration_emergency_contact",
               joinColumns = @JoinColumn(name="user_registrations_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="emergency_contacts_id", referencedColumnName="id"))
    private Set<Contact> emergencyContacts = new HashSet<>();

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

    public Set<Feed> getComments() {
        return comments;
    }

    public UserRegistration comments(Set<Feed> feeds) {
        this.comments = feeds;
        return this;
    }

    public UserRegistration addComments(Feed feed) {
        this.comments.add(feed);
        feed.setUserRegistration(this);
        return this;
    }

    public UserRegistration removeComments(Feed feed) {
        this.comments.remove(feed);
        feed.setUserRegistration(null);
        return this;
    }

    public void setComments(Set<Feed> feeds) {
        this.comments = feeds;
    }

    public Set<Contact> getEmergencyContacts() {
        return emergencyContacts;
    }

    public UserRegistration emergencyContacts(Set<Contact> contacts) {
        this.emergencyContacts = contacts;
        return this;
    }

    public UserRegistration addEmergencyContact(Contact contact) {
        this.emergencyContacts.add(contact);
        return this;
    }

    public UserRegistration removeEmergencyContact(Contact contact) {
        this.emergencyContacts.remove(contact);
        return this;
    }

    public void setEmergencyContacts(Set<Contact> contacts) {
        this.emergencyContacts = contacts;
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
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            "}";
    }
}
