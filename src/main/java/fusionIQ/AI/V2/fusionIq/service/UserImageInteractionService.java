package fusionIQ.AI.V2.fusionIq.service;


import fusionIQ.AI.V2.fusionIq.data.UserImageInteraction;
import fusionIQ.AI.V2.fusionIq.repository.UserImageInteractionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImageInteractionService {

    @Autowired
    private UserImageInteractionRepo userImageInteractionRepo;

    public UserImageInteraction createUserImageInteraction(UserImageInteraction userImageInteraction) {
        return userImageInteractionRepo.save(userImageInteraction);
    }

    public Optional<UserImageInteraction> getUserImageInteractionById(long id) {
        return userImageInteractionRepo.findById(id);
    }

    public List<UserImageInteraction> getUserImageInteractionsByUserId(long userId) {
        return userImageInteractionRepo.findByUserId(userId);
    }

    public List<UserImageInteraction> getUserImageInteractionsByImagePostId(long imagePostId) {
        return userImageInteractionRepo.findByImagePostId(imagePostId);
    }

    public List<UserImageInteraction> getAllUserImageInteractions() {
        return userImageInteractionRepo.findAll();
    }

    public void deleteUserImageInteractionById(long id) {
        userImageInteractionRepo.deleteById(id);
    }
}

