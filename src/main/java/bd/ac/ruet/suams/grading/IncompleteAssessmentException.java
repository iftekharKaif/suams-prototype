package bd.ac.ruet.suams.grading;

/** Thrown when a theory course has fewer than four class-test marks. */
public class IncompleteAssessmentException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IncompleteAssessmentException(String message) {
        super(message);
    }
}
