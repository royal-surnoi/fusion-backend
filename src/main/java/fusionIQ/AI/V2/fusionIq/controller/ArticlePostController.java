package fusionIQ.AI.V2.fusionIq.controller;

import fusionIQ.AI.V2.fusionIq.data.ArticlePost;
import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.service.ArticlePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articleposts")
public class ArticlePostController {

    @Autowired
    private ArticlePostService articlePostService;

    @PostMapping("/create")
    public ArticlePost createArticlePost(@RequestParam long userId,
                                         @RequestParam String article,
                                         @RequestParam(required = false) String tag) {
        return articlePostService.createArticlePost(userId, article, tag);
    }


    @GetMapping("/{id}")
    public ArticlePost getArticlePostById(@PathVariable long id) {
        return articlePostService.getArticlePostById(id);
    }

    @GetMapping("/getAll")
    public List<ArticlePost> getAllArticlePosts() {
        return articlePostService.getAllArticlePosts();
    }

    @GetMapping("/user/{userId}")
    public List<ArticlePost> getAllArticlePostsByUserId(@PathVariable long userId) {
        return articlePostService.getAllArticlePostsByUserId(userId);
    }

    @PutMapping("/{id}")
    public ArticlePost updateArticlePost(@PathVariable long id,
                                         @RequestParam(required = false) String article,@RequestParam(required = false) String tag) {
        return articlePostService.updateArticlePost(id, article,tag);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteArticlePost(@PathVariable long id) {
        try {
            articlePostService.deleteArticlePost(id);
            return ResponseEntity.ok("ArticlePost deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete ArticlePost: " + e.getMessage());
        }
    }


    @PostMapping("/{postId}/like")
    public ArticlePost likeArticlePost(@PathVariable long postId, @RequestParam long userId) {
        return articlePostService.likeArticlePost(postId, userId);
    }

    @PostMapping("/{postId}/dislike")
    public ArticlePost dislikeArticlePost(@PathVariable long postId, @RequestParam long userId) {
        return articlePostService.dislikeArticlePost(postId, userId);
    }

    @PostMapping("/{id}/share")
    public ArticlePost shareArticlePost(@PathVariable long id) {
        return articlePostService.shareArticlePost(id);
    }

    @GetMapping("/{id}/likeCount")
    public int getLikeCountByArticlePostId(@PathVariable long id) {
        return articlePostService.getLikeCountByArticlePostId(id);
    }

    @GetMapping("/{id}/shareCount")
    public int getShareCountByArticlePostId(@PathVariable long id) {
        return articlePostService.getShareCountByArticlePostId(id);
    }

    @GetMapping("/{postId}/likedUsers")
    public List<User> getUsersWhoLikedPost(@PathVariable long postId) {
        return articlePostService.getUsersWhoLikedPost(postId);
    }
    @GetMapping("/{postId}/liked-by/{userId}")
    public ResponseEntity<Boolean> isImagePostLikedByUser(
            @PathVariable long postId, @PathVariable long userId) {
        boolean isLiked = articlePostService.isArticlePostLikedByUser(postId, userId);
        return ResponseEntity.ok(isLiked);
    }
}
