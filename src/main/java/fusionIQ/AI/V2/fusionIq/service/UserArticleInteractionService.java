package fusionIQ.AI.V2.fusionIq.service;

import fusionIQ.AI.V2.fusionIq.data.UserArticleInteraction;
import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.data.ArticlePost;
import fusionIQ.AI.V2.fusionIq.repository.ArticlePostRepo;
import fusionIQ.AI.V2.fusionIq.repository.UserArticleInteractionRepo;
import fusionIQ.AI.V2.fusionIq.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserArticleInteractionService {

    @Autowired
    private UserArticleInteractionRepo userArticleInteractionRepo;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ArticlePostRepo articlePostRepository;

    public UserArticleInteraction postByUserIdAndArticleId(Long userId, Long articleId, Long interaction) {
        Optional<User> user = userRepository.findById(userId);
        Optional<ArticlePost> articlePost = articlePostRepository.findById(articleId);

        if (user.isPresent() && articlePost.isPresent()) {
            UserArticleInteraction userArticleInteraction = new UserArticleInteraction();
            userArticleInteraction.setUser(user.get());
            userArticleInteraction.setArticlePost(articlePost.get());
            userArticleInteraction.setArticleInteraction(interaction);
            return userArticleInteractionRepo.save(userArticleInteraction);
        }
        return null;
    }

    public List<UserArticleInteraction> getByUserId(Long userId) {
        return userArticleInteractionRepo.findAll().stream()
                .filter(interaction -> interaction.getUser().getId() == userId)
                .toList();
    }

    public List<UserArticleInteraction> getByArticleId(Long articleId) {
        return userArticleInteractionRepo.findAll().stream()
                .filter(interaction -> interaction.getArticlePost().getId() == articleId)
                .toList();
    }

    public List<UserArticleInteraction> getAll() {
        return userArticleInteractionRepo.findAll();
    }

    public void deleteById(Long id) {
        userArticleInteractionRepo.deleteById(id);
    }
}
