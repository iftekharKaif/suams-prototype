package bd.ac.ruet.suams.grading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/** Black-box tests BB-01 to BB-10: boundary-value analysis on rules R5 and R6. */
class GradeBoundaryTest {
    private final TheoryCoursePolicy policy = new TheoryCoursePolicy();

    private static AssessmentRecord withFinal(String finalMark) {
        return new AssessmentRecord(List.of(), BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal(finalMark));
    }

    @ParameterizedTest(name = "{0}: total {1}, final {2} gives {3}")
    @CsvSource({
        "BB-02, 0, 0, F",
        "BB-03, 39, 25, F",
        "BB-04, 40, 25, D",
        "BB-05, 79, 45, A",
        "BB-06, 80, 45, A+",
        "BB-07, 100, 60, A+",
        "BB-09, 50, 14, F",
        "BB-10, 55, 15, B-"
    })
    void gradeAtTheBoundaries(String id, int total, String finalMark, String letter) {
        assertEquals(letter, policy.gradeFor(total, withFinal(finalMark)).letter());
    }

    @ParameterizedTest(name = "{0}: total {1} is rejected")
    @CsvSource({"BB-01, -1", "BB-08, 101"})
    void totalOutsideTheRange(String id, int total) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> policy.gradeFor(total, withFinal("30")));
        assertEquals("total must be from 0 to 100", e.getMessage());
    }

    @ParameterizedTest(name = "total {0} gives {1}")
    @CsvSource({"80, A+", "75, A", "70, A-", "65, B+", "60, B", "55, B-", "50, C+", "45, C", "40, D", "39, F"})
    void allTenGradeBoundaries(int total, String letter) {
        assertEquals(letter, Grade.forTotal(total).letter());
    }
}
