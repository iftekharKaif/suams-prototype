package bd.ac.ruet.suams.registration;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Registration calendar under rule R1. A cycle is one week counted from the first
 * day of the semester, and registration is open in cycles 1 and 2.
 */
public class AcademicCalendar {
    /** Ordinance 11.5: Tk 500 for each cycle of late registration. */
    public static final int LATE_FEE_TAKA = 500;

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

    /** Late fee in taka (rule R1): nothing in cycle 1, Tk 500 in cycle 2. */
    public int lateFee(LocalDate date) {
        return cycleOf(date) == 2 ? LATE_FEE_TAKA : 0;
    }
}
