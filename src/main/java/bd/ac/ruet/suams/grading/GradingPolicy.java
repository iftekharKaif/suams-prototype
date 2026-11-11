package bd.ac.ruet.suams.grading;

/** Strategy interface: one implementation per set of marking rules. */
public interface GradingPolicy {
    int courseTotal(AssessmentRecord record);              // whole marks, 0 to 100

    Grade gradeFor(int total, AssessmentRecord record);    // applies any final-mark floor
}
