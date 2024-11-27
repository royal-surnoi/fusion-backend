package fusionIQ.AI.V2.fusionIq.controller;

import fusionIQ.AI.V2.fusionIq.data.ImagePost;
import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.service.ImagePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/imagePosts")
public class ImagePostController {

    @Autowired
    private ImagePostService imagePostService;

    @PostMapping("/create")
    public ImagePost createImagePost(@RequestParam long userId,
                                     @RequestParam MultipartFile photo,
                                     @RequestParam(required = false) String imageDescription,
                                     @RequestParam(required = false) String tag) throws IOException {
        byte[] photoBytes = photo.getBytes();
        return imagePostService.createImagePost(userId, photoBytes, imageDescription, tag);
    }


    @GetMapping("/get/{id}")
    public ImagePost getImagePostById(@PathVariable long id) {
        return imagePostService.getImagePostById(id);
    }

    @GetMapping("/getAll")
    public List<ImagePost> getAllImagePosts() {
        return imagePostService.getAllImagePosts();
    }

    @GetMapping("/user/{userId}")
    public List<ImagePost> getAllImagePostsByUserId(@PathVariable long userId) {
        return imagePostService.getAllImagePostsByUserId(userId);
    }

    @PutMapping("/update/{id}")
    public ImagePost updateImagePost(@PathVariable long id, @RequestParam(required = false) MultipartFile photo, @RequestParam(required = false) String imageDescription,@RequestParam(required = false) String tag) throws IOException {
        byte[] photoBytes = photo != null ? photo.getBytes() : null;
        return imagePostService.updateImagePost(id, photoBytes,imageDescription,tag);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImagePost(@PathVariable long id) {
        imagePostService.deleteImagePost(id);
    }

    @PostMapping("/{postId}/like")
    public ImagePost likeImagePost(@PathVariable long postId, @RequestParam long userId) {
        return imagePostService.likeImagePost(postId, userId);
    }

    @PostMapping("/{postId}/dislike")
    public ImagePost dislikeImagePost(@PathVariable long postId) {
        return imagePostService.dislikeImagePost(postId);
    }

    @PostMapping("/{postId}/share")
    public ImagePost shareImagePost(@PathVariable long postId) {
        return imagePostService.shareImagePost(postId);
    }

    @GetMapping("/{postId}/likeCount")
    public int getLikeCountByImagePostId(@PathVariable long postId) {
        return imagePostService.getLikeCountByImagePostId(postId);
    }

    @GetMapping("/{postId}/shareCount")
    public int getShareCountByImagePostId(@PathVariable long postId) {
        return imagePostService.getShareCountByImagePostId(postId);
    }

    @GetMapping("/{postId}/likedUsers")
    public List<User> getUsersWhoLikedPost(@PathVariable long postId) {
        return imagePostService.getUsersWhoLikedImagePost(postId);
    }
    @GetMapping("/{postId}/liked-by/{userId}")
    public ResponseEntity<Boolean> isImagePostLikedByUser(
            @PathVariable long postId, @PathVariable long userId) {
        boolean isLiked = imagePostService.isImagePostLikedByUser(postId, userId);
        return ResponseEntity.ok(isLiked);
    }
}