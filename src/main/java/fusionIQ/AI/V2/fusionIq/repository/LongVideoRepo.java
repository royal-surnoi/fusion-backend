package fusionIQ.AI.V2.fusionIq.repository;

import fusionIQ.AI.V2.fusionIq.data.LongVideo;
import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LongVideoRepo extends JpaRepository<LongVideo, Long> {
    List<LongVideo> findByUserId(long userId) throws UserNotFoundException;

    void delete(LongVideo longVideo);


    @Query("SELECT v FROM LongVideo v WHERE :userId MEMBER OF v.likedByUsers")
    List<LongVideo> findByLikedByUsersContaining(@Param("userId") Long userId);
    @Query("SELECT lv FROM LongVideo lv JOIN lv.likedByUsers u WHERE lv.id = :videoId AND u = :user")
    Optional<LongVideo> findByIdAndLikedByUsersContaining(@Param("videoId") Long videoId, @Param("user") User user);


    LongVideo findByUserIdAndId(Long userId, Long id);

    @Query("SELECT lv, pd, u FROM LongVideo lv " +
            "JOIN User u ON lv.user.id = u.id " +
            "LEFT JOIN PersonalDetails pd ON u.id = pd.user.id " +
            "WHERE u.id = :userId")
    List<Object[]> findLongVideosWithPersonalDetailsAndUser(@Param("userId") Long userId);

    @Query("SELECT lv, pd, u FROM LongVideo lv " +
            "JOIN User u ON lv.user.id = u.id " +
            "LEFT JOIN PersonalDetails pd ON u.id = pd.user.id")
    List<Object[]> findAllLongVideosWithPersonalDetails();
}