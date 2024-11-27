package fusionIQ.AI.V2.fusionIq.repository;

import fusionIQ.AI.V2.fusionIq.data.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MentorRepo extends JpaRepository<Mentor, Long> {
    Optional<Object> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByUserId(Long userId);

    Optional<Mentor> findByUserId(Long userId);

}
