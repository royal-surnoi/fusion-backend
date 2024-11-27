package fusionIQ.AI.V2.fusionIq.repository;

import fusionIQ.AI.V2.fusionIq.data.MockTestInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface MockTestInterviewRepository extends JpaRepository<MockTestInterview, Long> {

    @Query("SELECT COUNT(b) FROM BookedMockTestInterview b " +
            "WHERE b.user.id = :userId " +
            "AND b.slot.mockTestInterview.id = :mockTestInterviewId")
    Long countByStudentIdAndMockTestInterviewId(@Param("userId") Long userId,
                                                @Param("mockTestInterviewId") Long mockTestInterviewId);


    @Query("SELECT m FROM MockTestInterview m WHERE m.id = :id")
    Optional<MockTestInterview> findByIdWithoutJoins(Long id);

//    @Query("SELECT  m.title, m.description, m.testType, m.fee, m.freeAttempts,m.Image FROM MockTestInterview m")
//    List<Object[]> findSelectedFields();

    @Query("SELECT m.title, m.description, m.testType, m.fee, m.freeAttempts, m.Image, m.relatedCourseId.id, m.id " +
            "FROM MockTestInterview m")
    List<Object[]> findSelectedFields();



}
