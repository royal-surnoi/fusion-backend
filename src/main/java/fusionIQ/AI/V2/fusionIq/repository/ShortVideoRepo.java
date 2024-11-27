package fusionIQ.AI.V2.fusionIq.repository;

import fusionIQ.AI.V2.fusionIq.data.ShortVideo;
import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShortVideoRepo extends JpaRepository<ShortVideo, Long> {
    List<ShortVideo> findByUserId(long userId) throws UserNotFoundException;

    void delete(ShortVideo shortVideo);
    @Query("SELECT v FROM ShortVideo v WHERE :userId MEMBER OF v.likedByUsers")
    List<ShortVideo> findByLikedByUsersContaining(@Param("userId") Long userId);

@Query("SELECT sv FROM ShortVideo sv JOIN sv.likedByUsers u WHERE sv.id = :videoId AND u = :user")
Optional<ShortVideo> findByIdAndLikedByUsersContaining(@Param("videoId") Long videoId, @Param("user") User user);

    Object findByUserIdAndId(Long userId, Long id);
}

