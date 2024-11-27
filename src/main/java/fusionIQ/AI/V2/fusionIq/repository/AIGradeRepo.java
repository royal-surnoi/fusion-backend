package fusionIQ.AI.V2.fusionIq.repository;

import fusionIQ.AI.V2.fusionIq.data.AIGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIGradeRepo extends JpaRepository<AIGrade,Long> {

    @Query("SELECT g FROM AIGrade g WHERE g.aiAssignment.user.id = :userId")
    List<AIGrade> findByUserId(@Param("userId") Long userId);
}
