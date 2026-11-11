package bd.ac.ruet.suams.grading;

/** Strategy context: asks the registry for the right policy and applies it. */
public class ResultProcessor {
    private final PolicyRegistry registry;

    public ResultProcessor(PolicyRegistry registry) {
        this.registry = registry;
    }

    public CourseResult process(String studentId, String courseCode, String curriculum,
                                String courseType, AssessmentRecord record) {
        GradingPolicy policy = registry.policyFor(curriculum, courseType);
        int total = policy.courseTotal(record);
        return new CourseResult(studentId, courseCode, total, policy.gradeFor(total, record));
    }
}
