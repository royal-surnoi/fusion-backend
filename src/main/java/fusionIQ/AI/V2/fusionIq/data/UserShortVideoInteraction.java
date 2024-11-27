package fusionIQ.AI.V2.fusionIq.data;


import jakarta.persistence.*;

@Entity
public class UserShortVideoInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "short_video_id")
    private ShortVideo shortVideo;

    private long shortVideoInteraction;

    public UserShortVideoInteraction() {
    }

    public UserShortVideoInteraction(long id, User user, ShortVideo shortVideo, long shortVideoInteraction) {
        this.id = id;
        this.user = user;
        this.shortVideo = shortVideo;
        this.shortVideoInteraction = shortVideoInteraction;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ShortVideo getShortVideo() {
        return shortVideo;
    }

    public void setShortVideo(ShortVideo shortVideo) {
        this.shortVideo = shortVideo;
    }

    public long getShortVideoInteraction() {
        return shortVideoInteraction;
    }

    public void setShortVideoInteraction(long shortVideoInteraction) {
        this.shortVideoInteraction = shortVideoInteraction;
    }

    @Override
    public String toString() {
        return "UserShortVideoInteraction{" +
                "id=" + id +
                ", user=" + user +
                ", shortVideo=" + shortVideo +
                ", shortVideoInteraction=" + shortVideoInteraction +
                '}';
    }
}


