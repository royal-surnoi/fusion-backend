package fusionIQ.AI.V2.fusionIq.repository;

import fusionIQ.AI.V2.fusionIq.data.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepo extends JpaRepository<Search,Long> {
    List<Search> findByUserId(Long userId);
    List<Search> findByUserIdAndSearchContentIsNotNull(Long userId);
    List<Search> findByUserIdAndCourseContentIsNotNull(Long userId);

    List<Search> findBySearchContentIsNotNull();

    List<Search> findByCourseContentIsNotNull();

    @Query("SELECT s.searchContent FROM Search s WHERE s.user.id = :userId ORDER BY s.id DESC")
    List<String> findTop10SearchContentByUserId(Long userId);
}
