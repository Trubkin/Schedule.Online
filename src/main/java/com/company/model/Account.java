package com.company.model;

import javax.persistence.*;

@Entity
@Table(name = "accounts", schema = "public")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    // todo определить правило наличия/отсутствия email
    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "passhash")
    private String passhash;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "settings_notification")
    private Boolean settingsNotification;

    @Column(name = "schedule_notidication")
    private Boolean scheduleNotidication;

    @Column(name = "facebook_id")
    private Long facebookId;

    @Column(name = "google_id")
    private Long googleId;

    @Column(name = "vk_id")
    private Long vkId;

    public Account() {
    }

    public Account(String firstName,
                   String secondName,
                   String email,
                   Long phoneNumber,
                   String passhash,
                   Group group,
                   Boolean settingsNotification,
                   Boolean scheduleNotidication,
                   Long facebookId,
                   Long googleId,
                   Long vkId) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passhash = passhash;
        this.group = group;
        this.settingsNotification = settingsNotification;
        this.scheduleNotidication = scheduleNotidication;
        this.facebookId = facebookId;
        this.googleId = googleId;
        this.vkId = vkId;
    }

    public Account(Integer id,
                   String firstName,
                   String secondName,
                   String email,
                   Long phoneNumber,
                   String passhash,
                   Group group,
                   Boolean settingsNotification,
                   Boolean scheduleNotidication,
                   Long facebookId,
                   Long googleId,
                   Long vkId) {
        this(firstName, secondName, email, phoneNumber, passhash, group, settingsNotification, scheduleNotidication, facebookId, googleId, vkId);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getSettingsNotification() {
        return settingsNotification;
    }

    public void setSettingsNotification(Boolean settingsNotification) {
        this.settingsNotification = settingsNotification;
    }

    public Boolean getScheduleNotidication() {
        return scheduleNotidication;
    }

    public void setScheduleNotidication(Boolean scheduleNotidication) {
        this.scheduleNotidication = scheduleNotidication;
    }

    public Long getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(Long facebookId) {
        this.facebookId = facebookId;
    }

    public Long getGoogleId() {
        return googleId;
    }

    public void setGoogleId(Long googleId) {
        this.googleId = googleId;
    }

    public Long getVkId() {
        return vkId;
    }

    public void setVkId(Long vkId) {
        this.vkId = vkId;
    }
}
