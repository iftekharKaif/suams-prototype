package bd.ac.ruet.suams.registration;

import bd.ac.ruet.suams.grading.Grade;
import java.util.Optional;

/** Grades of courses a student has already taken; empty when there is no record. */
public interface TranscriptRepository {
    Optional<Grade> gradeOf(Student s, String courseCode);
}
