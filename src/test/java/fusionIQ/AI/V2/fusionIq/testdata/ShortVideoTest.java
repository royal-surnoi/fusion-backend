package fusionIQ.AI.V2.fusionIq.testdata;

import fusionIQ.AI.V2.fusionIq.data.ShortVideo;
import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.data.VideoComment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ShortVideoTest {

    private ShortVideo shortVideo;
    private User user;

    @BeforeEach
    void setUp() {
        // Initialize the User entity according to its structure
        user = new User(); // Initialize User with required properties
        user.setName("testuser");

        List<VideoComment> videoComments = new ArrayList<>(); // Assuming VideoComment entity exists
        Set<User> likedByUsers = new HashSet<>();

        shortVideo = new ShortVideo(
                1L,
                "Sample Title",
                "sample-s3-key",
                "http://sample-url.com",
                "Sample Description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "English",
                user,
                videoComments,
                likedByUsers,
                100, // likes
                50,  // shares
                5000, // views
                "SampleTag",
                "2:15" // shortVideoDuration
        );
    }

    @Test
    void testShortVideoInitialization() {
        assertNotNull(shortVideo);
        assertEquals(1L, shortVideo.getId());
        assertEquals("Sample Title", shortVideo.getShortVideoTitle());
        assertEquals("sample-s3-key", shortVideo.getS3Key());
        assertEquals("http://sample-url.com", shortVideo.getS3Url());
        assertEquals("Sample Description", shortVideo.getShortVideoDescription());
        assertEquals("English", shortVideo.getLanguage());
        assertEquals("SampleTag", shortVideo.getTag());
        assertEquals("2:15", shortVideo.getShortVideoDuration());
    }

    @Test
    void testShortVideoLikesSharesViews() {
        assertEquals(100, shortVideo.getShortVideoLikes());
        assertEquals(50, shortVideo.getShortVideoShares());
        assertEquals(5000, shortVideo.getShortVideoViews());
    }

    @Test
    void testShortVideoUserAssociation() {
        assertNotNull(shortVideo.getUser());
        assertEquals("testuser", shortVideo.getUser().getName());
    }

    @Test
    void testUpdateShortVideoLikes() {
        shortVideo.setShortVideoLikes(200);
        assertEquals(200, shortVideo.getShortVideoLikes());
    }

    @Test
    void testUpdateShortVideoViews() {
        shortVideo.setShortVideoViews(6000);
        assertEquals(6000, shortVideo.getShortVideoViews());
    }

    @Test
    void testUpdateShortVideoDuration() {
        shortVideo.setShortVideoDuration("3:00");
        assertEquals("3:00", shortVideo.getShortVideoDuration());
    }
}
