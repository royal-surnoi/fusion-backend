package fusionIQ.AI.V2.fusionIq.testcontroller;

import fusionIQ.AI.V2.fusionIq.controller.AssignmentController;
import fusionIQ.AI.V2.fusionIq.data.Assignment;
import fusionIQ.AI.V2.fusionIq.repository.AssignmentRepo;
import fusionIQ.AI.V2.fusionIq.repository.LessonRepo;
import fusionIQ.AI.V2.fusionIq.repository.SubmitAssignmentRepo;
import fusionIQ.AI.V2.fusionIq.repository.UserRepo;
import fusionIQ.AI.V2.fusionIq.service.AssignmentService;
import fusionIQ.AI.V2.fusionIq.service.SubmitAssignmentService;
import fusionIQ.AI.V2.fusionIq.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AssignmentController.class)
@ExtendWith(MockitoExtension.class)
public class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssignmentService assignmentService;

    @MockBean
    private AssignmentRepo assignmentRepo;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private LessonRepo lessonRepo;

    @MockBean
    private SubmitAssignmentService submitAssignmentService;

    @MockBean
    private SubmitAssignmentRepo submitAssignmentRepo;

    @MockBean
    private UserService userService; // Add this line to mock UserService


    @Test
    public void testGetAssignmentsByCourseId_Success() throws Exception {
        Long courseId = 1L;
        Assignment assignment1 = new Assignment();
        assignment1.setId(1L);
        Assignment assignment2 = new Assignment();
        assignment2.setId(2L);

        when(assignmentService.getAssignmentsByCourseId(courseId)).thenReturn(Arrays.asList(assignment1, assignment2));

        mockMvc.perform(get("/course/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    public void testGetAssignmentsByCourseId_NotFound() throws Exception {
        Long courseId = 1L;

        when(assignmentService.getAssignmentsByCourseId(courseId)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/course/{courseId}", courseId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddAssignment_Failure() throws Exception {
        Long lessonId = 1L;
        Long courseId = 1L;
        String assignmentTitle = "Test Assignment";
        String assignmentTopicName = "Test Topic";
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(5);
        LocalDateTime reviewMeetDate = LocalDateTime.now().plusDays(7);

        when(assignmentService.addAssign(eq(lessonId), eq(courseId), eq(assignmentTitle), eq(assignmentTopicName),
                any(MockMultipartFile.class), eq(startDate), eq(endDate), eq(reviewMeetDate)))
                .thenThrow(new IllegalArgumentException("Lesson not found or Course not found"));

        MockMultipartFile mockFile = new MockMultipartFile("document", "test.txt", "text/plain", "test content".getBytes());

        mockMvc.perform(multipart("/saveLesson/{lessonId}/{courseId}", lessonId, courseId)
                        .file(mockFile)
                        .param("assignmentTitle", assignmentTitle)
                        .param("assignmentTopicName", assignmentTopicName)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("reviewMeetDate", reviewMeetDate.toString())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateAssignment_Success() throws Exception {
        Long assignmentId = 1L;
        String assignmentTitle = "Updated Assignment";
        String assignmentTopicName = "Updated Topic";
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(5);
        LocalDateTime reviewMeetDate = LocalDateTime.now().plusDays(7);
        String assignmentDescription = "Updated Description";
        Long maxScore = 100L;
        String assignmentAnswer = "Sample Answer";
        MockMultipartFile assignmentDocument = new MockMultipartFile("document", "test.txt", "text/plain", "updated content".getBytes());

        Assignment updatedAssignment = new Assignment();
        updatedAssignment.setId(assignmentId);
        updatedAssignment.setAssignmentTitle(assignmentTitle);

        when(assignmentService.updateAssign(eq(assignmentId), eq(assignmentTitle), eq(assignmentTopicName),
                any(MockMultipartFile.class), eq(startDate), eq(endDate), eq(reviewMeetDate),
                eq(assignmentDescription), eq(maxScore), eq(assignmentAnswer)))
                .thenReturn(updatedAssignment);

        mockMvc.perform(multipart("/updateAssignment/{assignmentId}", assignmentId)
                        .file(assignmentDocument)
                        .param("assignmentTitle", assignmentTitle)
                        .param("assignmentTopicName", assignmentTopicName)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("reviewMeetDate", reviewMeetDate.toString())
                        .param("assignmentDescription", assignmentDescription)
                        .param("maxScore", maxScore.toString())
                        .param("assignmentAnswer", assignmentAnswer)
                        .with(request -> {
                            request.setMethod("PUT");  // Override the method to PUT
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(assignmentId))
                .andExpect(jsonPath("$.assignmentTitle").value(assignmentTitle));
    }

    @Test
    public void testDeleteAssignment_Success() throws Exception {
        Long assignmentId = 1L;

        Mockito.doNothing().when(assignmentService).deleteAssign(assignmentId);

        mockMvc.perform(delete("/deleteAssignment/{assignmentId}", assignmentId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteAssignment_Failure() throws Exception {
        Long assignmentId = 1L;

        Mockito.doThrow(new IllegalArgumentException("Assignment not found")).when(assignmentService).deleteAssign(assignmentId);

        mockMvc.perform(delete("/deleteAssignment/{assignmentId}", assignmentId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUpcomingAssignments_Success() throws Exception {
        Long userId = 1L;
        Assignment assignment1 = new Assignment();
        assignment1.setId(1L);
        Assignment assignment2 = new Assignment();
        assignment2.setId(2L);

        when(assignmentService.getUpcomingAssignmentsByUserId(userId)).thenReturn(Arrays.asList(assignment1, assignment2));

        mockMvc.perform(get("/upcoming/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }
}
