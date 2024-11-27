package fusionIQ.AI.V2.fusionIq.service;

import fusionIQ.AI.V2.fusionIq.data.*;
import fusionIQ.AI.V2.fusionIq.exception.ResourceNotFoundException;
import fusionIQ.AI.V2.fusionIq.exception.UserNotFoundException;
import fusionIQ.AI.V2.fusionIq.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MockTestInterviewService {

    @Autowired
    private MockTestInterviewRepository repository;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MentorRepo mentorRepo;

    @Autowired
    private AssignmentRepo assignmentRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private BookedMockTestInterviewRepository bookedMockTestInterviewRepo;



    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private TrainingRoomRepo trainingRoomRepo;

    public MockTestInterview createMockTestInterview(MockTestInterview mockTestInterview) {
        return repository.save(mockTestInterview);
    }


    public MockTestInterview createMockTestInterviewByCourse(MockTestInterview mockTestInterview, Long teacherId) {
        // Retrieve the teacher entity from the database
        User teacher = userRepo.findById(teacherId)
                .orElseThrow(() -> new UserNotFoundException("Teacher with ID " + teacherId + " not found."));

        // Set the teacher for the mockTestInterview
        mockTestInterview.setTeacher(teacher);

        // Save the mock test interview with the teacher set
        return repository.save(mockTestInterview);
    }
    public Optional<MockTestInterview> getMockTestInterviewById(Long id) {
        Optional<MockTestInterview> mockTestInterviewOpt = repository.findById(id);

        if (mockTestInterviewOpt.isPresent()) {
            MockTestInterview mockTestInterview = mockTestInterviewOpt.get();

            // Fetch Project or Assignment based on TestType
            if (mockTestInterview.getTestType() == MockTestInterview.TestType.PROJECT) {
                // Initialize Project (this assumes the project relationship is eagerly fetched)
                mockTestInterview.getProject();
            } else if (mockTestInterview.getTestType() == MockTestInterview.TestType.ASSIGNMENT) {
                // Initialize Assignment (this assumes the assignment relationship is eagerly fetched)
                mockTestInterview.getAssignment();
            }

            return Optional.of(mockTestInterview);
        }

        return Optional.empty();
    }
//    public List<MockTestInterview> getAllMockTestInterviews() {
//        return repository.findAll();
//    }



    public List<MockTestInterview> getAllMockTestInterviews() {
        return repository.findAll();
    }



    public boolean hasExceededFreeTrials(Long studentId, Long mockId) {
        MockTestInterview mockTestInterview = repository.findById(mockId)
                .orElseThrow(() -> new RuntimeException("MockTestInterview not found"));

        Long bookedCount = repository.countByStudentIdAndMockTestInterviewId(studentId, mockId);
        return bookedCount >= mockTestInterview.getFreeAttempts();
    }

    public MockTestInterview getMockTestInterviewDetails(Long mockId) {
        return repository.findById(mockId)
                .orElseThrow(() -> new RuntimeException("MockTestInterview not found"));
    }

    public MockTestInterview findById(Long id) {
        Optional<MockTestInterview> mockTestInterview = repository.findById(id);
        return mockTestInterview.orElse(null);
    }

//    public Object getByMockId(Long mockId) {
//        Optional<Assignment> assignment = assignmentRepo.findByMockTestInterviewId(mockId);
//        if (assignment.isPresent()) {
//            return assignment.get();
//        }
//
//        Optional<Project> project = projectRepo.findByMockTestInterviewId(mockId);
//        if (project.isPresent()) {
//            return project.get();
//        }
//
//        throw new ResourceNotFoundException("No assignment or project found for mock_id: " + mockId);
//    }

    public Map<String, Object> getByMockId(Long mockId) {
        Map<String, Object> result = new HashMap<>();

        // Fetch the slot associated with the given mockId
        List<Slot> slots = slotRepository.findByMockTestInterviewId(mockId);

        if (slots.isEmpty()) {
            throw new ResourceNotFoundException("No slots found for mock_id: " + mockId);
        }

        Slot selectedSlot = slots.get(0); // Choose the slot you need (e.g., the first one, or based on specific logic)
        MockTestInterview mockTestInterview = selectedSlot.getMockTestInterview();

        result.put("slotId", selectedSlot.getId());
        result.put("slotName", selectedSlot.getSlotName());
        result.put("slotTime", selectedSlot.getSlotTime());
        result.put("endTime", selectedSlot.getEndTime());

        // Fetch all projects associated with this mockTestInterview
        List<Project> projects = projectRepo.findAllByMockTestInterviewId(mockId);
        if (!projects.isEmpty()) {
            result.put("testType", "PROJECT");
            result.put("projects", projects.stream().map(project -> {
                Map<String, Object> projectDetails = new HashMap<>();
                projectDetails.put("id", project.getId());
                projectDetails.put("title", project.getProjectTitle());
                projectDetails.put("description", project.getProjectDescription());
                projectDetails.put("document", project.getProjectDocument());
                projectDetails.put("mock_id",project.getMockTestInterview().getId());
                return projectDetails;
            }).collect(Collectors.toList()));
        }

        // Fetch all assignments associated with this mockTestInterview
        List<Assignment> assignments = assignmentRepo.findAllByMockTestInterviewId(mockId);
        if (!assignments.isEmpty()) {
            result.put("testType", "ASSIGNMENT");
            result.put("assignments", assignments.stream().map(assignment -> {
                Map<String, Object> assignmentDetails = new HashMap<>();
                assignmentDetails.put("id", assignment.getId());
                assignmentDetails.put("title", assignment.getAssignmentTitle());
                assignmentDetails.put("description", assignment.getAssignmentDescription());
                assignmentDetails.put("document", assignment.getAssignmentDocument());
                assignmentDetails.put("mock_id",assignment.getMockTestInterview().getId());
                return assignmentDetails;
            }).collect(Collectors.toList()));
        }

        // Fetch interview details if the test type is "INTERVIEW"
        List<TrainingRoom> trainingRooms = trainingRoomRepo.findAllByMockTestInterviewId(mockId);
        if (!trainingRooms.isEmpty()) {
            result.put("testType", "INTERVIEW");
            result.put("interviews", trainingRooms.stream().map(trainingRoom -> {
                Map<String, Object> interviewDetails = new HashMap<>();
                interviewDetails.put("trainingRoomId", trainingRoom.getId());
                interviewDetails.put("conferenceUrl", trainingRoom.getConferenceUrl());
                interviewDetails.put("trainingRoomName", trainingRoom.getName());
                interviewDetails.put("mock_id",trainingRoom.getMockTestInterview().getId());
                return interviewDetails;
            }).collect(Collectors.toList()));
        }

        // If neither projects, assignments, nor interviews were found, throw an exception
        if (projects.isEmpty() && assignments.isEmpty() && trainingRooms.isEmpty()) {
            throw new ResourceNotFoundException("No assignment, project, or interview found for mock_id: " + mockId);
        }

        return result;
    }


//    public List<Map<String, Object>> getAllMockTestInterviewsWithSelectedFields() {
//        List<Object[]> results = repository.findSelectedFields();
//        List<Map<String, Object>> mockTests = new ArrayList<>();
//
//        for (Object[] row : results) {
//            Map<String, Object> rowMap = new HashMap<>();
//            rowMap.put("title", row[0]);
//            rowMap.put("description", row[1]);
//            rowMap.put("testType", row[2]);
//            rowMap.put("fee", row[3]);
//            rowMap.put("freeAttempts", row[4]);
//            rowMap.put("image",row[5]);
//            mockTests.add(rowMap);
//        }
//
//        return mockTests;
//    }

    public List<Map<String, Object>> getAllMockTestInterviewsWithSelectedFields() {
        List<Object[]> results = repository.findSelectedFields();
        List<Map<String, Object>> mockTests = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put("title", row[0]);
            rowMap.put("description", row[1]);
            rowMap.put("testType", row[2]);
            rowMap.put("fee", row[3]);
            rowMap.put("freeAttempts", row[4]);
            rowMap.put("image", row[5]);
            rowMap.put("courseId", row[6]); // Adding courseId to the map
            rowMap.put("mock_id",row[7]);
            mockTests.add(rowMap);

        }

        return mockTests;
    }


}
