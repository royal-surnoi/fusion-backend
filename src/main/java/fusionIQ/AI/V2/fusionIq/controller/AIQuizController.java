package fusionIQ.AI.V2.fusionIq.controller;

import fusionIQ.AI.V2.fusionIq.data.AIQuiz;
import fusionIQ.AI.V2.fusionIq.data.AIQuizAnswer;
import fusionIQ.AI.V2.fusionIq.data.AIQuizQuestion;
import fusionIQ.AI.V2.fusionIq.data.User;
import fusionIQ.AI.V2.fusionIq.exception.ResourceNotFoundException;
import fusionIQ.AI.V2.fusionIq.repository.AIQuizAnswerRepo;
import fusionIQ.AI.V2.fusionIq.repository.AIQuizQuestionRepo;
import fusionIQ.AI.V2.fusionIq.repository.AIQuizRepo;
import fusionIQ.AI.V2.fusionIq.repository.UserRepo;
import fusionIQ.AI.V2.fusionIq.service.AIQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class AIQuizController {

    @Autowired
    AIQuizRepo aiQuizRepo;

    @Autowired
    AIQuizService aiQuizService;

    @Autowired
    AIQuizQuestionRepo aiQuizQuestionRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AIQuizAnswerRepo aiQuizAnswerRepo;

    @PostMapping("/postAiQuiz/course/{courseId}/lesson/{lessonId}/user/{userId}")
    public ResponseEntity<AIQuiz> createAIQuiz(
            @PathVariable Long courseId,
            @PathVariable Long lessonId,
            @PathVariable Long userId,
           @RequestParam String AIQuizName) {

        AIQuiz savedQuiz = aiQuizService.createAIQuiz(courseId, lessonId, userId, AIQuizName);
        return ResponseEntity.ok(savedQuiz);
    }


    @DeleteMapping("AIQuiz/{id}")
    public ResponseEntity<Void> deleteQuizById(@PathVariable Long id) {
        try {
            aiQuizService.deleteQuizById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/quiz/user/{userId}")
    public ResponseEntity<List<AIQuiz>> getQuizzesByUserId(@PathVariable Long userId) {
        List<AIQuiz> quizzes = aiQuizService.getQuizzesByUserId(userId);
        return ResponseEntity.ok(quizzes);
    }
    @PostMapping("/postAiQuizQuestion/{quizId}")
    public ResponseEntity<AIQuizQuestion> createQuizQuestion(@PathVariable Long quizId, @RequestBody AIQuizQuestion aiQuizQuestion) {
        AIQuizQuestion createdQuestion = aiQuizService.saveQuizQuestion(quizId, aiQuizQuestion);
        return ResponseEntity.ok(createdQuestion);
    }

    @PostMapping("/submitAnswer/user/{userId}/question/{questionId}")
    public ResponseEntity<AIQuizAnswer> createQuizAnswer(@PathVariable Long userId, @PathVariable Long questionId, @RequestBody AIQuizAnswer aiQuizAnswer) {
        AIQuizAnswer createdAnswer = aiQuizService.saveQuizAnswer(userId, questionId, aiQuizAnswer);
        return ResponseEntity.ok(createdAnswer);
    }

    @GetMapping("/user/{userId}/lesson/{lessonId}")
    public ResponseEntity<List<AIQuiz>> getAIQuizzesByUserAndLesson(
            @PathVariable long userId,
            @PathVariable long lessonId) {
        List<AIQuiz> quizzes = aiQuizService.findByUserAndLesson(userId, lessonId);
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/aiQuiz/questions/{aiQuizId}")
    public List<AIQuizQuestion> getAIQuizQuestionsByAIQuizId(@PathVariable Long aiQuizId) {
        return aiQuizQuestionRepo.findByAiQuizId(aiQuizId);
    }

//    @PostMapping("/{aiQuizId}/{userId}/submit")
//    public ResponseEntity<?> submitQuizAnswers(
//            @PathVariable Long aiQuizId,
//            @PathVariable Long userId,
//            @RequestBody List<AIQuizAnswer> answers) {
//        aiQuizService.submitQuizAnswers(aiQuizId, userId, answers);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/{aiQuizId}/{userId}/submit")
    public ResponseEntity<?> submitQuizAnswers(
            @PathVariable Long aiQuizId,
            @PathVariable Long userId,
            @RequestBody List<AIQuizAnswer> answers) {
        try {
            aiQuizService.submitQuizAnswers(aiQuizId, userId, answers);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{aiQuizId}/{userId}/result")
    public ResponseEntity<Map<String, Object>> getQuizResult(
            @PathVariable Long aiQuizId,
            @PathVariable Long userId) {
        Map<String, Object> result = aiQuizService.getQuizResult(aiQuizId, userId);
        return ResponseEntity.ok(result);
    }
}
