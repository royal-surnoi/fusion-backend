package fusionIQ.AI.V2.fusionIq.repository;


import fusionIQ.AI.V2.fusionIq.data.UserShortVideoInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserShortVideoInteractionRepo extends JpaRepository<UserShortVideoInteraction, Long> {

    List<UserShortVideoInteraction> findByUserId(long userId);
    List<UserShortVideoInteraction> findByShortVideoId(long shortVideoId);
}


