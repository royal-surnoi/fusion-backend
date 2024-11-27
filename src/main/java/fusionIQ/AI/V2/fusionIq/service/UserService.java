package fusionIQ.AI.V2.fusionIq.service;
import fusionIQ.AI.V2.fusionIq.data.*;
import fusionIQ.AI.V2.fusionIq.exception.ResourceNotFoundException;
import fusionIQ.AI.V2.fusionIq.exception.UserNotFoundException;
import fusionIQ.AI.V2.fusionIq.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EnrollmentRepo enrollmentRepo;
    @Autowired
    private FollowRepo followRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private SavedItemsRepo savedItemsRepo;
    @Autowired
    private MentorRepo mentorRepo;
    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private AssignmentRepo assignmentRepo;

    @Autowired
    private QuizRepo quizRepo;

    @Autowired
    private QuestionRepo questionRepo;


    public User saveUser(User user) {
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already in use.");
        }
        return userRepo.save(user);
    }


    public Optional<User> findUserById(Long id) {
        return userRepo.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @Transactional
    public void deleteUser(Long id) {
        enrollmentRepo.deleteByUserId(id);
        followRepo.deleteByFollowerId(id);
        followRepo.deleteByFollowingId(id);
        userRepo.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }


    public List<Enrollment> getUserEnrollments(Long userId) {
        return enrollmentRepo.findByUserId(userId);
    }


    public void followUser(Long followerId, Long followingId) {
        if (!followRepo.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            Follow follow = new Follow();
            follow.setFollower(userRepo.findById(followerId).orElseThrow(() -> new IllegalArgumentException("Follower not found")));
            follow.setFollowing(userRepo.findById(followingId).orElseThrow(() -> new IllegalArgumentException("Following not found")));
            followRepo.save(follow);
        }
    }


    @Transactional
    public void unfollowUser(Long followerId, Long followingId) {
        followRepo.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }

    public void enrollUserInCourse(long userId, long courseId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setProgress(enrollment.getProgress());
        enrollmentRepo.save(enrollment);
    }

    public void unenrollUserFromCourse(long userId, long courseId) {
        enrollmentRepo.deleteByUserIdAndCourseId(userId, courseId);
    }

    public User save(User user) {
        return userRepo.save(user);
    }


    public String generateTokenForUser(User user) {
        return tokenService.generateToken(user.getEmail());
    }

    public boolean validateToken(String token, User user) {
        return tokenService.validateToken(token, user);
    }


    public User registerUser(User user) {
        return saveUser(user);
    }


    public User login(String email, String password) {
        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User addNewUser(String name, String email, String password, String preferences, String userLanguage, String createdAt, String updatedAt, MultipartFile userImage, String userDescription) throws IOException {
        LocalDateTime createdDateTime = LocalDateTime.parse(createdAt);
        LocalDateTime updatedDateTime = LocalDateTime.parse(updatedAt);

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPreferences(preferences);
        newUser.setUserLanguage(userLanguage);
        newUser.setCreatedAt(createdDateTime);
        newUser.setUpdatedAt(updatedDateTime);
        newUser.setUserImage(userImage.getBytes());


        if (userImage != null && !userImage.isEmpty()) {
            byte[] imageBytes = userImage.getBytes();
            newUser.setUserImage(imageBytes);
        }
        newUser.setUserDescription(userDescription);

        userRepo.save(newUser);

        return newUser;
    }


    public User getUser(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public User findByName(String name) {
        return (userRepo.findByName(name));
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setPreferences(userDetails.getPreferences());
        user.setUserLanguage(userDetails.getUserLanguage());
        user.setOtp(userDetails.getOtp());
        user.setOtpGeneratedTime(userDetails.getOtpGeneratedTime());
        user.setCreatedAt(userDetails.getCreatedAt());
        user.setUpdatedAt(userDetails.getUpdatedAt());
        user.setUserImage(userDetails.getUserImage());

        return userRepo.save(user);
    }

    public User patchUserById(long userId, Map<String, Object> updates) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            updates.forEach((key, value) -> {
                switch (key) {
                    case "name":
                        user.setName((String) value);
                        break;
                    case "email":
                        user.setEmail((String) value);
                        break;
                    case "password":
                        user.setPassword((String) value);
                        break;

                    case "preferences":
                        user.setPreferences((String) value);
                        break;
                    case "language":
                        user.setUserLanguage((String) value);
                        break;
                    case "otp":
                        user.setOtp((String) value);
                        break;
                    case "otpGeneratedTime":

                        break;
                    case "userImage":

                        break;
                    case "onlineStatus":
                        user.setOnlineStatus(User.OnlineStatus.valueOf((String) value));
                        break;
                    case "lastSeen":

                        break;
                    case "userDescription":
                        user.setUserDescription((String) value);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid field: " + key);
                }
            });
            user.setUpdatedAt(LocalDateTime.now());
            return userRepo.save(user);
        }
        return null;
    }

    public void saveUserImage(long id, MultipartFile image) throws IOException {

        User user = userRepo.findById(id)

                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUserImage(image.getBytes());

        userRepo.save(user);

    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }


    public Mentor registerMentor(Long userId, Mentor mentor) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (mentorRepo.existsByUserId(userId)) {
            throw new IllegalArgumentException("User is already registered as a mentor");
        }

        // Check if the username already exists
        if (mentorRepo.existsByUsername(mentor.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        mentor.setUser(user);

        try {
            return mentorRepo.save(mentor);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Error saving Mentor", e);
        }
    }

    public Mentor loginMentor(String username, String password) {
        Mentor mentor = (Mentor) mentorRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor not found"));
        if (mentor.getPassword().equals(password)) {
            return mentor;
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public boolean existsByUsername(String username) {
        return mentorRepo.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email) || mentorRepo.existsByUsername(email);
    }

    public List<Mentor> getAllMentors() {
        List<Mentor> mentors = mentorRepo.findAll();
        mentors.forEach(mentor -> {
            if (mentor.getUser() != null) {
                mentor.getUser().getName(); // Force fetch user details
            }
        });
        return mentors;
    }

    public Map<String, Object> updateUserStatusAndGetLastSeen(Long userId, User.OnlineStatus status) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setOnlineStatus(status);
        if (status == User.OnlineStatus.OFFLINE) {
            user.setLastSeen(LocalDateTime.now());
        }
        user = userRepo.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("onlineStatus", user.getOnlineStatus());
        response.put("lastSeen", user.getLastSeen());
        return response;

    }

    public Set<User> findAllByIds(List<Long> userIds) {
        return new HashSet<>(userRepo.findAllById(userIds));
    }


    @Cacheable(value = "userDetailsCache", key = "#userId")
    public Map<String, Object> getUserDetailsById(Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);

        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        User user = userOptional.get();
        return transformUserDetails(user);
    }

    private Map<String, Object> transformUserDetails(User user) {
        if (user == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("id", user.getId());
        userDetails.put("name", user.getName());

        // Optionally check if these fields are not null before adding them to the map
        if (user.getPreferences() != null) {
            userDetails.put("preferences", user.getPreferences());
        }
        if (user.getProfession() != null) {
            userDetails.put("profession", user.getProfession());
        }
        if (user.getUserImage() != null) {
            userDetails.put("userImage", user.getUserImage());
        }

        // Add more fields as needed, checking for null values where appropriate
        // Example:
        // if (user.getEmail() != null) {
        //     userDetails.put("email", user.getEmail());
        // }

        return userDetails;
    }

    public boolean isUserMentor(Long userId) {
        return mentorRepo.existsByUserId(userId);
    }


    public User.OnlineStatus getUserOnlineStatus(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        return user.getOnlineStatus();
    }

    public void updateUserOnlineStatus(Long userId, User.OnlineStatus status) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

        user.setOnlineStatus(status);
        if (status == User.OnlineStatus.OFFLINE) {
            user.setLastSeen(LocalDateTime.now()); // Update lastSeen to the current time
        } else {
            user.setLastSeen(null); // Reset last seen when online
        }

        userRepo.save(user);
    }


    public List<Map<String, Object>> getCourseDetailsByTeacherId(Long teacherId) {
        Mentor mentor = mentorRepo.findByUserId(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with ID " + teacherId + " not found."));

        List<Course> courses = courseRepo.findByUserId(teacherId);
        List<Map<String, Object>> courseDetailsList = new ArrayList<>();

        for (Course course : courses) {
            Map<String, Object> courseDetails = new HashMap<>();
            courseDetails.put("courseTitle", course.getCourseTitle());
            courseDetails.put("courseType", course.getCourseType());
            int enrolledStudentsCount = course.getEnrollments().size();
            courseDetails.put("enrolledStudentsCount", enrolledStudentsCount);

            // Fetch assignments
            List<Map<String, Object>> assignmentDetails = new ArrayList<>();
            List<Assignment> assignments = assignmentRepo.findByCourseId(course.getId());

            for (Assignment assignment : assignments) {
                Map<String, Object> assignmentInfo = new HashMap<>();
                assignmentInfo.put("assignmentId", assignment.getId());
                assignmentInfo.put("assignmentTitle", assignment.getAssignmentTitle());
                assignmentInfo.put("assignmentTopicName", assignment.getAssignmentTopicName());
                assignmentInfo.put("assignmentDescription", assignment.getAssignmentDescription());
                assignmentInfo.put("maxScore", assignment.getMaxScore());
                assignmentInfo.put("startDate", assignment.getStartDate());
                assignmentInfo.put("endDate", assignment.getEndDate());
                assignmentInfo.put("reviewMeetDate", assignment.getReviewMeetDate());
                assignmentInfo.put("assignmentAnswer", assignment.getAssignmentAnswer());
                assignmentInfo.put("studentIds", assignment.getStudentIds());
                assignmentInfo.put("studentNames", fetchStudentNamesFromIds(assignment.getStudentIds()));
                assignmentInfo.put("assignmentDocument", assignment.getAssignmentDocument()); // Include document

                assignmentDetails.add(assignmentInfo);
            }

            // Fetch projects
            List<Map<String, Object>> projectDetails = new ArrayList<>();
            List<Project> projects = projectRepo.findByCourseId(course.getId());

            for (Project project : projects) {
                Map<String, Object> projectInfo = new HashMap<>();
                projectInfo.put("projectId", project.getId());
                projectInfo.put("projectTitle", project.getProjectTitle());
                projectInfo.put("projectDescription", project.getProjectDescription());
                projectInfo.put("projectDeadline", project.getProjectDeadline());
                projectInfo.put("startDate", project.getStartDate());
                projectInfo.put("reviewMeetDate", project.getReviewMeetDate());
                projectInfo.put("maxTeam", project.getMaxTeam());
                projectInfo.put("gitUrl", project.getGitUrl());
                projectInfo.put("studentIds", project.getStudentIds());
                projectInfo.put("studentNames", fetchStudentNamesFromIds(project.getStudentIds()));
                projectInfo.put("projectDocument", project.getProjectDocument()); // Include document

                projectDetails.add(projectInfo);
            }

            courseDetails.put("assignmentCount", assignments.size());
            courseDetails.put("assignmentDetails", assignmentDetails);
            courseDetails.put("projectCount", projects.size());
            courseDetails.put("projectDetails", projectDetails);

            courseDetailsList.add(courseDetails);
        }

        return courseDetailsList;
    }

    private List<Map<String, Object>> fetchStudentNamesFromIds(String studentIds) {
        List<Map<String, Object>> studentList = new ArrayList<>();
        if (studentIds != null && !studentIds.isEmpty()) {
            String[] ids = studentIds.split(",");
            for (String idStr : ids) {
                Long studentId = Long.parseLong(idStr.trim());
                User student = userRepo.findById(studentId)
                        .orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " not found."));
                Map<String, Object> studentInfo = new HashMap<>();
                studentInfo.put("studentId", studentId);
                studentInfo.put("studentName", student.getName());
                studentList.add(studentInfo);
            }
        }
        return studentList;
    }

    public boolean isUserLoggedIn(Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getOnlineStatus() == User.OnlineStatus.ONLINE;
        }

        return false;
    }

    public void logoutUser(Long userId) {
        updateUserOnlineStatus(userId, User.OnlineStatus.OFFLINE);
        // Optionally, invalidate any tokens associated with this user
        String token = getTokenByUserId(userId);
        if (token != null) {
            tokenService.invalidateToken(token);
        }
    }

    public String getTokenByUserId(Long userId) {
        // Retrieve the token associated with this userId, implementation depends on your setup
        // For example, you might store tokens in a database or in memory
        return null;
    }

    public User getByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public boolean updatePassword(String email, String currentPassword, String newPassword) {
        User user = getByEmail(email);
        if (user != null && user.getPassword().equals(currentPassword)) {
            user.setPassword(newPassword);  // Hash the password here in a real application
            user.setUpdatedAt(LocalDateTime.now());
            userRepo.save(user);
            return true;
        }
        return false;
    }

    public List<Map<String, Object>> getQuizDetailsByTeacherId(Long teacherId) {
        // Check if the teacher is a mentor
        Mentor mentor = mentorRepo.findByUserId(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with ID " + teacherId + " not found."));

        // Fetch courses taught by the teacher
        List<Course> courses = courseRepo.findByUserId(teacherId);
        List<Map<String, Object>> courseDetailsList = new ArrayList<>();

        for (Course course : courses) {
            Map<String, Object> courseDetails = new HashMap<>();
            courseDetails.put("courseTitle", course.getCourseTitle());
            courseDetails.put("courseType", course.getCourseType());
            int enrolledStudentsCount = course.getEnrollments().size();
            courseDetails.put("enrolledStudentsCount", enrolledStudentsCount);

            // Fetch quizzes associated with this course where studentIds are present
            List<Map<String, Object>> quizDetails = new ArrayList<>();
            List<Quiz> quizzes = quizRepo.findByCourseId(course.getId());

            for (Quiz quiz : quizzes) {
                if (quiz.getStudentIds() != null && !quiz.getStudentIds().isEmpty()) {
                    Map<String, Object> quizInfo = new HashMap<>();
                    quizInfo.put("quizId", quiz.getId());
                    quizInfo.put("quizName", quiz.getQuizName());
                    quizInfo.put("startDate", quiz.getStartDate());
                    quizInfo.put("endDate", quiz.getEndDate());

                    // Count and fetch student details
                    List<Map<String, Object>> studentDetails = new ArrayList<>();
                    String[] studentIdsArray = quiz.getStudentIds().split(",");
                    quizInfo.put("studentCount", studentIdsArray.length);

                    for (String studentIdStr : studentIdsArray) {
                        Long studentId = Long.valueOf(studentIdStr);
                        userRepo.findById(studentId).ifPresent(student -> {
                            Map<String, Object> studentInfo = new HashMap<>();
                            studentInfo.put("studentId", student.getId());
                            studentInfo.put("studentName", student.getName());
                            studentDetails.add(studentInfo);
                        });
                    }
                    quizInfo.put("studentNames", studentDetails);

                    // Fetch questions and options for each quiz
                    List<Map<String, Object>> questionDetails = questionRepo.findByQuizId(quiz.getId()).stream().map(question -> {
                        Map<String, Object> questionInfo = new HashMap<>();
                        questionInfo.put("questionId", question.getId());
                        questionInfo.put("text", question.getText());
                        questionInfo.put("optionA", question.getOptionA());
                        questionInfo.put("optionB", question.getOptionB());
                        questionInfo.put("optionC", question.getOptionC());
                        questionInfo.put("optionD", question.getOptionD());
                        questionInfo.put("correctAnswer", question.getCorrectAnswer());
                        return questionInfo;
                    }).collect(Collectors.toList());
                    quizInfo.put("questions", questionDetails);

                    quizDetails.add(quizInfo);
                }
            }

            courseDetails.put("quizCount", quizDetails.size());
            courseDetails.put("quizDetails", quizDetails);

            courseDetailsList.add(courseDetails);
        }

        return courseDetailsList;
    }

    public void updateUserName(Long id, String name) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(name);
        user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(user);
    }
}