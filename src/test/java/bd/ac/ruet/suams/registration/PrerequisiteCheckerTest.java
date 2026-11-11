package bd.ac.ruet.suams.registration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bd.ac.ruet.suams.grading.Grade;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Unit tests UT-08 to UT-10 for rule R2, with a stub in place of Student Records. */
class PrerequisiteCheckerTest {
    private final Student stu = new Student("stu-001");

    @Test
    @DisplayName("UT-08: D is the lowest passing grade")
    void ut08() {
        TranscriptRepository stub = (s, code) -> Optional.of(Grade.D);
        assertTrue(new PrerequisiteChecker(stub).hasPassed(stu, List.of("CSE 1203")));
    }

    @Test
    @DisplayName("UT-09: F is not a pass")
    void ut09() {
        TranscriptRepository stub = (s, code) -> Optional.of(Grade.F);
        assertFalse(new PrerequisiteChecker(stub).hasPassed(stu, List.of("CSE 1203")));
    }

    @Test
    @DisplayName("UT-10: a missing record is not a pass, and nothing is thrown")
    void ut10() {
        TranscriptRepository stub = (s, code) -> code.equals("CSE 1203") ? Optional.of(Grade.A) : Optional.empty();
        assertFalse(new PrerequisiteChecker(stub).hasPassed(stu, List.of("CSE 1203", "CSE 2201")));
    }
}
