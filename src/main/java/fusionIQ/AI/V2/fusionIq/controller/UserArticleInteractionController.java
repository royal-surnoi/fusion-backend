package fusionIQ.AI.V2.fusionIq.controller;

import fusionIQ.AI.V2.fusionIq.data.UserArticleInteraction;
import fusionIQ.AI.V2.fusionIq.service.UserArticleInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userArticleInteractions")
public class UserArticleInteractionController {

    @Autowired
    private UserArticleInteractionService userArticleInteractionService;

    @PostMapping("/post/{userId}/{articleId}")
    public UserArticleInteraction postByUserIdAndArticleId(
            @PathVariable Long userId,
            @PathVariable Long articleId,
            @RequestParam Long articleInteraction) {
        return userArticleInteractionService.postByUserIdAndArticleId(userId, articleId, articleInteraction);
    }


    @GetMapping("/user/{userId}")
    public List<UserArticleInteraction> getByUserId(@PathVariable Long userId) {
        return userArticleInteractionService.getByUserId(userId);
    }

    @GetMapping("/article/{articleId}")
    public List<UserArticleInteraction> getByArticleId(@PathVariable Long articleId) {
        return userArticleInteractionService.getByArticleId(articleId);
    }

    @GetMapping("/all")
    public List<UserArticleInteraction> getAll() {
        return userArticleInteractionService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        userArticleInteractionService.deleteById(id);
    }
}

