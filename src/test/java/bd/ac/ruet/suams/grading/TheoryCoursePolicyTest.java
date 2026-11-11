package bd.ac.ruet.suams.grading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Unit tests UT-01 to UT-07 for rule R5. */
class TheoryCoursePolicyTest {
    private final TheoryCoursePolicy policy = new TheoryCoursePolicy();

    /** Builds a record from text such as "12,15,9,18". */
    static AssessmentRecord record(String classTests, String attendance, String assignment, String finalMark) {
        List<BigDecimal> tests = classTests.isBlank() ? List.of()
                : Arrays.stream(classTests.split(",")).map(String::trim).map(BigDecimal::new).toList();
        return new AssessmentRecord(tests, new BigDecimal(attendance), new BigDecimal(assignment),
                new BigDecimal(finalMark));
    }

    @Test
    @DisplayName("UT-01: best three of four class tests, normal path")
    void ut01() {
        assertEquals(77, policy.courseTotal(record("12,15,9,18", "9", "8", "45")));
    }

    @Test
    @DisplayName("UT-02: a raw total of 76.5 is rounded up to 77")
    void ut02() {
        assertEquals(77, policy.courseTotal(record("18,16,14,9", "9", "7", "44.5")));
    }

    @Test
    @DisplayName("UT-03: no class tests, loop skipped")
    void ut03() {
        IncompleteAssessmentException e = assertThrows(IncompleteAssessmentException.class,
                () -> policy.courseTotal(record("", "9", "8", "45")));
        assertEquals("0 of 4 class tests found", e.getMessage());
    }

    @Test
    @DisplayName("UT-04: three class tests, loop runs n-1 times")
    void ut04() {
        IncompleteAssessmentException e = assertThrows(IncompleteAssessmentException.class,
                () -> policy.courseTotal(record("14,16,11", "9", "8", "45")));
        assertEquals("3 of 4 class tests found", e.getMessage());
    }

    @Test
    @DisplayName("UT-05: five class tests, loop runs n+1 times")
    void ut05() {
        InvalidAssessmentException e = assertThrows(InvalidAssessmentException.class,
                () -> policy.courseTotal(record("14,16,11,19,8", "9", "8", "45")));
        assertEquals("at most 4 class tests", e.getMessage());
    }

    @Test
    @DisplayName("UT-06: full marks give exactly 100")
    void ut06() {
        assertEquals(100, policy.courseTotal(record("20,20,20,20", "10", "10", "60")));
    }

    @Test
    @DisplayName("UT-07: a class-test mark above 20 is refused")
    void ut07() {
        InvalidMarkException e = assertThrows(InvalidMarkException.class,
                () -> policy.courseTotal(record("18,21,12,15", "9", "8", "45")));
        assertEquals("class-test mark 21 is above 20", e.getMessage());
    }
}
