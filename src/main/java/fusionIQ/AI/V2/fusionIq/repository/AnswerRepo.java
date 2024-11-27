package fusionIQ.AI.V2.fusionIq.repository;

import fusionIQ.AI.V2.fusionIq.data.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepo extends JpaRepository<Answer, Long> {

    List<Answer> findByQuizIdAndUserId(Long quizId, Long userId);

    boolean existsByQuizId(Long quizId);

    int countByCourseIdAndUserId(Long courseId, Long userId);

    @Query("SELECT COUNT(DISTINCT a.quiz.id) FROM Answer a WHERE a.user.id = :userId AND a.course.id = :courseId")
    int countDistinctByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);

    boolean existsByQuizIdAndUserId(Long quizId, Long userId);


    @Query("SELECT COUNT(a) > 0 FROM Answer a WHERE a.quiz.id = :quizId AND a.student.id = :studentId")
    boolean existsByQuizIdAndStudentId(@Param("quizId") Long quizId, @Param("studentId") Long studentId);

}

