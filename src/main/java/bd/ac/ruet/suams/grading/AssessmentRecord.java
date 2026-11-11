package bd.ac.ruet.suams.grading;

import java.math.BigDecimal;
import java.util.List;

/** Marks of one student in one theory course (rule R5). */
public record AssessmentRecord(List<BigDecimal> classTests, BigDecimal attendance,
                               BigDecimal assignment, BigDecimal finalMark) {

    public AssessmentRecord {
        classTests = List.copyOf(classTests);
    }
}
