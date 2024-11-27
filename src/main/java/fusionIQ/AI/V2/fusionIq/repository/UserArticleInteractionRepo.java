package fusionIQ.AI.V2.fusionIq.repository;

import fusionIQ.AI.V2.fusionIq.data.ArticlePost;
import fusionIQ.AI.V2.fusionIq.data.UserArticleInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserArticleInteractionRepo extends JpaRepository<UserArticleInteraction,Long> {
    void deleteByArticlePost(ArticlePost articlePost);
}
