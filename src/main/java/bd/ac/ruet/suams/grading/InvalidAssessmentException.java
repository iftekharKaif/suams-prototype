package bd.ac.ruet.suams.grading;

/** Thrown when a theory course has more class-test marks than the rules allow. */
public class InvalidAssessmentException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidAssessmentException(String message) {
        super(message);
    }
}
