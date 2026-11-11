package bd.ac.ruet.suams.registration;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Registration calendar under rule R1. A cycle is one week counted from the first
 * day of the semester, and registration is open in cycles 1 and 2.
 */
public class AcademicCalendar {
    private final LocalDate firstDay;

    public AcademicCalendar(LocalDate firstDay) {
        this.firstDay = firstDay;
    }

    /** Cycle of the date: 1 for the first week of the semester, 0 before it starts. */
    public int cycleOf(LocalDate date) {
        if (date.isBefore(firstDay)) {
            return 0;
        }
        return (int) (ChronoUnit.DAYS.between(firstDay, date) / 7) + 1;
    }

    public boolean isRegistrationOpen(LocalDate date) {
        int cycle = cycleOf(date);
        return cycle == 1 || cycle == 2;
    }

    /** Late fee in taka. The fee rule is still open: issue #8. */
    public int lateFee(LocalDate date) {
        return 0;
    }
}
