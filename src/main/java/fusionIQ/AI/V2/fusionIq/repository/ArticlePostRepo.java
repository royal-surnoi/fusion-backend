package fusionIQ.AI.V2.fusionIq.repository;

import fusionIQ.AI.V2.fusionIq.data.ArticlePost;
import fusionIQ.AI.V2.fusionIq.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticlePostRepo extends JpaRepository<ArticlePost, Long> {
    List<ArticlePost> findByUserOrderByPostDateDesc(User user);

    @Query("SELECT ap FROM ArticlePost ap ORDER BY ap.postDate DESC")
    List<ArticlePost> findAllOrderByPostDateDesc();

    ArticlePost findByUserIdAndId(Long userId, Long id);
    @Query("SELECT ap, pd, u " +
            "FROM ArticlePost ap " +
            "JOIN ap.user u " +
            "JOIN PersonalDetails pd ON pd.user.id = u.id " +
            "WHERE u.id = :userId")
    List<Object[]> findArticlePostsWithPersonalDetailsAndUser(@Param("userId") Long userId);

    @Query("SELECT ap, pd, u FROM ArticlePost ap " +
            "JOIN User u ON ap.user.id = u.id " +
            "LEFT JOIN PersonalDetails pd ON u.id = pd.user.id")
    List<Object[]> findAllArticlePostsWithPersonalDetails();

}

