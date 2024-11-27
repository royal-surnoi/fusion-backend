package fusionIQ.AI.V2.fusionIq.testdata;

import fusionIQ.AI.V2.fusionIq.data.ArticlePost;
import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.data.UserArticleInteraction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserArticleInteractionTests {

    @Test
    void testDefaultConstructor() {
        UserArticleInteraction interaction = new UserArticleInteraction();

        assertThat(interaction.getId()).isEqualTo(0); // Default value for long should be 0
        assertThat(interaction.getUser()).isNull();
        assertThat(interaction.getArticlePost()).isNull();
        assertThat(interaction.getArticleInteraction()).isEqualTo(0); // Default value for long should be 0
    }

    @Test
    void testParameterizedConstructor() {
        User user = new User(); // Replace with actual User object initialization
        ArticlePost articlePost = new ArticlePost(); // Replace with actual ArticlePost object initialization
        long interaction = 10L;

        UserArticleInteraction interactionObj = new UserArticleInteraction(1L, user, articlePost, interaction);

        assertThat(interactionObj.getId()).isEqualTo(1L);
        assertThat(interactionObj.getUser()).isEqualTo(user);
        assertThat(interactionObj.getArticlePost()).isEqualTo(articlePost);
        assertThat(interactionObj.getArticleInteraction()).isEqualTo(interaction);
    }

    @Test
    void testSettersAndGetters() {
        User user = new User(); // Replace with actual User object initialization
        ArticlePost articlePost = new ArticlePost(); // Replace with actual ArticlePost object initialization

        UserArticleInteraction interaction = new UserArticleInteraction();
        interaction.setId(1L);
        interaction.setUser(user);
        interaction.setArticlePost(articlePost);
        interaction.setArticleInteraction(20L);

        assertThat(interaction.getId()).isEqualTo(1L);
        assertThat(interaction.getUser()).isEqualTo(user);
        assertThat(interaction.getArticlePost()).isEqualTo(articlePost);
        assertThat(interaction.getArticleInteraction()).isEqualTo(20L);
    }

    @Test
    void testToString() {
        User user = new User(); // Replace with actual User object initialization
        ArticlePost articlePost = new ArticlePost(); // Replace with actual ArticlePost object initialization

        UserArticleInteraction interaction = new UserArticleInteraction(1L, user, articlePost, 30L);

        String expectedString = "UserArticleInteraction{" +
                "id=1" +
                ", user=" + user +
                ", articlePost=" + articlePost +
                ", articleInteraction=30" +
                '}';

        assertThat(interaction.toString()).isEqualTo(expectedString);
    }
}
