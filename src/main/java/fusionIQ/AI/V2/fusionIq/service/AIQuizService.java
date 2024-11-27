package fusionIQ.AI.V2.fusionIq.service;


import fusionIQ.AI.V2.fusionIq.data.*;
import fusionIQ.AI.V2.fusionIq.exception.ResourceNotFoundException;
import fusionIQ.AI.V2.fusionIq.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AIQuizService {
    @Autowired
    AIQuizRepo aiQuizRepo;

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    UserRepo userRepo;
    @Autowired
    LessonRepo lessonRepo;

    @Autowired
    AIQuizQuestionRepo aiQuizQuestionRepo;

    @Autowired
    AIQuizAnswerRepo aiQuizAnswerRepo;

    @Autowired
    NotificationRepo notificationRepo;


    public AIQuiz createAIQuiz(Long courseId, Long lessonId, Long userId,
                               String AIQuizName) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(() -> new RuntimeException("Lesson not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        AIQuiz aiQuiz = new AIQuiz();
        aiQuiz.setAIQuizName(AIQuizName);
        aiQuiz.setCreatedAt(LocalDateTime.now());
        aiQuiz.setCourse(course);
        aiQuiz.setLesson(lesson);
        aiQuiz.setUser(user);


        return aiQuizRepo.save(aiQuiz);

    }


    public void deleteQuizById(Long id) {
        if (!aiQuizRepo.existsById(id)) {
            throw new RuntimeException("AIQuiz not found");
        }
        aiQuizRepo.deleteById(id);
    }

    public List<AIQuiz> getQuizzesByUserId(Long userId) {
        return aiQuizRepo.findByUserId(userId);
    }

    public AIQuiz getQuizById(Long quizId) {
        return aiQuizRepo.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    public AIQuizQuestion saveQuizQuestion(Long quizId, AIQuizQuestion aiQuizQuestion) {
        AIQuiz aiQuiz = getQuizById(quizId); // Fetching the quiz by its ID
        aiQuizQuestion.setAiQuiz(aiQuiz);
        return aiQuizQuestionRepo.save(aiQuizQuestion);
    }

    public AIQuizAnswer saveQuizAnswer(Long userId, Long questionId, AIQuizAnswer aiQuizAnswer) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        AIQuizQuestion aiQuizQuestion = aiQuizQuestionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        AIQuiz aiQuiz = aiQuizQuestion.getAiQuiz();

        aiQuizAnswer.setUser(user);
        aiQuizAnswer.setAiQuiz(aiQuiz);
        aiQuizAnswer.setAiQuizQuestion(aiQuizQuestion);

        return aiQuizAnswerRepo.save(aiQuizAnswer);
    }

    public List<AIQuiz> findByUserAndLesson(long userId, long lessonId) {
        return aiQuizRepo.findByUserIdAndLessonId(userId, lessonId);
    }

//    @Transactional
//    public void submitQuizAnswers(Long aiQuizId, Long userId, List<AIQuizAnswer> answers) {
//        AIQuiz aiQuiz = aiQuizRepo.findById(aiQuizId)
//                .orElseThrow(() -> new RuntimeException("AIQuiz not found"));
//
//        User user = userRepo.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        for (AIQuizAnswer answer : answers) {
//            AIQuizQuestion question = aiQuizQuestionRepo.findById(answer.getAiQuizQuestion().getId())
//                    .orElseThrow(() -> new RuntimeException("AIQuizQuestion not found"));
//
//            answer.setAiQuiz(aiQuiz);
//            answer.setUser(user);
//            answer.setAiQuizQuestion(question);
//            answer.setCorrectAnswer(answer.getAIQuizUserAnswer().equals(question.getAIQuizCorrectAnswer()));
//
//            aiQuizAnswerRepo.save(answer);
//        }
//    }

    @Transactional
    public void submitQuizAnswers(Long aiQuizId, Long userId, List<AIQuizAnswer> answers) {
        if (isQuizAlreadySubmitted(aiQuizId, userId)) {
            throw new RuntimeException("This aiQuiz has already been submitted by the user.");
        }

        AIQuiz aiQuiz = aiQuizRepo.findById(aiQuizId)
                .orElseThrow(() -> new RuntimeException("aiQuiz not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        for (AIQuizAnswer answer : answers) {
            AIQuizQuestion question = aiQuizQuestionRepo.findById(answer.getAiQuizQuestion().getId())
                    .orElseThrow(() -> new RuntimeException("aiQuizQuestion not found"));

            answer.setAiQuiz(aiQuiz);
            answer.setUser(user);
            answer.setAiQuizQuestion(question);
            answer.setCorrectAnswer(answer.getAIQuizUserAnswer().equals(question.getAIQuizCorrectAnswer()));

            aiQuizAnswerRepo.save(answer);
        }
    }

    private boolean isQuizAlreadySubmitted(Long quizId, Long userId) {
        return aiQuizAnswerRepo.existsByAiQuizIdAndUserId(quizId, userId);
    }
    public Map<String, Object> getQuizResult(Long aiQuizId, Long userId) {
        AIQuiz aiQuiz = aiQuizRepo.findById(aiQuizId)
                .orElseThrow(() -> new RuntimeException("AIQuiz not found"));

        List<AIQuizAnswer> answers = aiQuizAnswerRepo.findByAiQuizIdAndUserId(aiQuizId, userId);

        int totalQuestions = answers.size();
        int correctAnswers = (int) answers.stream().filter(AIQuizAnswer::isCorrectAnswer).count();
        double percentage = (double) correctAnswers / totalQuestions * 100;
        String ratio = correctAnswers + "/" + totalQuestions;

        Map<String, Object> result = new HashMap<>();
        result.put("aiQuizId", aiQuizId);
        result.put("userId", userId);
        result.put("totalQuestions", totalQuestions);
        result.put("correctAnswers", correctAnswers);
        result.put("percentage", percentage);
        result.put("ratio", ratio);

        return result;
    }
}

