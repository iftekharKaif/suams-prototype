package bd.ac.ruet.suams.grading;

/** The result of one student in one course. */
public record CourseResult(String studentId, String courseCode, int total, Grade grade) { }
