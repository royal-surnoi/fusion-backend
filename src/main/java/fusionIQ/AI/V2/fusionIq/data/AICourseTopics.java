package fusionIQ.AI.V2.fusionIq.data;


import jakarta.persistence.*;

@Entity
@Table(name = "ai_course_topics")
public class AICourseTopics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "week_number", nullable = false)
    private int weekNumber;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String mainTopicTitle;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String subTopic;

    @Lob
    @Column(name = "explanation", nullable = false)
    private String explanation;

    @ManyToOne
    @JoinColumn(name = "ai_course_plan_id", nullable = false)
    private AICoursePlan aiCoursePlan;


    public AICourseTopics() {
    }

    public AICourseTopics(Long id, int weekNumber, String mainTopicTitle, String subTopic, String explanation, AICoursePlan aiCoursePlan) {
        this.id = id;
        this.weekNumber = weekNumber;
        this.mainTopicTitle = mainTopicTitle;
        this.subTopic = subTopic;
        this.explanation = explanation;
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

    public String getMainTopicTitle() {
        return mainTopicTitle;
    }

    public void setMainTopicTitle(String mainTopicTitle) {
        this.mainTopicTitle = mainTopicTitle;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public AICoursePlan getAiCoursePlan() {
        return aiCoursePlan;
    }

    public void setAiCoursePlan(AICoursePlan aiCoursePlan) {
        this.aiCoursePlan = aiCoursePlan;
    }

    @Override
    public String toString() {
        return "AICourseTopics{" +
                "id=" + id +
                ", weekNumber=" + weekNumber +
                ", mainTopicTitle='" + mainTopicTitle + '\'' +
                ", subTopic='" + subTopic + '\'' +
                ", explanation='" + explanation + '\'' +
                ", aiCoursePlan=" + aiCoursePlan +
                '}';
    }
}
