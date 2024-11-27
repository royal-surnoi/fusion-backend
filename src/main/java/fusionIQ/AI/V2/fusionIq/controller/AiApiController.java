package fusionIQ.AI.V2.fusionIq.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fusionIQ.AI.V2.fusionIq.service.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AiApiController {

    @Autowired
    private LongVideoService longVideoService;
    @Autowired
    private ShortVideoService shortVideoService;
    @Autowired
    private ArticlePostService articlePostService;
    @Autowired
    private ImagePostService imagePostService;
    @Autowired
    private UserService userService;
    @Autowired
    private AiApiService apiService;
    @Autowired
    AITranscribeService aiTranscribeService;

    private ObjectMapper objectMapper = new ObjectMapper();


//    @PostMapping("/feedRecommendations")
//    public ResponseEntity<String> feedRecommendations(@RequestBody Map<String, Object> requestBody) {
//        String response = apiService.forwardRequest("/feedRecommendations", requestBody);
//        return ResponseEntity.ok(response);
//    }


    @PostMapping("/suggestFriendsRecommendations")
    public ResponseEntity<String> suggestFriends(@RequestBody Map<String, Object> requestBody) {
        String response = apiService.forwardRequest("/suggest_friends", requestBody);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/homeRecommendations")
    public ResponseEntity<String> homeRecommendations(@RequestBody Map<String, Object> requestBody) {
        String response = apiService.forwardRequest("/homerecommendations", requestBody);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/relatedCoursesRecommendations")
    public ResponseEntity<String> relatedCourses(@RequestBody Map<String, Object> requestBody) {
        String response = apiService.forwardRequest("/candidate_recommendations", requestBody);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/chatRecommendations")
    public ResponseEntity<String> chat(@RequestBody Map<String, Object> requestBody) {
        String response = apiService.forwardRequest("/chat", requestBody);
        return ResponseEntity.ok(response);
    }


//    @PostMapping("/transcribeRecommendations")
//    public ResponseEntity<String> transcribe(@RequestBody Map<String, Object> requestBody) {
//        String response = apiService.forwardRequest("/transcribe", requestBody);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/transcribeRecommendations")
    public ResponseEntity<Map<String, Object>> transcribe() {
        // Forward the request to FastAPI
        String response = aiTranscribeService.fastForwardRequest("/transcribe");

        // Construct the response map
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", "Transcription and translation completed for all videos");
        responseMap.put("transcripts", response); // Assuming `response` contains the transcripts

        return ResponseEntity.ok(responseMap);
    }


    @PostMapping("/generateQuiz")
    public ResponseEntity<String> generateQuiz(@RequestBody Map<String, Object> requestBody) {
        String response = apiService.forwardRequest("/generate_quiz", requestBody);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/generateAssignment")
    public ResponseEntity<String> generateAssignment(@RequestBody Map<String, Object> requestBody) {
        String response = apiService.forwardRequest("/generate_assignment/generate_assignment", requestBody);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitAnswer")
    public ResponseEntity<String> submitAnswer(@RequestBody Map<String, Object> requestBody) {
        String response = apiService.forwardRequest("/submit_answer/submit_answer", requestBody);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/evaluateAssignment")
    public ResponseEntity<String> evaluateAssignment(@RequestBody Map<String, Object> requestBody) {
        String response = apiService.forwardRequest("/evaluate_assignment", requestBody);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/skillRecommendations")
    public ResponseEntity<String> skillRecommendations(@RequestBody Map<String, Object> requestBody) {
        String response = apiService.forwardRequest("/skill_recommendations", requestBody);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/languageRecommendations")
    public ResponseEntity<String> languageRecommendations(@RequestBody Map<String, Object> requestBody) {
        String response = apiService.forwardRequest("/language_recommendations", requestBody);
        return ResponseEntity.ok(response);
    }

//    @Cacheable(value = "feedRecommendations", key = "#requestBody")
//    @PostMapping("/feedRecommendations")
//    public ResponseEntity<List<Map<String, Object>>> feedRecommendations(@RequestBody Map<String, Object> requestBody) throws IOException {
//        // Forward the request to the FastAPI backend and get the recommendations as a JSON string
//        String endpoint = "/feedRecommendations"; // Assuming this is your FastAPI endpoint
//        String response = apiService.forwardRequest(endpoint, requestBody);
//
//        // Convert the JSON string response to a List of Maps
//        List<Map<String, Object>> recommendedPosts = convertJsonToListOfMaps(response);
//
//        // Process each recommended post to fetch full details
//        List<Map<String, Object>> fullDetails = recommendedPosts.stream().map(post -> {
//            Long userId = Long.valueOf(post.get("user_id").toString());
//
//            // Fetch user details
//            Map<String, Object> userDetails = userService.getUserDetailsById(userId);
//
//            // Prepare the response based on the post type
//            Map<String, Object> postDetails = new HashMap<>();
//            postDetails.putAll(post); // Add all post properties
//
//            // Add user details to the response
//            postDetails.put("user", userDetails);
//
//            // Fetch detailed post data
//            switch ((String) post.get("type")) {
//                case "long_video":
//                    postDetails.put("videoDetails", longVideoService.getFullVideoDetails(userId, Long.valueOf(post.get("long_video_id").toString())));
//                    break;
//                case "image_post":
//                    postDetails.put("imageDetails", imagePostService.getFullImageDetails(userId, Long.valueOf(post.get("image_id").toString())));
//                    break;
//                case "article_post":
//                    postDetails.put("articleDetails", articlePostService.getFullArticleDetails(userId, Long.valueOf(post.get("article_id").toString())));
//                    break;
//                default:
//                    // Handle unknown type or include the post as is
//                    break;
//            }
//            return postDetails;
//        }).collect(Collectors.toList());
//
//        // Return the full details in the response
//        return ResponseEntity.ok(fullDetails);
//    }

    // Helper method to convert JSON string to List<Map<String, Object>>
//    private List<Map<String, Object>> convertJsonToListOfMaps(String json) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        // Check if the JSON is an array or object containing an array
//        JsonNode rootNode = objectMapper.readTree(json);
//        if (rootNode.isArray()) {
//            return objectMapper.convertValue(rootNode, new TypeReference<List<Map<String, Object>>>() {});
//        } else if (rootNode.isObject()) {
//            // Assuming the array is inside a field named "recommendations"
//            JsonNode arrayNode = rootNode.path("recommendations");
//            return objectMapper.convertValue(arrayNode, new TypeReference<List<Map<String, Object>>>() {});
//        }
//        throw new IOException("Unexpected JSON format");
//    }

//    @Cacheable(value = "feedShortVideos", key = "#requestBody")
//    @PostMapping("/feedShortVideos")
//    public ResponseEntity<List<Map<String, Object>>> feedShortVideos(@RequestBody Map<String, Object> requestBody) throws IOException {
//        // Forward the request to the FastAPI backend and get the recommendations as a JSON string
//        String endpoint = "/short_video_feed"; // Assuming this is your FastAPI endpoint for short videos
//        String response = apiService.forwardRequest(endpoint, requestBody);
//
//        // Convert the JSON string response to a List of Maps
//        List<Map<String, Object>> recommendedShortVideos = convertJsonToListOfMaps(response);
//
//        // Process each recommended short video to fetch full details
//        List<Map<String, Object>> fullDetails = recommendedShortVideos.stream().map(video -> {
//            Long userId = Long.valueOf(video.get("user_id").toString());
//
//            // Fetch user details
//            Map<String, Object> userDetails = userService.getUserDetailsById(userId);
//
//            // Prepare the response based on the short video
//            Map<String, Object> videoDetails = new HashMap<>();
//            videoDetails.putAll(video); // Add all video properties
//
//            // Add user details to the response
//            videoDetails.put("user", userDetails);
//
//            // Fetch detailed short video data
//            videoDetails.put("shortVideoDetails", shortVideoService.getShortVideoDetails(userId, Long.valueOf(video.get("short_video_id").toString())));
//
//            return videoDetails;
//        }).collect(Collectors.toList());
//
//        // Return the full details in the response
//        return ResponseEntity.ok(fullDetails);
//    }
//
//
//
//
//
//    public List<Map<String, Object>> convertJsonToListOfMaps(String json) throws IOException {
//        // Parse the JSON into a tree structure
//        JsonNode rootNode = objectMapper.readTree(json);
//
//        // Check if the root is an array
//        if (rootNode.isArray()) {
//            // Convert the array directly to a list of maps
//            return objectMapper.convertValue(rootNode, new TypeReference<List<Map<String, Object>>>() {});
//        } else if (rootNode.isObject()) {
//            // Traverse the object to find the first array node
//            JsonNode arrayNode = findArrayNode(rootNode);
//            if (arrayNode != null && arrayNode.isArray()) {
//                return objectMapper.convertValue(arrayNode, new TypeReference<List<Map<String, Object>>>() {});
//            } else {
//                throw new IOException("Unexpected JSON format: Expected an array within the object");
//            }
//        }
//
//        throw new IOException("Unexpected JSON format");
//    }
//
//    private JsonNode findArrayNode(JsonNode node) {
//        if (node.isArray()) {
//            return node;
//        } else if (node.isObject()) {
//            for (JsonNode child : node) {
//                JsonNode arrayNode = findArrayNode(child);
//                if (arrayNode != null && arrayNode.isArray()) {
//                    return arrayNode;
//                }
//            }
//        }
//        return null;
//    }


    @PostMapping("/mentorSuggestions")
    public ResponseEntity<String> mentorSuggestions(@RequestBody Map<String, Object> requestBody) {
        String response = apiService.forwardRequest("/mentor_suggestions", requestBody);
        return ResponseEntity.ok(response);
    }


    @Cacheable(value = "feedRecommendations", key = "#requestBody")
    @PostMapping("/feedRecommendations")
    public ResponseEntity<List<Map<String, Object>>> feedRecommendations(@RequestBody Map<String, Object> requestBody) {
        try {
            String endpoint = "/feedRecommendations";
            String response = apiService.forwardRequest(endpoint, requestBody);
            List<Map<String, Object>> recommendedPosts = convertJsonToListOfMaps(response);
            List<Map<String, Object>> fullDetails = recommendedPosts.stream()
                    .map(this::processRecommendedPost)
                    .filter(post -> post != null)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(fullDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @Cacheable(value = "feedShortVideos", key = "#requestBody")
    @PostMapping("/feedShortVideos")
    public ResponseEntity<List<Map<String, Object>>> feedShortVideos(@RequestBody Map<String, Object> requestBody) {
        try {
            String endpoint = "/short_video_feed";
            String response = apiService.forwardRequest(endpoint, requestBody);
            List<Map<String, Object>> recommendedShortVideos = convertJsonToListOfMaps(response);
            List<Map<String, Object>> fullDetails = recommendedShortVideos.stream()
                    .map(this::processShortVideo)
                    .filter(video -> video != null)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(fullDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    private Map<String, Object> processRecommendedPost(Map<String, Object> post) {
        try {
            Long userId = getLongValue(post, "user_id");
            if (userId == null) return null;
            Map<String, Object> userDetails = userService.getUserDetailsById(userId);
            Map<String, Object> postDetails = new HashMap<>(post);
            postDetails.put("user", userDetails);
            String type = (String) post.get("type");
            if (type != null) {
                switch (type) {
                    case "long_video":
                        Long longVideoId = getLongValue(post, "long_video_id");
                        if (longVideoId != null) {
                            postDetails.put("videoDetails", longVideoService.getFullVideoDetails(userId, longVideoId));
                        }
                        break;
                    case "image_post":
                        Long imageId = getLongValue(post, "image_id");
                        if (imageId != null) {
                            postDetails.put("imageDetails", imagePostService.getFullImageDetails(userId, imageId));
                        }
                        break;
                    case "article_post":
                        Long articleId = getLongValue(post, "article_id");
                        if (articleId != null) {
                            postDetails.put("articleDetails", articlePostService.getFullArticleDetails(userId, articleId));
                        }
                        break;
                }
            }
            return postDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private Map<String, Object> processShortVideo(Map<String, Object> video) {
        try {
            Long userId = getLongValue(video, "user_id");
            Long shortVideoId = getLongValue(video, "short_video_id");
            if (userId == null || shortVideoId == null) return null;
            Map<String, Object> userDetails = userService.getUserDetailsById(userId);
            Map<String, Object> videoDetails = new HashMap<>(video);
            videoDetails.put("user", userDetails);
            videoDetails.put("shortVideoDetails", shortVideoService.getShortVideoDetails(userId, shortVideoId));
            return videoDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private Long getLongValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        try {
            return Long.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private List<Map<String, Object>> convertJsonToListOfMaps(String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        if (rootNode.isArray()) {
            return objectMapper.convertValue(rootNode, new TypeReference<List<Map<String, Object>>>() {});
        } else if (rootNode.isObject()) {
            JsonNode arrayNode = findArrayNode(rootNode);
            if (arrayNode != null && arrayNode.isArray()) {
                return objectMapper.convertValue(arrayNode, new TypeReference<List<Map<String, Object>>>() {});
            }
        }
        return new ArrayList<>();
    }
    private JsonNode findArrayNode(JsonNode node) {
        if (node.isArray()) {
            return node;
        } else if (node.isObject()) {
            for (JsonNode child : node) {
                JsonNode arrayNode = findArrayNode(child);
                if (arrayNode != null && arrayNode.isArray()) {
                    return arrayNode;
                }
            }
        }
        return null;
    }

    @PostMapping("/home_edu_recommendations")
    public ResponseEntity<String> recommend_courses(@RequestBody Map<String, Object> requestBody) {
        String response = apiService.forwardRequest("/recommend_courses", requestBody);
        return ResponseEntity.ok(response);
    }

//@Cacheable(value = "feedRecommendations", key = "#requestBody")
//    @PostMapping("/feedRecommendations")
//    public ResponseEntity<Page<Map<String, Object>>> feedRecommendations(
//            @RequestBody Map<String, Object> requestBody,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        try {
//            String endpoint = "/feedRecommendations";
//            String response = apiService.forwardRequest(endpoint, requestBody);
//            List<Map<String, Object>> recommendedPosts = convertJsonToListOfMaps(response);
//
//            // If the feed is empty, you might want to return a default set or an empty page
//            if (recommendedPosts.isEmpty()) {
//                System.out.println("No recommendations found, returning empty page.");
//                return ResponseEntity.ok(Page.empty(PageRequest.of(page, size)));
//            }
//
//            List<Map<String, Object>> fullDetails = recommendedPosts.stream()
//                    .map(this::processRecommendedPost)
//                    .filter(post -> post != null)
//                    .collect(Collectors.toList());
//
//            Pageable pageable = PageRequest.of(page, size);
//            return ResponseEntity.ok(toPage(fullDetails, pageable));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//
//    @Cacheable(value = "feedShortVideos", key = "#requestBody")
//    @PostMapping("/feedShortVideos")
//    public ResponseEntity<Page<Map<String, Object>>> feedShortVideos(
//            @RequestBody Map<String, Object> requestBody,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        try {
//            String endpoint = "/short_video_feed";
//            String response = apiService.forwardRequest(endpoint, requestBody);
//
//            // Debugging: Log the response from the API
//            System.out.println("Feed Short Videos Response: " + response);
//
//            List<Map<String, Object>> recommendedShortVideos = convertJsonToListOfMaps(response);
//
//            // Debugging: Log the size of the recommended short videos before processing
//            System.out.println("Number of Recommended Short Videos: " + recommendedShortVideos.size());
//
//            List<Map<String, Object>> fullDetails = recommendedShortVideos.stream()
//                    .map(this::processShortVideo)
//                    .filter(video -> video != null)
//                    .collect(Collectors.toList());
//
//            // Debugging: Log the size after processing
//            System.out.println("Number of Processed Short Videos: " + fullDetails.size());
//
//            Pageable pageable = PageRequest.of(page, size);
//            return ResponseEntity.ok(toPage(fullDetails, pageable));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//    private Page<Map<String, Object>> toPage(List<Map<String, Object>> list, Pageable pageable) {
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), list.size());
//        List<Map<String, Object>> sublist = list.subList(start, end);
//        return new PageImpl<>(sublist, pageable, list.size());
//    }
//
//    private Map<String, Object> processRecommendedPost(Map<String, Object> post) {
//        try {
//            Long userId = getLongValue(post, "user_id");
//            if (userId == null) return null;
//
//            Map<String, Object> userDetails = userService.getUserDetailsById(userId);
//            Map<String, Object> postDetails = new HashMap<>(post);
//            postDetails.put("user", userDetails);
//
//            String type = (String) post.get("type");
//            if (type != null) {
//                switch (type) {
//                    case "long_video":
//                        Long longVideoId = getLongValue(post, "id");
//                        if (longVideoId != null) {
//                            postDetails.put("videoDetails", longVideoService.getFullVideoDetails(userId, longVideoId));
//                        }
//                        break;
//                    case "image_post":
//                        Long imageId = getLongValue(post, "image_id");
//                        if (imageId != null) {
//                            postDetails.put("imageDetails", imagePostService.getFullImageDetails(userId, imageId));
//                        }
//                        break;
//                    case "article_post":
//                        Long articleId = getLongValue(post, "article_id");
//                        if (articleId != null) {
//                            postDetails.put("articleDetails", articlePostService.getFullArticleDetails(userId, articleId));
//                        }
//                        break;
//                    default:
//                        return null;
//                }
//            }
//            return postDetails;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private Map<String, Object> processShortVideo(Map<String, Object> video) {
//        try {
//            Long userId = getLongValue(video, "user_id");
//            Long shortVideoId = getLongValue(video, "id");
//            if (userId == null || shortVideoId == null) return null;
//
//            Map<String, Object> userDetails = userService.getUserDetailsById(userId);
//            Map<String, Object> videoDetails = new HashMap<>(video);
//            videoDetails.put("user", userDetails);
//            videoDetails.put("shortVideoDetails", shortVideoService.getShortVideoDetails(userId, shortVideoId));
//
//            return videoDetails;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private Long getLongValue(Map<String, Object> map, String key) {
//        Object value = map.get(key);
//        if (value == null) return null;
//        if (value instanceof Number) {
//            return ((Number) value).longValue();
//        }
//        try {
//            return Long.valueOf(value.toString());
//        } catch (NumberFormatException e) {
//            return null;
//        }
//    }
//
//    private List<Map<String, Object>> convertJsonToListOfMaps(String json) throws IOException {
//        JsonNode rootNode = objectMapper.readTree(json);
//        if (rootNode.isArray()) {
//            return objectMapper.convertValue(rootNode, new TypeReference<List<Map<String, Object>>>() {});
//        } else if (rootNode.isObject()) {
//            JsonNode arrayNode = findArrayNode(rootNode);
//            if (arrayNode != null && arrayNode.isArray()) {
//                return objectMapper.convertValue(arrayNode, new TypeReference<List<Map<String, Object>>>() {});
//            }
//        }
//        return new ArrayList<>();
//    }
//
//    private JsonNode findArrayNode(JsonNode node) {
//        if (node.isArray()) {
//            return node;
//        } else if (node.isObject()) {
//            for (JsonNode child : node) {
//                JsonNode arrayNode = findArrayNode(child);
//                if (arrayNode != null && arrayNode.isArray()) {
//                    return arrayNode;
//                }
//            }
//        }
//        return null;
//    }


}
