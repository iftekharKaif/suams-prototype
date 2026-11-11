package bd.ac.ruet.suams.grading;

/** Thrown when a single mark lies outside its allowed range. */
public class InvalidMarkException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidMarkException(String message) {
        super(message);
    }
}
