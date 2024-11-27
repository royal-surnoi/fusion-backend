package fusionIQ.AI.V2.fusionIq.testdata;

import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.data.ShortVideo;
import fusionIQ.AI.V2.fusionIq.data.UserShortVideoInteraction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserShortVideoInteractionTests {

    @Test
    void testGettersAndSetters() {
        // Create a new instance of UserShortVideoInteraction
        UserShortVideoInteraction interaction = new UserShortVideoInteraction();

        // Set values
        long id = 1L;
        User user = new User(); // Assume proper User initialization
        ShortVideo shortVideo = new ShortVideo(); // Assume proper ShortVideo initialization
        long interactionValue = 5L;

        interaction.setId(id);
        interaction.setUser(user);
        interaction.setShortVideo(shortVideo);
        interaction.setShortVideoInteraction(interactionValue);

        // Assert values
        assertThat(interaction.getId()).isEqualTo(id);
        assertThat(interaction.getUser()).isEqualTo(user);
        assertThat(interaction.getShortVideo()).isEqualTo(shortVideo);
        assertThat(interaction.getShortVideoInteraction()).isEqualTo(interactionValue);
    }

    @Test
    void testDefaultConstructor() {
        // Create a new instance using the default constructor
        UserShortVideoInteraction interaction = new UserShortVideoInteraction();

        // Assert default values
        assertThat(interaction.getId()).isZero(); // Default value for long is 0
        assertThat(interaction.getUser()).isNull();
        assertThat(interaction.getShortVideo()).isNull();
        assertThat(interaction.getShortVideoInteraction()).isZero(); // Default value for long is 0
    }

    @Test
    void testParameterizedConstructor() {
        // Create instances for User and ShortVideo
        User user = new User(); // Assume proper User initialization
        ShortVideo shortVideo = new ShortVideo(); // Assume proper ShortVideo initialization
        long interactionValue = 10L;

        // Create a new instance using the parameterized constructor
        UserShortVideoInteraction interaction = new UserShortVideoInteraction(1L, user, shortVideo, interactionValue);

        // Assert that the values are set correctly
        assertThat(interaction.getId()).isEqualTo(1L);
        assertThat(interaction.getUser()).isEqualTo(user);
        assertThat(interaction.getShortVideo()).isEqualTo(shortVideo);
        assertThat(interaction.getShortVideoInteraction()).isEqualTo(interactionValue);
    }

    @Test
    void testToString() {
        // Create instances for User and ShortVideo
        User user = new User(); // Assume proper User initialization
        ShortVideo shortVideo = new ShortVideo(); // Assume proper ShortVideo initialization
        long interactionValue = 15L;

        // Create a new instance
        UserShortVideoInteraction interaction = new UserShortVideoInteraction(2L, user, shortVideo, interactionValue);

        // Verify the toString() method output
        String expectedString = "UserShortVideoInteraction{" +
                "id=2" +
                ", user=" + user +
                ", shortVideo=" + shortVideo +
                ", shortVideoInteraction=" + interactionValue +
                '}';

        assertThat(interaction.toString()).isEqualTo(expectedString);
    }
}
