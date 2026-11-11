package bd.ac.ruet.suams.grading;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Theory course under rule R5: attendance 10, class tests 20 (best three of four,
 * each marked out of 20), assignment 10 and semester final 60. A student with fewer
 * than 15 of the 60 final marks gets F whatever the total.
 */
public class TheoryCoursePolicy implements GradingPolicy {
    static final int CLASS_TESTS = 4;
    private static final BigDecimal TEST_MAX = new BigDecimal("20");
    private static final BigDecimal THREE = new BigDecimal("3");
    private static final BigDecimal FINAL_FLOOR = new BigDecimal("15");

    @Override
    public int courseTotal(AssessmentRecord r) {
        List<BigDecimal> marks = new ArrayList<>();
        for (BigDecimal m : r.classTests()) {                 // the loop tested by UT-03 to UT-07
            if (m.compareTo(TEST_MAX) > 0) {
                throw new InvalidMarkException("class-test mark " + plain(m) + " is above 20");
            }
            if (m.signum() < 0) {
                throw new InvalidMarkException("class-test mark " + plain(m) + " is below 0");
            }
            marks.add(m);
        }
        if (marks.size() < CLASS_TESTS) {
            throw new IncompleteAssessmentException(marks.size() + " of 4 class tests found");
        }
        if (marks.size() > CLASS_TESTS) {
            throw new InvalidAssessmentException("at most 4 class tests");
        }
        marks.sort(Comparator.reverseOrder());
        BigDecimal bestThree = marks.get(0).add(marks.get(1)).add(marks.get(2));
        BigDecimal classTests = bestThree.divide(THREE, 10, RoundingMode.HALF_UP);
        BigDecimal raw = classTests.add(r.attendance()).add(r.assignment()).add(r.finalMark());
        return raw.setScale(0, RoundingMode.HALF_UP).intValueExact();   // .5 is rounded up
    }

    @Override
    public Grade gradeFor(int total, AssessmentRecord r) {
        Grade byTotal = Grade.forTotal(total);                // rejects totals outside 0 to 100
        return r.finalMark().compareTo(FINAL_FLOOR) < 0 ? Grade.F : byTotal;
    }

    private static String plain(BigDecimal m) {
        return m.stripTrailingZeros().toPlainString();
    }
}
