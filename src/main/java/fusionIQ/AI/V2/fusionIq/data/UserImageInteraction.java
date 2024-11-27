package fusionIQ.AI.V2.fusionIq.data;


import jakarta.persistence.*;

@Entity
public class UserImageInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long imageInteraction;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "imagePost_id")
    private ImagePost imagePost;

    public UserImageInteraction(long id, long imageInteraction, User user, ImagePost imagePost) {
        this.id = id;
        this.imageInteraction = imageInteraction;
        this.user = user;
        this.imagePost = imagePost;
    }

    public UserImageInteraction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getImageInteraction() {
        return imageInteraction;
    }

    public void setImageInteraction(long imageInteraction) {
        this.imageInteraction = imageInteraction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ImagePost getImagePost() {
        return imagePost;
    }

    public void setImagePost(ImagePost imagePost) {
        this.imagePost = imagePost;
    }

    @Override
    public String toString() {
        return "UserImageInteraction{" +
                "id=" + id +
                ", imageInteraction=" + imageInteraction +
                ", user=" + user +
                ", imagePost=" + imagePost +
                '}';
    }
}