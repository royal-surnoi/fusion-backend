//package fusionIQ.AI.V2.fusionIq.testdata;
//
//import fusionIQ.AI.V2.fusionIq.data.*;
//import org.junit.jupiter.api.Test;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//class VideoTests {
//
//    @Test
//    void testDefaultConstructor() {
//        Video video = new Video();
//
//        // Verifying that the default constructor initializes fields correctly
//        assertThat(video.getId()).isEqualTo(0L); // Default long value
//        assertThat(video.getVideoTitle()).isNull();
//        assertThat(video.getS3Key()).isNull();
//        assertThat(video.getS3Url()).isNull();
//        assertThat(video.getProgress()).isEqualTo(0.0); // Default double value
//        assertThat(video.getVideoDescription()).isNull();
//        assertThat(video.getCreatedAt()).isNotNull(); // Should be initialized to current time
//        assertThat(video.getLanguage()).isNull();
//        assertThat(video.getCourse()).isNull();
//        assertThat(video.getLesson()).isNull();
//        assertThat(video.getTranscript()).isNull();
//        assertThat(video.getLessonModule()).isNull();
//        assertThat(video.getNotes()).isNull(); // Should be null since it's not initialized
//    }
//
//    @Test
//    void testParameterizedConstructor() {
//        Course course = new Course();
//        Lesson lesson = new Lesson();
//        LessonModule lessonModule = new LessonModule();
//        List<Notes> notes = new ArrayList<>();
//        LocalDateTime createdAt = LocalDateTime.now();
//
//        Video video = new Video(1L, "Java Tutorial", "s3-key", "http://s3url.com/video", 50.0, "A comprehensive Java tutorial", createdAt, "English", course, lesson, "Transcript text", lessonModule, notes);
//
//        // Verifying that the parameterized constructor initializes fields correctly
//        assertThat(video.getId()).isEqualTo(1L);
//        assertThat(video.getVideoTitle()).isEqualTo("Java Tutorial");
//        assertThat(video.getS3Key()).isEqualTo("s3-key");
//        assertThat(video.getS3Url()).isEqualTo("http://s3url.com/video");
//        assertThat(video.getProgress()).isEqualTo(50.0);
//        assertThat(video.getVideoDescription()).isEqualTo("A comprehensive Java tutorial");
//        assertThat(video.getCreatedAt()).isEqualTo(createdAt);
//        assertThat(video.getLanguage()).isEqualTo("English");
//        assertThat(video.getCourse()).isEqualTo(course);
//        assertThat(video.getLesson()).isEqualTo(lesson);
//        assertThat(video.getTranscript()).isEqualTo("Transcript text");
//        assertThat(video.getLessonModule()).isEqualTo(lessonModule);
//        assertThat(video.getNotes()).isEqualTo(notes);
//    }
//
//    @Test
//    void testSettersAndGetters() {
//        Video video = new Video();
//        Course course = new Course();
//        Lesson lesson = new Lesson();
//        LessonModule lessonModule = new LessonModule();
//        List<Notes> notes = new ArrayList<>();
//        LocalDateTime createdAt = LocalDateTime.now();
//
//        video.setId(1L);
//        video.setVideoTitle("Spring Boot Tutorial");
//        video.setS3Key("s3-key-123");
//        video.setS3Url("http://s3url.com/springboot");
//        video.setProgress(75.0);
//        video.setVideoDescription("Spring Boot tutorial for beginners");
//        video.setCreatedAt(createdAt);
//        video.setLanguage("French");
//        video.setCourse(course);
//        video.setLesson(lesson);
//        video.setTranscript("Spring Boot transcript");
//        video.setLessonModule(lessonModule);
//        video.setNotes(notes);
//
//        assertThat(video.getId()).isEqualTo(1L);
//        assertThat(video.getVideoTitle()).isEqualTo("Spring Boot Tutorial");
//        assertThat(video.getS3Key()).isEqualTo("s3-key-123");
//        assertThat(video.getS3Url()).isEqualTo("http://s3url.com/springboot");
//        assertThat(video.getProgress()).isEqualTo(75.0);
//        assertThat(video.getVideoDescription()).isEqualTo("Spring Boot tutorial for beginners");
//        assertThat(video.getCreatedAt()).isEqualTo(createdAt);
//        assertThat(video.getLanguage()).isEqualTo("French");
//        assertThat(video.getCourse()).isEqualTo(course);
//        assertThat(video.getLesson()).isEqualTo(lesson);
//        assertThat(video.getTranscript()).isEqualTo("Spring Boot transcript");
//        assertThat(video.getLessonModule()).isEqualTo(lessonModule);
//        assertThat(video.getNotes()).isEqualTo(notes);
//    }
//
//    @Test
//    void testToString() {
//        Course course = new Course();
//        Lesson lesson = new Lesson();
//        LessonModule lessonModule = new LessonModule();
//        List<Notes> notes = new ArrayList<>();
//        LocalDateTime createdAt = LocalDateTime.now();
//
//        Video video = new Video(1L, "Spring Framework", "s3-key-framework", "http://s3url.com/framework", 80.0, "Spring Framework tutorial", createdAt, "Spanish", course, lesson, "Framework transcript", lessonModule, notes);
//
//        String expectedToString = "Video{" +
//                "id=" + video.getId() +
//                ", videoTitle='" + video.getVideoTitle() + '\'' +
//                ", s3Key='" + video.getS3Key() + '\'' +
//                ", s3Url='" + video.getS3Url() + '\'' +
//                ", progress=" + video.getProgress() +
//                ", videoDescription='" + video.getVideoDescription() + '\'' +
//                ", createdAt=" + video.getCreatedAt() +
//                ", language='" + video.getLanguage() + '\'' +
//                ", course=" + course +
//                ", lesson=" + lesson +
//                ", transcript='" + video.getTranscript() + '\'' +
//                ", lessonModule=" + lessonModule +
//                ", notes=" + notes +
//                '}';
//
//        assertThat(video.toString()).isEqualTo(expectedToString);
//    }
//}
