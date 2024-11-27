package fusionIQ.AI.V2.fusionIq.service;

import fusionIQ.AI.V2.fusionIq.data.Course;
import fusionIQ.AI.V2.fusionIq.data.CourseDocuments;
import fusionIQ.AI.V2.fusionIq.exception.ResourceNotFoundException;
import fusionIQ.AI.V2.fusionIq.repository.CourseDocumentsRepo;
import fusionIQ.AI.V2.fusionIq.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseDocumentsService {

    @Autowired
    private CourseDocumentsRepo courseDocumentsRepo;

    @Autowired
    private CourseRepo courseRepo;

    public Course getCourseById(Long courseId) {
        return courseRepo.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }

    public CourseDocuments saveCourseDocument(CourseDocuments courseDocument) {
        return courseDocumentsRepo.save(courseDocument);
    }

    public List<CourseDocuments> getDocumentsByCourseId(Long courseId) {
        return courseDocumentsRepo.findByCourseId(courseId);
    }

    public void deleteDocumentById(Long id) {
        courseDocumentsRepo.deleteById(id);
    }
}
