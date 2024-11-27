package fusionIQ.AI.V2.fusionIq.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import fusionIQ.AI.V2.fusionIq.data.*;
import fusionIQ.AI.V2.fusionIq.exception.UnauthorizedException;
import fusionIQ.AI.V2.fusionIq.exception.UserNotFoundException;
import fusionIQ.AI.V2.fusionIq.repository.*;
import jakarta.transaction.Transactional;
import fusionIQ.AI.V2.fusionIq.repository.SavedItemsRepo;
import fusionIQ.AI.V2.fusionIq.repository.VideoCommentRepo;
import fusionIQ.AI.V2.fusionIq.repository.ShortVideoRepo;
import fusionIQ.AI.V2.fusionIq.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShortVideoService {

    @Autowired
    private ShortVideoRepo shortVideoRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AmazonS3 amazonS3;
    @Autowired
    private VideoCommentRepo videoCommentRepo;
    @Autowired
    private SavedItemsRepo savedItemsRepo;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NestedCommentRepo nestedCommentRepo;
    @Autowired
    private CommentRepo commentRepo;

    private final String bucketName = "fusion-chat";
    private final String folderName = "ShortVideos/";

    public ShortVideo uploadShortVideo(MultipartFile file, Long userId, String shortVideoDescription, String tag) throws IOException {
        String key = folderName + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        File convertedFile = convertMultiPartToFile(file);

        amazonS3.putObject(new PutObjectRequest(bucketName, key, convertedFile));
        String s3Url = amazonS3.getUrl(bucketName, key).toString();
        String duration = getVideoDuration(convertedFile);

        convertedFile.delete();

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

        ShortVideo shortVideo = new ShortVideo();
        shortVideo.setShortVideoTitle(file.getOriginalFilename());
        shortVideo.setS3Key(key);
        shortVideo.setS3Url(s3Url);
        shortVideo.setUser(user);
        shortVideo.setShortVideoDuration(duration);
        if (shortVideoDescription != null) shortVideo.setShortVideoDescription(shortVideoDescription);
        if (tag != null) shortVideo.setTag(tag);

        return shortVideoRepo.save(shortVideo);
    }


    private String getVideoDuration(File videoFile) {
        try {
            MultimediaObject multimediaObject = new MultimediaObject(videoFile);
            MultimediaInfo info = multimediaObject.getInfo();
            long durationInMillis = info.getDuration();

            long hours = (durationInMillis / 3600000);
            long minutes = (durationInMillis % 3600000) / 60000;
            long seconds = (durationInMillis % 60000) / 1000;

            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } catch (InputFormatException e) {
            throw new RuntimeException("Unsupported video format: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error while reading video file: " + e.getMessage());
        }
    }

    public List<ShortVideo> getAllShortVideos() {
        return shortVideoRepo.findAll();
    }

    public Optional<ShortVideo> getShortVideo(Long id) {
        return shortVideoRepo.findById(id);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }


    @Transactional
    public void likeVideo(Long videoId, Long userId) {
        ShortVideo shortVideo = shortVideoRepo.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found with id " + videoId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        if (shortVideo.getLikedByUsers().contains(user)) {
            shortVideo.getLikedByUsers().remove(user);
            shortVideo.getLikeTimestamps().remove(userId);
            shortVideo.setShortVideoLikes(shortVideo.getShortVideoLikes() - 1);
        } else {
            shortVideo.getLikedByUsers().add(user);
            shortVideo.getLikeTimestamps().put(userId, LocalDateTime.now());
            shortVideo.setShortVideoLikes(shortVideo.getShortVideoLikes() + 1);

            notificationService.createLikePostNotification(userId, videoId, "short_video"); // Trigger like notification
        }

        shortVideoRepo.save(shortVideo);
    }

    public void shareVideo(Long videoId) {
        ShortVideo shortVideo = shortVideoRepo.findById(videoId)
                .orElseThrow(() -> new UserNotFoundException("Video not found with id " + videoId));
        shortVideo.setShortVideoShares(shortVideo.getShortVideoShares() + 1);
        shortVideoRepo.save(shortVideo);
    }

    public void incrementViewCount(Long videoId) {
        ShortVideo shortVideo = shortVideoRepo.findById(videoId)
                .orElseThrow(() -> new UserNotFoundException("Video not found with id " + videoId));
        shortVideo.setShortVideoViews(shortVideo.getShortVideoViews() + 1);
        shortVideoRepo.save(shortVideo);
    }

    public VideoComment addComment(Long videoId, Long userId, String content) {
        ShortVideo shortVideo = shortVideoRepo.findById(videoId)
                .orElseThrow(() -> new UserNotFoundException("Video not found with id " + videoId));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        VideoComment videoComment = new VideoComment();
        videoComment.setVideoCommentContent(content);
        videoComment.setShortVideo(shortVideo);
        videoComment.setUser(user);
        videoComment.setCreatedAt(LocalDateTime.now());
        VideoComment savedComment = videoCommentRepo.save(videoComment);
        notificationService.createCommentNotification(userId, videoId, content,"short_video"); // Trigger comment notification
        return savedComment;
    }

    public List<VideoComment> getComments(Long videoId) {
        ShortVideo shortVideo = shortVideoRepo.findById(videoId)
                .orElseThrow(() -> new UserNotFoundException("Video not found with id " + videoId));
        return shortVideo.getVideoComments();
    }


    public int getLikeCount(Long videoId) {
        ShortVideo shortVideo = shortVideoRepo.findById(videoId).orElseThrow(() -> new UserNotFoundException("Video not found"));
        return shortVideo.getShortVideoLikes();
    }

    public int getShareCount(Long videoId) {
        ShortVideo shortVideo = shortVideoRepo.findById(videoId).orElseThrow(() -> new UserNotFoundException("Video not found"));
        return shortVideo.getShortVideoShares();
    }

    public int getCommentCount(Long videoId) {
        return videoCommentRepo.countByShortVideoId(videoId);
    }
    public int getViewCount(Long videoId) {
        ShortVideo shortVideo = shortVideoRepo.findById(videoId).orElseThrow(() -> new UserNotFoundException("Video not found"));
        return shortVideo.getShortVideoViews();
    }



    public NestedComment addNestedComment(Long parentCommentId, Long userId, String content) {
        VideoComment parentComment = videoCommentRepo.findById(parentCommentId)
                .orElseThrow(() -> new UserNotFoundException("Parent comment not found with id " + parentCommentId));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

        NestedComment nestedComment = new NestedComment();
        nestedComment.setParentComment(parentComment);
        nestedComment.setUser(user);
        nestedComment.setContent(content);
        nestedComment.setCreatedAt(LocalDateTime.now());
        return nestedCommentRepo.save(nestedComment);
    }

    public List<NestedComment> getNestedComments(Long parentCommentId) {
        VideoComment parentComment = videoCommentRepo.findById(parentCommentId)
                .orElseThrow(() -> new UserNotFoundException("Parent comment not found with id " + parentCommentId));
        return nestedCommentRepo.findByParentComment(parentComment);
    }

    public List<ShortVideo> getShortVideosByUserId(long userId) throws UserNotFoundException {
        List<ShortVideo> shortVideos = shortVideoRepo.findByUserId(userId);
        if (shortVideos == null) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        return shortVideos;
    }

    public ShortVideo updateShortVideoDescription(Long id, String shortVideoDescription,String tag) throws UserNotFoundException {
        Optional<ShortVideo> optionalShortVideo = shortVideoRepo.findById(id);
        if (!optionalShortVideo.isPresent()) {
            throw new UserNotFoundException("Short video not found");
        }
        ShortVideo shortVideo = optionalShortVideo.get();
        if (shortVideoDescription != null) shortVideo.setShortVideoDescription(shortVideoDescription);
        if (tag != null) shortVideo.setTag(tag);
        shortVideo.setUpdatedDate(LocalDateTime.now());
        return shortVideoRepo.save(shortVideo);
    }

    @Transactional
    public void deleteShortVideo(long id) {
        ShortVideo shortVideo = getShortVideoById(id);
        if (shortVideo != null) {
            videoCommentRepo.deleteByShortVideo(shortVideo);
            savedItemsRepo.deleteByShortVideo(shortVideo);
            shortVideoRepo.delete(shortVideo);
        } else {
            throw new RuntimeException("ShortVideo not found");
        }
    }

    private ShortVideo getShortVideoById(long id) {
        return shortVideoRepo.findById(id).orElseThrow(() -> new RuntimeException("Short Video not found"));

    }


    public VideoComment likeComment(Long videoId, Long commentId, Long userId) throws UserNotFoundException {
        ShortVideo video = getShortVideo(videoId).orElseThrow(() -> new UserNotFoundException("Video not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        VideoComment comment = videoCommentRepo.findById(commentId)
                .orElseThrow(() -> new UserNotFoundException("Comment not found"));

        if (video.getVideoComments().contains(comment)) {
            comment.incrementLikes();
            return videoCommentRepo.save(comment);
        } else {
            throw new UserNotFoundException("Comment does not belong to this video");
        }
    }

    public int getCommentLikes(Long videoId, Long commentId) throws UserNotFoundException {
        ShortVideo video = getShortVideo(videoId).orElseThrow(() -> new UserNotFoundException("Video not found"));
        VideoComment comment = videoCommentRepo.findById(commentId)
                .orElseThrow(() -> new UserNotFoundException("Comment not found"));

        if (video.getVideoComments().contains(comment)) {
            return comment.getLikes();
        } else {
            throw new UserNotFoundException("Comment does not belong to this video");
        }
    }
    public boolean isVideoLikedByUser(Long videoId, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        Optional<ShortVideo> video = shortVideoRepo.findByIdAndLikedByUsersContaining(videoId, user);
        return video.isPresent();
    }
    public List<ShortVideo> getLikedVideosByUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        return shortVideoRepo.findByLikedByUsersContaining(userId);
    }

    @Transactional
    public NestedComment likeNestedComment(Long nestedCommentId, Long userId) {
        NestedComment nestedComment = nestedCommentRepo.findById(nestedCommentId)
                .orElseThrow(() -> new RuntimeException("Nested comment not found with id " + nestedCommentId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        if (nestedComment.getLikedUserIds().contains(user)) {
            nestedComment.getLikedUserIds().remove(user);
            nestedComment.setLikes(nestedComment.getLikes() - 1);
        } else {
            nestedComment.getLikedUserIds().add(userId);
            nestedComment.setLikes(nestedComment.getLikes() + 1);

            // Trigger a notification or any other logic here if needed
        }

        return nestedCommentRepo.save(nestedComment);
    }

    public int getNestedCommentLikes(Long nestedCommentId) {
        NestedComment nestedComment = nestedCommentRepo.findById(nestedCommentId)
                .orElseThrow(() -> new RuntimeException("Nested comment not found with id " + nestedCommentId));
        return nestedComment.getLikes();
    }


    @Transactional
    public void deleteNestedComment(Long nestedCommentId) {
        NestedComment nestedComment = nestedCommentRepo.findById(nestedCommentId)
                .orElseThrow(() -> new RuntimeException("Nested comment not found with id " + nestedCommentId));
        nestedCommentRepo.delete(nestedComment);
    }
    @Transactional
    public void deleteComment(Long videoId, Long commentId, Long userId) throws UnauthorizedException {
        ShortVideo shortVideo = shortVideoRepo.findById(videoId)
                .orElseThrow(() -> new UserNotFoundException("Video not found with id " + videoId));

        VideoComment comment = videoCommentRepo.findById(commentId)
                .orElseThrow(() -> new UserNotFoundException("Comment not found with id " + commentId));

        // Check if the user is either the video owner or the comment owner
        if (!(shortVideo.getUser().getId() == (userId)) && !(comment.getUser().getId() == (userId))) {
            throw new UnauthorizedException("User is not authorized to delete this comment");
        }

        // Delete the comment and its nested comments
        deleteNestedComments(commentId);
        videoCommentRepo.delete(comment);
    }

    private void deleteNestedComments(Long parentCommentId) {
        List<VideoComment> nestedComments = videoCommentRepo.findByParentCommentId(parentCommentId);
        for (VideoComment nestedComment : nestedComments) {
            deleteNestedComments(nestedComment.getId());
            videoCommentRepo.delete(nestedComment);
        }
    }

    @Transactional
    public VideoComment editComment(Long videoId, Long commentId, Long userId, String newContent) throws UnauthorizedException {
        ShortVideo longVideo = shortVideoRepo.findById(videoId)
                .orElseThrow(() -> new UserNotFoundException("Video not found with id " + videoId));

        VideoComment comment = videoCommentRepo.findById(commentId)
                .orElseThrow(() -> new UserNotFoundException("Comment not found with id " + commentId));

        if (!(comment.getUser().getId() == (userId))) {
            throw new UnauthorizedException("You are not authorized to edit this comment.");
        }

        comment.setVideoCommentContent(newContent);
        comment.setUpdatedAt(LocalDateTime.now()); // Update the timestamp if necessary

        return videoCommentRepo.save(comment);
    }

    @Transactional
    public NestedComment editNestedComment(Long nestedCommentId, Long userId, String newContent) throws UnauthorizedException {
        NestedComment nestedComment = nestedCommentRepo.findById(nestedCommentId)
                .orElseThrow(() -> new RuntimeException("Nested comment not found with id " + nestedCommentId));

        if (!(nestedComment.getUser().getId() == (userId))) {
            throw new UnauthorizedException("You are not authorized to edit this nested comment.");
        }

        nestedComment.setContent(newContent);
        nestedComment.setUpdatedAt(LocalDateTime.now()); // Update the timestamp if necessary

        return nestedCommentRepo.save(nestedComment);
    }
    @Cacheable(value = "shortVideoDetailsCache", key = "#userId + '-' + #shortVideoId")
    public Object getShortVideoDetails(Long userId, Long shortVideoId) {
        return shortVideoRepo.findByUserIdAndId(userId, shortVideoId);
    }
}
