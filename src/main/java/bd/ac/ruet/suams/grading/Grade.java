package bd.ac.ruet.suams.grading;

import java.math.BigDecimal;

/** RUET letter grades and grade points (rule R6). */
public enum Grade {
    A_PLUS("A+", 80, "4.00"), A("A", 75, "3.75"), A_MINUS("A-", 70, "3.50"),
    B_PLUS("B+", 65, "3.25"), B("B", 60, "3.00"), B_MINUS("B-", 55, "2.75"),
    C_PLUS("C+", 50, "2.50"), C("C", 45, "2.25"), D("D", 40, "2.00"), F("F", 0, "0.00");

    private final String letter;
    private final int lowestTotal;
    private final BigDecimal point;

    Grade(String letter, int lowestTotal, String point) {
        this.letter = letter;
        this.lowestTotal = lowestTotal;
        this.point = new BigDecimal(point);
    }

    public String letter() { return letter; }

    public BigDecimal point() { return point; }

    /** Grade for a whole-number course total from 0 to 100. */
    public static Grade forTotal(int total) {
        if (total < 0 || total > 100) {
            throw new IllegalArgumentException("total must be from 0 to 100");
        }
        for (Grade g : values()) {
            if (total >= g.lowestTotal) {
                return g;
            }
        }
        return F;
    }
}
