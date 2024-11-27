package fusionIQ.AI.V2.fusionIq.data;

import jakarta.persistence.*;

@Entity
@Table(name = "ai_course_assignments")
public class AICourseAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "week_number", nullable = false)
    private int weekNumber;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String aiCourseAssignment;

    @ManyToOne
    @JoinColumn(name = "ai_course_plan_id", nullable = false)
    private AICoursePlan aiCoursePlan;


    public AICourseAssignment() {
    }

    public AICourseAssignment(Long id, int weekNumber, String aiCourseAssignment, AICoursePlan aiCoursePlan) {
        this.id = id;
        this.weekNumber = weekNumber;
        this.aiCourseAssignment = aiCourseAssignment;
        this.aiCoursePlan = aiCoursePlan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getAiCourseAssignment() {
        return aiCourseAssignment;
    }

    public void setAiCourseAssignment(String aiCourseAssignment) {
        this.aiCourseAssignment = aiCourseAssignment;
    }

    public AICoursePlan getAiCoursePlan() {
        return aiCoursePlan;
    }

    public void setAiCoursePlan(AICoursePlan aiCoursePlan) {
        this.aiCoursePlan = aiCoursePlan;
    }

    @Override
    public String toString() {
        return "AICourseAssignment{" +
                "id=" + id +
                ", weekNumber=" + weekNumber +
                ", aiCourseAssignment='" + aiCourseAssignment + '\'' +
                ", aiCoursePlan=" + aiCoursePlan +
                '}';
    }
}

