package fusionIQ.AI.V2.fusionIq.controller;

import fusionIQ.AI.V2.fusionIq.data.AiFeed;
import fusionIQ.AI.V2.fusionIq.data.Search;
import fusionIQ.AI.V2.fusionIq.repository.AIFeedRepo;
import fusionIQ.AI.V2.fusionIq.repository.PersonalDetailsRepo;
import fusionIQ.AI.V2.fusionIq.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ai")
public class AiFeedController {

    @Autowired
    private PersonalDetailsService personalDetailsService;

    @Autowired
    private PersonalDetailsRepo personalDetailsRepo;

    @Autowired
    private ArticlePostService articlePostService;

    @Autowired
    private ImagePostService imagePostService;

    @Autowired
    private LongVideoService longVideoService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private AIFeedRepo aiFeedRepo;

    @Autowired
    private FollowService followService;
    @Autowired
    private AiFeedService aiFeedService;

    @GetMapping("/getUserPersonalDetails/{userId}")
    public ResponseEntity<Map<String, Object>> getUserDetails(@PathVariable Long userId) {
        Map<String, Object> details = personalDetailsService.getPersonalDetailsWithUserFields(userId);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

//    @GetMapping("/getAllArticlePosts/{userId}")
//    public ResponseEntity<List<Map<String, Object>>> getAllArticlePosts(@PathVariable Long userId) {
//        List<Map<String, Object>> articles = articlePostService.getArticlePostsWithDetails(userId);
//        return new ResponseEntity<>(articles, HttpStatus.OK);
//    }
//
//    @GetMapping("/getAllImagePosts/{userId}")
//    public ResponseEntity<List<Map<String, Object>>> getImagePostsWithDetails(@PathVariable Long userId) {
//        List<Map<String, Object>> image = imagePostService.getImagePostsWithDetails(userId);
//        return new ResponseEntity<>(image, HttpStatus.OK);
//    }

//    @GetMapping("/getAllLongVideoPosts/{userId}")
//    public ResponseEntity<List<Map<String, Object>>> getLongVideosWithDetails(@PathVariable Long userId) {
//        List<Map<String, Object>> longVideos = longVideoService.getLongVideosWithDetails(userId);
//        return new ResponseEntity<>(longVideos, HttpStatus.OK);
//    }


    @GetMapping("/getAllArticlePosts")
    public ResponseEntity<List<Map<String, Object>>> getAllArticlePostsWithDetails() {
        List<Map<String, Object>> articlePosts = articlePostService.getAllArticlePostsWithDetails();
        return new ResponseEntity<>(articlePosts, HttpStatus.OK);
    }

    @GetMapping("/getAllImagePosts")
    public ResponseEntity<List<Map<String, Object>>> getAllImagePostsWithDetails() {
        List<Map<String, Object>> imagePosts = imagePostService.getAllImagePostsWithDetails();
        return new ResponseEntity<>(imagePosts, HttpStatus.OK);
    }

    @GetMapping("/getAllLongVideoPosts")
    public ResponseEntity<List<Map<String, Object>>> getAllLongVideosWithDetails() {
        List<Map<String, Object>> longVideos = longVideoService.getAllLongVideosWithDetails();
        return new ResponseEntity<>(longVideos, HttpStatus.OK);
    }

    @GetMapping("/last-10-searches/{userId}")
    public List<Map<String, String>> getLast10SearchesByUserId(@PathVariable Long userId) {
        List<String> searchContents = searchService.getLast10SearchContentsByUserId(userId);
        return searchContents.stream()
                .map(content -> {
                    Map<String, String> searchMap = new HashMap<>();
                    searchMap.put("searchContent", content);
                    return searchMap;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/last-10-feedInteractions/{userId}")
    public List<AiFeed> getLast10FeedsWithInteraction(@PathVariable Long userId) {
        return aiFeedRepo.findTop10ByFeedInteractionTrueOrderByCreatedAtDesc(userId);
    }

    @GetMapping("/followingIds/{userId}")
    public List<Long> getFollowingIdsByUserId(@PathVariable Long userId) {
        return followService.getFollowingIdsByUserId(userId);
    }

    @GetMapping("/getSelectedDetails")
    public ResponseEntity<List<Map<String, Object>>> getSelectedPersonalDetails() {
        List<Map<String, Object>> selectedDetails = personalDetailsService.getSelectedPersonalDetails();
        return new ResponseEntity<>(selectedDetails, HttpStatus.OK);
    }

    @PostMapping("/insertRecommendation/{userId}")
    public ResponseEntity<String> createAiFeed(
            @PathVariable Long userId,
            @RequestParam String feedType,
            @RequestParam boolean feedInteraction,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt,
            @RequestParam(required = false) Long articleId,
            @RequestParam(required = false) Long imageId,
            @RequestParam(required = false) Long shortVideoId,
            @RequestParam(required = false) Long longVideoId) {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();  // Set current time if not provided
        }

        try {
            personalDetailsService.createAiFeed(userId, feedType, feedInteraction, createdAt, articleId, imageId, shortVideoId, longVideoId);
            return ResponseEntity.ok("AiFeed created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create AiFeed.");
        }
    }

    @GetMapping("/getByFeedId/{feedId}")
    public ResponseEntity<?> getAiFeedById(@PathVariable Long feedId) {
        Optional<AiFeed> aiFeedOpt = aiFeedService.getAiFeedById(feedId);
        if (aiFeedOpt.isPresent()) {
            AiFeed aiFeed = aiFeedOpt.get();
            Map<String, Object> response = aiFeedService.buildAiFeedResponse(aiFeed);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Feed not found with id: " + feedId);
        }
    }

    @GetMapping("/type/{feedType}")
    public ResponseEntity<?> getAiFeedsByFeedType(@PathVariable String feedType) {
        List<Map<String, Object>> response = aiFeedService.getAiFeedsByFeedTypeFormatted(feedType);
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No feeds found with type: " + feedType);
        }
    }



}