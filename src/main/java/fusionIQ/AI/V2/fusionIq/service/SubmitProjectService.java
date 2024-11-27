package fusionIQ.AI.V2.fusionIq.service;

import fusionIQ.AI.V2.fusionIq.data.*;
import fusionIQ.AI.V2.fusionIq.exception.ResourceNotFoundException;
import fusionIQ.AI.V2.fusionIq.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SubmitProjectService {

    @Autowired
    private SubmitProjectRepo submitProjectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private MockTestInterviewRepository mockTestInterviewRepo;


    public SubmitProject submitProject(Long userId, Long courseId,Long projectId,String userAnswer, MultipartFile submitProject) throws IOException {
        Optional<User> userOpt = userRepo.findById(userId);
        Optional<Course> courseOptional = courseRepo.findById(courseId);
        Optional<Project> projectOptional = projectRepo.findById(projectId);
        if (userOpt.isPresent()&& courseOptional.isPresent()&&projectOptional.isPresent()) {
            SubmitProject submitProject1 = new SubmitProject();
            submitProject1.setUser(userOpt.get());
            submitProject1.setCourse(courseOptional.get());
            submitProject1.setProject(projectOptional.get());
            submitProject1.setUserAnswer(userAnswer);
            submitProject1.setSubmitProject(submitProject.getBytes());
            submitProject1.setSubmitted(true);
            return submitProjectRepo.save(submitProject1);
        } else {
            throw new IllegalArgumentException("User or Course not found");
        }
    }

    public List<SubmitProject> findAllSubmissionProjects() {
        return submitProjectRepo.findAll();
    }

    public Optional<SubmitProject> findProjectSubmissionById(Long id) {
        return submitProjectRepo.findById(id);
    }

    public List<SubmitProject> getSubmitProjectByCourseId(Long courseId) {
        return submitProjectRepo.findByCourseId(courseId);
    }

    public List<SubmitProject> getSubmitProjectByUserId(Long userId) {
        return submitProjectRepo.findByUserId(userId);
    }

    public void deleteSubmitProject(Long id) {
        Optional<SubmitProject> submitProject = submitProjectRepo.findById(id);
        if (submitProject.isPresent()) {
            submitProjectRepo.deleteById(id);
        } else {
            throw new RuntimeException("SubmitProject not found with id " + id);
        }
    }
    public SubmitProject submitProject(Long courseId, Long userId, Long projectId, byte[] submitProjectFile, String userAnswer) throws IOException {

        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }

        Optional<Course> courseOptional = courseRepo.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new ResourceNotFoundException("Course not found with id " + courseId);
        }

        Optional<Project> projectOptional = projectRepo.findById(projectId);
        if (projectOptional.isEmpty()) {
            throw new ResourceNotFoundException("Project not found with id " + projectId);
        }

        User user = userOptional.get();
        Course course = courseOptional.get();
        Project project = projectOptional.get();

        SubmitProject submitProject = new SubmitProject();
        submitProject.setUser(user);
        submitProject.setCourse(course);
        submitProject.setProject(project);
        submitProject.setSubmitProject(submitProjectFile);
        submitProject.setUserAnswer(userAnswer);
        submitProject.setSubmittedAt(LocalDateTime.now());

        return submitProjectRepo.save(submitProject);
    }

    public void submitProjectByStudent(Long studentId, Long courseId, Long projectId, MultipartFile submitProject, String userAnswer) throws IOException {
        User student = userRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        SubmitProject newSubmission = new SubmitProject();
        newSubmission.setSubmitProject(submitProject.getBytes());
        newSubmission.setUserAnswer(userAnswer);
        newSubmission.setStudent(student);
        newSubmission.setCourse(course);
        newSubmission.setProject(project);
        newSubmission.setSubmittedAt(LocalDateTime.now());

        submitProjectRepo.save(newSubmission);
    }

    public SubmitProject submitProjectByMock(Long studentId, Long projectId, Long mockId, MultipartFile submitProject, String userAnswer) throws IOException {
        User student = userRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        MockTestInterview mockTestInterview = mockTestInterviewRepo.findById(mockId).orElseThrow(() -> new RuntimeException("MockTestInterview not found"));

        SubmitProject newSubmission = new SubmitProject();
        newSubmission.setStudent(student);
        newSubmission.setProject(project);
        newSubmission.setMockTestInterview(mockTestInterview);
        newSubmission.setSubmitProject(submitProject.getBytes());
        newSubmission.setUserAnswer(userAnswer);
        newSubmission.setSubmitted(true);
        newSubmission.setSubmittedAt(LocalDateTime.now());

        return submitProjectRepo.save(newSubmission);
    }

}
