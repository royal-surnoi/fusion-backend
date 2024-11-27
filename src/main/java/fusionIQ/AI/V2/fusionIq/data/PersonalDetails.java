package fusionIQ.AI.V2.fusionIq.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class PersonalDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(length = 1500)
    private String userDescription;
    private String userLanguage;
    private String profession;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String phoneNumber;
    private String permanentAddress;
    private String permanentCity;
    private String permanentState;
    private String permanentCountry;
    private String permanentZipcode;
    private String interests;
    private Double longitude;
    private Double latitude;
    private String skills;
    private String gender;
    private Integer age;

    @Column(name = "is_profile_complete", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isProfileComplete = false;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public PersonalDetails() {
    }

    public PersonalDetails(Long id, String firstName, String lastName, String userDescription, String userLanguage, String profession, Date dateOfBirth, String phoneNumber, String permanentAddress, String permanentCity, String permanentState, String permanentCountry, String permanentZipcode, String interests, Double longitude, Double latitude, String skills, String gender, Integer age, boolean isProfileComplete, LocalDateTime createdAt, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userDescription = userDescription;
        this.userLanguage = userLanguage;
        this.profession = profession;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.permanentAddress = permanentAddress;
        this.permanentCity = permanentCity;
        this.permanentState = permanentState;
        this.permanentCountry = permanentCountry;
        this.permanentZipcode = permanentZipcode;
        this.interests = interests;
        this.longitude = longitude;
        this.latitude = latitude;
        this.skills = skills;
        this.gender = gender;
        this.age = age;
        this.isProfileComplete = isProfileComplete;
        this.createdAt = createdAt;
        this.user = user;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPermanentCity() {
        return permanentCity;
    }

    public void setPermanentCity(String permanentCity) {
        this.permanentCity = permanentCity;
    }

    public String getPermanentState() {
        return permanentState;
    }

    public void setPermanentState(String permanentState) {
        this.permanentState = permanentState;
    }

    public String getPermanentCountry() {
        return permanentCountry;
    }

    public void setPermanentCountry(String permanentCountry) {
        this.permanentCountry = permanentCountry;
    }

    public String getPermanentZipcode() {
        return permanentZipcode;
    }

    public void setPermanentZipcode(String permanentZipcode) {
        this.permanentZipcode = permanentZipcode;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public boolean isProfileComplete() {
        return isProfileComplete;
    }

    public void setProfileComplete(boolean profileComplete) {
        isProfileComplete = profileComplete;
    }

    @Override
    public String toString() {
        return "PersonalDetails{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userDescription='" + userDescription + '\'' +
                ", userLanguage='" + userLanguage + '\'' +
                ", profession='" + profession + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", permanentAddress='" + permanentAddress + '\'' +
                ", permanentCity='" + permanentCity + '\'' +
                ", permanentState='" + permanentState + '\'' +
                ", permanentCountry='" + permanentCountry + '\'' +
                ", permanentZipcode='" + permanentZipcode + '\'' +
                ", interests='" + interests + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", skills='" + skills + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", isProfileComplete=" + isProfileComplete +
                ", createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }
}
