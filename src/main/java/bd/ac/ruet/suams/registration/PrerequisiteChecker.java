package bd.ac.ruet.suams.registration;

import bd.ac.ruet.suams.grading.Grade;
import java.util.List;
import java.util.Optional;

/** Rule R2: every prerequisite must have been passed with D or better. */
public class PrerequisiteChecker {
    private final TranscriptRepository transcripts;

    public PrerequisiteChecker(TranscriptRepository transcripts) {
        this.transcripts = transcripts;
    }

    public boolean hasPassed(Student s, List<String> prerequisites) {
        for (String code : prerequisites) {
            Optional<Grade> grade = transcripts.gradeOf(s, code);
            if (grade.isEmpty() || grade.get() == Grade.F) {
                return false;
            }
        }
        return true;
    }
}
