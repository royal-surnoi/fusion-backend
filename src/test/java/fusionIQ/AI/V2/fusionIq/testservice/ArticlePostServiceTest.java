package fusionIQ.AI.V2.fusionIq.testservice;



import fusionIQ.AI.V2.fusionIq.data.ArticlePost;
import fusionIQ.AI.V2.fusionIq.data.ArticlePostLike;
import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.repository.*;
import fusionIQ.AI.V2.fusionIq.service.ArticlePostService;
import fusionIQ.AI.V2.fusionIq.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ArticlePostServiceTest {

    @InjectMocks
    private ArticlePostService articlePostService;

    @Mock
    private ArticlePostRepo articlePostRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ArticlePostLikeRepo articlePostLikeRepo;

    @Mock
    private CommentRepo commentRepo;

    @Mock
    private UserArticleInteractionRepo userArticleInteractionRepo;

    @Mock
    private SavedItemsRepo savedItemsRepo;

    @Mock
    private NotificationService notificationService;

    @Mock
    private AIFeedRepo aiFeedRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateArticlePost() {
        User mockUser = new User();
        mockUser.setId(1L);

        ArticlePost mockArticlePost = new ArticlePost();
        mockArticlePost.setId(1L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(articlePostRepo.save(any(ArticlePost.class))).thenReturn(mockArticlePost);

        ArticlePost createdArticlePost = articlePostService.createArticlePost(1L, "Sample Article", "Tag");

        assertEquals(mockArticlePost, createdArticlePost);
    }

    @Test
    void testGetArticlePostById() {
        ArticlePost mockArticlePost = new ArticlePost();
        mockArticlePost.setId(1L);

        when(articlePostRepo.findById(1L)).thenReturn(Optional.of(mockArticlePost));

        ArticlePost retrievedArticlePost = articlePostService.getArticlePostById(1L);

        assertEquals(mockArticlePost, retrievedArticlePost);
    }

    @Test
    void testGetAllArticlePosts() {
        ArticlePost mockArticlePost = new ArticlePost();
        mockArticlePost.setId(1L);

        List<ArticlePost> mockArticlePosts = Arrays.asList(mockArticlePost);

        when(articlePostRepo.findAllOrderByPostDateDesc()).thenReturn(mockArticlePosts);

        List<ArticlePost> retrievedArticlePosts = articlePostService.getAllArticlePosts();

        assertEquals(mockArticlePosts, retrievedArticlePosts);
    }

    @Test
    void testGetAllArticlePostsByUserId() {
        User mockUser = new User();
        mockUser.setId(1L);

        ArticlePost mockArticlePost = new ArticlePost();
        mockArticlePost.setId(1L);

        List<ArticlePost> mockArticlePosts = Arrays.asList(mockArticlePost);

        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(articlePostRepo.findByUserOrderByPostDateDesc(mockUser)).thenReturn(mockArticlePosts);

        List<ArticlePost> retrievedArticlePosts = articlePostService.getAllArticlePostsByUserId(1L);

        assertEquals(mockArticlePosts, retrievedArticlePosts);
    }

    @Test
    void testUpdateArticlePost() {
        ArticlePost mockArticlePost = new ArticlePost();
        mockArticlePost.setId(1L);

        when(articlePostRepo.findById(1L)).thenReturn(Optional.of(mockArticlePost));
        when(articlePostRepo.save(mockArticlePost)).thenReturn(mockArticlePost);

        ArticlePost updatedArticlePost = articlePostService.updateArticlePost(1L, "Updated Article", "Updated Tag");

        assertEquals(mockArticlePost, updatedArticlePost);
    }

    @Test
    void testDeleteArticlePost() {
        ArticlePost mockArticlePost = new ArticlePost();
        mockArticlePost.setId(1L);

        when(articlePostRepo.findById(1L)).thenReturn(Optional.of(mockArticlePost));

        articlePostService.deleteArticlePost(1L);

        verify(articlePostLikeRepo).deleteByArticlePost(mockArticlePost);
        verify(commentRepo).deleteByArticlePost(mockArticlePost);
        verify(userArticleInteractionRepo).deleteByArticlePost(mockArticlePost);
        verify(savedItemsRepo).deleteByArticlePost(mockArticlePost);
        verify(articlePostRepo).delete(mockArticlePost);
        verify(aiFeedRepo).deleteByArticlePost(mockArticlePost);
    }

    @Test
    void testLikeArticlePost() {
        User mockUser = new User();
        mockUser.setId(1L);

        ArticlePost mockArticlePost = new ArticlePost();
        mockArticlePost.setId(1L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(articlePostRepo.findById(1L)).thenReturn(Optional.of(mockArticlePost));
        when(articlePostLikeRepo.findByArticlePostAndUser(mockArticlePost, mockUser)).thenReturn(Arrays.asList());

        ArticlePost likedArticlePost = articlePostService.likeArticlePost(1L, 1L);

        assertEquals(mockArticlePost, likedArticlePost);
        verify(articlePostLikeRepo).save(any(ArticlePostLike.class));
        verify(notificationService).createLikePostNotification(1L, 1L, "article");
    }

    @Test
    void testDislikeArticlePost() {
        User mockUser = new User();
        mockUser.setId(1L);

        ArticlePost mockArticlePost = new ArticlePost();
        mockArticlePost.setId(1L);

        ArticlePostLike mockArticlePostLike = new ArticlePostLike(mockUser, mockArticlePost);

        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(articlePostRepo.findById(1L)).thenReturn(Optional.of(mockArticlePost));
        when(articlePostLikeRepo.findByArticlePostAndUser(mockArticlePost, mockUser)).thenReturn(Arrays.asList(mockArticlePostLike));

        ArticlePost dislikedArticlePost = articlePostService.dislikeArticlePost(1L, 1L);

        assertEquals(mockArticlePost, dislikedArticlePost);
        verify(articlePostLikeRepo).deleteAll(anyList());
    }

    @Test
    void testShareArticlePost() {
        ArticlePost mockArticlePost = new ArticlePost();
        mockArticlePost.setId(1L);

        when(articlePostRepo.findById(1L)).thenReturn(Optional.of(mockArticlePost));
        when(articlePostRepo.save(mockArticlePost)).thenReturn(mockArticlePost);

        ArticlePost sharedArticlePost = articlePostService.shareArticlePost(1L);

        assertEquals(mockArticlePost, sharedArticlePost);
    }

    @Test
    void testGetLikeCountByArticlePostId() {
        ArticlePost mockArticlePost = new ArticlePost();
        mockArticlePost.setId(1L);
        mockArticlePost.setArticleLikeCount(5);

        when(articlePostRepo.findById(1L)).thenReturn(Optional.of(mockArticlePost));

        int likeCount = articlePostService.getLikeCountByArticlePostId(1L);

        assertEquals(5, likeCount);
    }

    @Test
    void testGetShareCountByArticlePostId() {
        ArticlePost mockArticlePost = new ArticlePost();
        mockArticlePost.setId(1L);
        mockArticlePost.setArticleShareCount(3);

        when(articlePostRepo.findById(1L)).thenReturn(Optional.of(mockArticlePost));

        int shareCount = articlePostService.getShareCountByArticlePostId(1L);

        assertEquals(3, shareCount);
    }

    @Test
    void testGetUsersWhoLikedPost() {
        User mockUser = new User();
        mockUser.setId(1L);

        ArticlePost mockArticlePost = new ArticlePost();
        mockArticlePost.setId(1L);

        ArticlePostLike mockArticlePostLike = new ArticlePostLike(mockUser, mockArticlePost);

        when(articlePostRepo.findById(1L)).thenReturn(Optional.of(mockArticlePost));
        when(articlePostLikeRepo.findByArticlePost(mockArticlePost)).thenReturn(Arrays.asList(mockArticlePostLike));

        List<User> usersWhoLikedPost = articlePostService.getUsersWhoLikedPost(1L);

        assertEquals(Arrays.asList(mockUser), usersWhoLikedPost);
    }

    @Test
    void testIsArticlePostLikedByUser() {
        when(articlePostLikeRepo.findByUserIdAndArticlePostId(1L, 1L)).thenReturn(Optional.of(new ArticlePostLike()));

        boolean isLiked = articlePostService.isArticlePostLikedByUser(1L, 1L);

        assertEquals(true, isLiked);
    }
}
