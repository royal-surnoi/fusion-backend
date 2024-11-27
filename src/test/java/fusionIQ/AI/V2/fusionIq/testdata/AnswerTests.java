package fusionIQ.AI.V2.fusionIq.testdata;

import fusionIQ.AI.V2.fusionIq.data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTests {

    private Answer answer;
    private User user;
    private Quiz quiz;
    private Question question;
    private Course course;
    private User student;

    @BeforeEach
    void setUp() {
        user = new User(); // Initialize with appropriate values or mocks
        user.setId(1L);

        quiz = new Quiz(); // Initialize with appropriate values or mocks
        quiz.setId(1L);

        question = new Question(); // Initialize with appropriate values or mocks
        question.setId(1L);

        course = new Course(); // Initialize with appropriate values or mocks
        course.setId(1L);

        student = new User(); // Initialize with appropriate values or mocks
        student.setId(2L);

        answer = new Answer();
    }

    @Test
    void testDefaultConstructor() {
        assertThat(answer.getId()).isNull();
        assertThat(answer.getUser()).isNull();
        assertThat(answer.getQuiz()).isNull();
        assertThat(answer.getQuestion()).isNull();
        assertThat(answer.getCourse()).isNull();
        assertThat(answer.getStudent()).isNull();
        assertThat(answer.getSelectedAnswer()).isNull();
        assertThat(answer.isCorrect()).isFalse();
    }

    @Test
    void testParameterizedConstructor() {
        Answer answer = new Answer(
                1L,
                user,
                quiz,
                question,
                course,
                student,
                "Selected Answer",
                true
        );

        assertThat(answer.getId()).isEqualTo(1L);
        assertThat(answer.getUser()).isEqualTo(user);
        assertThat(answer.getQuiz()).isEqualTo(quiz);
        assertThat(answer.getQuestion()).isEqualTo(question);
        assertThat(answer.getCourse()).isEqualTo(course);
        assertThat(answer.getStudent()).isEqualTo(student);
        assertThat(answer.getSelectedAnswer()).isEqualTo("Selected Answer");
        assertThat(answer.isCorrect()).isTrue();
    }

    @Test
    void testSettersAndGetters() {
        answer.setId(2L);
        answer.setUser(user);
        answer.setQuiz(quiz);
        answer.setQuestion(question);
        answer.setCourse(course);
        answer.setStudent(student);
        answer.setSelectedAnswer("Updated Answer");
        answer.setCorrect(false);

        assertThat(answer.getId()).isEqualTo(2L);
        assertThat(answer.getUser()).isEqualTo(user);
        assertThat(answer.getQuiz()).isEqualTo(quiz);
        assertThat(answer.getQuestion()).isEqualTo(question);
        assertThat(answer.getCourse()).isEqualTo(course);
        assertThat(answer.getStudent()).isEqualTo(student);
        assertThat(answer.getSelectedAnswer()).isEqualTo("Updated Answer");
        assertThat(answer.isCorrect()).isFalse();
    }

    @Test
    void testToString() {
        answer.setId(1L);
        answer.setUser(user);
        answer.setQuiz(quiz);
        answer.setQuestion(question);
        answer.setCourse(course);
        answer.setStudent(student);
        answer.setSelectedAnswer("Selected Answer");
        answer.setCorrect(true);

        String expected = "Answer{" +
                "id=" + 1L +
                ", user=" + user +
                ", quiz=" + quiz +
                ", question=" + question +
                ", course=" + course +
                ", student=" + student +
                ", selectedAnswer='Selected Answer'" +
                ", isCorrect=true" +
                '}';

        assertThat(answer.toString()).isEqualTo(expected);
    }
}
