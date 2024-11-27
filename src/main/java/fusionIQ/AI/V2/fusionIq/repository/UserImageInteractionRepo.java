package fusionIQ.AI.V2.fusionIq.repository;

import fusionIQ.AI.V2.fusionIq.data.UserImageInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserImageInteractionRepo extends JpaRepository<UserImageInteraction,Long> {
    List<UserImageInteraction> findByUserId(long userId);

    List<UserImageInteraction> findByImagePostId(long imagePostId);
}
