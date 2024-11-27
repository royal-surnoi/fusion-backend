package fusionIQ.AI.V2.fusionIq.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private long id;

    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    private String preferences;
    private String profession;
    private String userLanguage;

    private String otp;
    private LocalDateTime otpGeneratedTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] userImage;
    @Enumerated(EnumType.STRING)
    private OnlineStatus onlineStatus;

    private LocalDateTime lastSeen;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices = new ArrayList<>();




    @Column(length = 1500)
    private String userDescription;



    public enum OnlineStatus {
        ONLINE,
        OFFLINE
    }



    public User() {
         this.createdAt = LocalDateTime.now();
          this.updatedAt = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtpGeneratedTime() {
        return otpGeneratedTime;
    }

    public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
        this.otpGeneratedTime = otpGeneratedTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }



    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public User(long id, String name, String email, String password, String preferences, String profession, String userLanguage, String otp, LocalDateTime otpGeneratedTime, LocalDateTime createdAt, LocalDateTime updatedAt, byte[] userImage, OnlineStatus onlineStatus, LocalDateTime lastSeen, String userDescription) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.preferences = preferences;
        this.profession = profession;
        this.userLanguage = userLanguage;
        this.otp = otp;
        this.otpGeneratedTime = otpGeneratedTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userImage = userImage;
        this.onlineStatus = onlineStatus;
        this.lastSeen = lastSeen;
        this.userDescription = userDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", preferences='" + preferences + '\'' +
                ", profession='" + profession + '\'' +
                ", userLanguage='" + userLanguage + '\'' +
                ", otp='" + otp + '\'' +
                ", otpGeneratedTime=" + otpGeneratedTime +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userImage=" + Arrays.toString(userImage) +
                ", onlineStatus=" + onlineStatus +
                ", lastSeen=" + lastSeen +
                ", userDescription='" + userDescription + '\'' +
                '}';
    }

    public User(Long id)  {
        this.id = id;
    }
}

