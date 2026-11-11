package bd.ac.ruet.suams.grading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** The Strategy pattern: the registry picks the policy, and no course gets a default one. */
class ResultProcessorTest {
    private final PolicyRegistry registry =
            new PolicyRegistry().register("2019", "THEORY", new TheoryCoursePolicy());

    @Test
    @DisplayName("A theory course is graded by TheoryCoursePolicy")
    void theoryCourse() {
        CourseResult result = new ResultProcessor(registry).process("stu-001", "CSE 3205", "2019", "THEORY",
                TheoryCoursePolicyTest.record("12,15,9,18", "9", "8", "45"));
        assertEquals(77, result.total());
        assertEquals("A", result.grade().letter());
    }

    @Test
    @DisplayName("An unknown curriculum and course type is refused")
    void unknownPair() {
        assertThrows(IllegalStateException.class, () -> registry.policyFor("2019", "SESSIONAL"));
    }
}
