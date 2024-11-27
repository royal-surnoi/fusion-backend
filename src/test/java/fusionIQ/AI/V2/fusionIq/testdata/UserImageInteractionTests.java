package fusionIQ.AI.V2.fusionIq.testdata;

import fusionIQ.AI.V2.fusionIq.data.ImagePost;
import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.data.UserImageInteraction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserImageInteractionTests {

    @Test
    void testParameterizedConstructor() {
        User user = new User(); // Replace with actual User object initialization
        ImagePost imagePost = new ImagePost(); // Replace with actual ImagePost object initialization
        long interaction = 123L;

        UserImageInteraction interactionObj = new UserImageInteraction(1L, interaction, user, imagePost);

        assertThat(interactionObj.getId()).isEqualTo(1L);
        assertThat(interactionObj.getImageInteraction()).isEqualTo(interaction);
        assertThat(interactionObj.getUser()).isEqualTo(user);
        assertThat(interactionObj.getImagePost()).isEqualTo(imagePost);
    }

    @Test
    void testDefaultConstructor() {
        UserImageInteraction interactionObj = new UserImageInteraction();

        assertThat(interactionObj.getId()).isZero();
        assertThat(interactionObj.getImageInteraction()).isZero();
        assertThat(interactionObj.getUser()).isNull();
        assertThat(interactionObj.getImagePost()).isNull();
    }

    @Test
    void testSettersAndGetters() {
        User user = new User(); // Replace with actual User object initialization
        ImagePost imagePost = new ImagePost(); // Replace with actual ImagePost object initialization

        UserImageInteraction interactionObj = new UserImageInteraction();
        interactionObj.setId(2L);
        interactionObj.setImageInteraction(456L);
        interactionObj.setUser(user);
        interactionObj.setImagePost(imagePost);

        assertThat(interactionObj.getId()).isEqualTo(2L);
        assertThat(interactionObj.getImageInteraction()).isEqualTo(456L);
        assertThat(interactionObj.getUser()).isEqualTo(user);
        assertThat(interactionObj.getImagePost()).isEqualTo(imagePost);
    }

    @Test
    void testToString() {
        User user = new User(); // Replace with actual User object initialization
        ImagePost imagePost = new ImagePost(); // Replace with actual ImagePost object initialization

        UserImageInteraction interactionObj = new UserImageInteraction(3L, 789L, user, imagePost);

        String expectedString = "UserImageInteraction{" +
                "id=" + 3L +
                ", imageInteraction=" + 789L +
                ", user=" + user +
                ", imagePost=" + imagePost +
                '}';

        assertThat(interactionObj.toString()).isEqualTo(expectedString);
    }
}
