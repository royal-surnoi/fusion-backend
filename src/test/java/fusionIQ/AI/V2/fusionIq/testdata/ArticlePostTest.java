package fusionIQ.AI.V2.fusionIq.testdata;

import fusionIQ.AI.V2.fusionIq.data.ArticlePost;
import fusionIQ.AI.V2.fusionIq.data.Comment;
import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.data.UserArticleInteraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticlePostTest {

    private ArticlePost articlePost;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        articlePost = new ArticlePost();
    }

    @Test
    void testDefaultConstructor() {
        assertThat(articlePost.getId()).isEqualTo(0L);
        assertThat(articlePost.getArticle()).isNull();
        assertThat(articlePost.getUser()).isNull();
        assertThat(articlePost.getPostDate()).isNotNull();
        assertThat(articlePost.getUpdatedDate()).isNull();
        assertThat(articlePost.getArticleLikeCount()).isEqualTo(0);
        assertThat(articlePost.getArticleDislikes()).isEqualTo(0);
        assertThat(articlePost.getArticleShareCount()).isEqualTo(0);
        assertThat(articlePost.getTag()).isNull();
        assertThat(articlePost.getComments()).isNull();
        assertThat(articlePost.getUserArticleInteraction()).isNull();
    }

    @Test
    void testSettersAndGetters() {
        LocalDateTime now = LocalDateTime.now();

        articlePost.setId(1L);
        articlePost.setArticle("Sample Article");
        articlePost.setUser(user);
        articlePost.setPostDate(now);
        articlePost.setUpdatedDate(now);
        articlePost.setArticleLikeCount(10);
        articlePost.setArticleDislikes(2);
        articlePost.setArticleShareCount(5);
        articlePost.setTag("Technology");

        assertThat(articlePost.getId()).isEqualTo(1L);
        assertThat(articlePost.getArticle()).isEqualTo("Sample Article");
        assertThat(articlePost.getUser()).isEqualTo(user);
        assertThat(articlePost.getPostDate()).isEqualTo(now);
        assertThat(articlePost.getUpdatedDate()).isEqualTo(now);
        assertThat(articlePost.getArticleLikeCount()).isEqualTo(10);
        assertThat(articlePost.getArticleDislikes()).isEqualTo(2);
        assertThat(articlePost.getArticleShareCount()).isEqualTo(5);
        assertThat(articlePost.getTag()).isEqualTo("Technology");
    }

    @Test
    void testParameterizedConstructor() {
        LocalDateTime now = LocalDateTime.now();
        List<Comment> comments = new ArrayList<>();
        List<UserArticleInteraction> interactions = new ArrayList<>();

        ArticlePost articlePost = new ArticlePost(1L, "Sample Article", user, now, now, 10, 2, 5, "Technology", comments, interactions);

        assertThat(articlePost.getId()).isEqualTo(1L);
        assertThat(articlePost.getArticle()).isEqualTo("Sample Article");
        assertThat(articlePost.getUser()).isEqualTo(user);
        assertThat(articlePost.getPostDate()).isEqualTo(now);
        assertThat(articlePost.getUpdatedDate()).isEqualTo(now);
        assertThat(articlePost.getArticleLikeCount()).isEqualTo(10);
        assertThat(articlePost.getArticleDislikes()).isEqualTo(2);
        assertThat(articlePost.getArticleShareCount()).isEqualTo(5);
        assertThat(articlePost.getTag()).isEqualTo("Technology");
        assertThat(articlePost.getComments()).isEqualTo(comments);
        assertThat(articlePost.getUserArticleInteraction()).isEqualTo(interactions);
    }

    @Test
    void testToString() {
        LocalDateTime now = LocalDateTime.now();
        List<Comment> comments = new ArrayList<>();
        List<UserArticleInteraction> interactions = new ArrayList<>();

        articlePost.setId(1L);
        articlePost.setArticle("Sample Article");
        articlePost.setUser(user);
        articlePost.setPostDate(now);
        articlePost.setUpdatedDate(now);
        articlePost.setArticleLikeCount(10);
        articlePost.setArticleDislikes(2);
        articlePost.setArticleShareCount(5);
        articlePost.setTag("Technology");
        articlePost.setComments(comments);
        articlePost.setUserArticleInteraction(interactions);

        String expected = "ArticlePost{id=1, article='Sample Article', user=" + user + ", postDate=" + now + ", updatedDate=" + now + ", articleLikeCount=10, articleDislikes=2, articleShareCount=5, tag='Technology', userArticleInteraction=" + interactions + "}";
        assertThat(articlePost.toString()).isEqualTo(expected);
    }

    @Test
    void testNullFields() {
        articlePost.setArticle(null);
        articlePost.setTag(null);
        articlePost.setComments(null);
        articlePost.setUserArticleInteraction(null);

        assertThat(articlePost.getArticle()).isNull();
        assertThat(articlePost.getTag()).isNull();
        assertThat(articlePost.getComments()).isNull();
        assertThat(articlePost.getUserArticleInteraction()).isNull();
    }

    @Test
    void testAddRemoveComments() {
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        articlePost.setComments(comments);
        assertThat(articlePost.getComments()).hasSize(2);

        articlePost.getComments().remove(comment1);
        assertThat(articlePost.getComments()).hasSize(1).contains(comment2);
    }
}
