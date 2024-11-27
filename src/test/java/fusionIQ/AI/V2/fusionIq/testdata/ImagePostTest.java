package fusionIQ.AI.V2.fusionIq.testdata;

import fusionIQ.AI.V2.fusionIq.data.ImagePost;
import fusionIQ.AI.V2.fusionIq.data.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class ImagePostTest {

    @Test
    void testImagePostDefaultConstructor() {
        ImagePost imagePost = new ImagePost();
        assertThat(imagePost).isNotNull();
        assertThat(imagePost.getId()).isEqualTo(0);
        assertThat(imagePost.getPhoto()).isNull();
        assertThat(imagePost.getUser()).isNull();
        assertThat(imagePost.getUserImageInteraction()).isNull();
        assertThat(imagePost.getPostDate()).isNull();
        assertThat(imagePost.getUpdatedDate()).isNull();
        assertThat(imagePost.getImageLikeCount()).isEqualTo(0);
        assertThat(imagePost.getImageDislikes()).isEqualTo(0);
        assertThat(imagePost.getImageShareCount()).isEqualTo(0);
        assertThat(imagePost.getImageDescription()).isNull();
        assertThat(imagePost.getTag()).isNull();
    }

    @Test
    void testImagePostParameterizedConstructor() {
        User user = new User();
        user.setId(1L);

        byte[] photo = new byte[]{1, 2, 3};
        LocalDateTime now = LocalDateTime.now();

        ImagePost imagePost = new ImagePost(1L, photo, user, Collections.emptyList(), now, now, 10, 2, 5, "Test Description", "Test Tag", Collections.emptyList());

        assertThat(imagePost.getId()).isEqualTo(1L);
        assertThat(imagePost.getPhoto()).isEqualTo(photo);
        assertThat(imagePost.getUser()).isEqualTo(user);
        assertThat(imagePost.getPostDate()).isEqualTo(now);
        assertThat(imagePost.getUpdatedDate()).isEqualTo(now);
        assertThat(imagePost.getImageLikeCount()).isEqualTo(10);
        assertThat(imagePost.getImageDislikes()).isEqualTo(2);
        assertThat(imagePost.getImageShareCount()).isEqualTo(5);
        assertThat(imagePost.getImageDescription()).isEqualTo("Test Description");
        assertThat(imagePost.getTag()).isEqualTo("Test Tag");
    }

    @Test
    void testSettersAndGetters() {
        ImagePost imagePost = new ImagePost();

        User user = new User();
        user.setId(1L);
        byte[] photo = new byte[]{1, 2, 3};
        LocalDateTime now = LocalDateTime.now();

        imagePost.setId(1L);
        imagePost.setPhoto(photo);
        imagePost.setUser(user);
        imagePost.setPostDate(now);
        imagePost.setUpdatedDate(now);
        imagePost.setImageLikeCount(10);
        imagePost.setImageDislikes(2);
        imagePost.setImageShareCount(5);
        imagePost.setImageDescription("Test Description");
        imagePost.setTag("Test Tag");

        assertThat(imagePost.getId()).isEqualTo(1L);
        assertThat(imagePost.getPhoto()).isEqualTo(photo);
        assertThat(imagePost.getUser()).isEqualTo(user);
        assertThat(imagePost.getPostDate()).isEqualTo(now);
        assertThat(imagePost.getUpdatedDate()).isEqualTo(now);
        assertThat(imagePost.getImageLikeCount()).isEqualTo(10);
        assertThat(imagePost.getImageDislikes()).isEqualTo(2);
        assertThat(imagePost.getImageShareCount()).isEqualTo(5);
        assertThat(imagePost.getImageDescription()).isEqualTo("Test Description");
        assertThat(imagePost.getTag()).isEqualTo("Test Tag");
    }

    @Test
    void testToString() {
        User user = new User();
        user.setId(1L);
        ImagePost imagePost = new ImagePost(1L, new byte[]{1, 2, 3}, user, Collections.emptyList(),
                LocalDateTime.now(), LocalDateTime.now(), 10, 2, 5, "Test Description", "Test Tag", Collections.emptyList());

        String expectedString = "ImagePost{" +
                "id=1, " +
                "photo=[1, 2, 3], " +
                "user=" + user + ", " +
                "UserImageInteraction=[], " +
                "postDate=" + imagePost.getPostDate() + ", " +
                "updatedDate=" + imagePost.getUpdatedDate() + ", " +
                "imageLikeCount=10, " +
                "imageDislikes=2, " +
                "imageShareCount=5, " +
                "imageDescription='Test Description', " +
                "tag='Test Tag'}";

        assertThat(imagePost.toString()).isEqualToIgnoringWhitespace(expectedString);
    }
}
