package fusionIQ.AI.V2.fusionIq.service;


import fusionIQ.AI.V2.fusionIq.data.AIAssignment;
import fusionIQ.AI.V2.fusionIq.data.AIGrade;
import fusionIQ.AI.V2.fusionIq.data.AIQuiz;
import fusionIQ.AI.V2.fusionIq.repository.AIAssignmentRepo;
import fusionIQ.AI.V2.fusionIq.repository.AIGradeRepo;
import fusionIQ.AI.V2.fusionIq.repository.AIQuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIGradeService {

    @Autowired
    AIGradeRepo aiGradeRepo;
    @Autowired
    AIQuizRepo aiQuizRepo;
    @Autowired
    AIAssignmentRepo aiAssignmentRepo;

    public AIGrade saveAIQuizGrade(Long aiQuizId, String aiGrade, String aiFeedback) {
        AIQuiz aiQuiz = aiQuizRepo.findById(aiQuizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        AIGrade newAIGrade = new AIGrade();
        newAIGrade.setAIGrade(aiGrade);
        newAIGrade.setAIFeedback(aiFeedback);
        newAIGrade.setAiQuiz(aiQuiz);

        return aiGradeRepo.save(newAIGrade);
    }

    public AIGrade saveAIAssignmentGrade(Long assignmentId, String aiGrade, String aiFeedback) {
        AIAssignment aiAssignment = aiAssignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        AIGrade newAIGrade = new AIGrade();
        newAIGrade.setAIGrade(aiGrade);
        newAIGrade.setAIFeedback(aiFeedback);
        newAIGrade.setAiAssignment(aiAssignment);

        return aiGradeRepo.save(newAIGrade);
    }
    public List<AIGrade> getGradesByUserId(Long userId) {
        return aiGradeRepo.findByUserId(userId);
    }

    public List<AIGrade> getAllGrades() {
        return aiGradeRepo.findAll();
    }
    public void deleteGradeById(Long id) {
        if (aiGradeRepo.existsById(id)) {
            aiGradeRepo.deleteById(id);
        } else {
            throw new RuntimeException("Grade not found");
        }
    }
}
