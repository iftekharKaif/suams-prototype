package bd.ac.ruet.suams.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Issue #8: the late fee of rule R1. The semester starts on Saturday 2 January 2027. */
class LateFeeTest {
    private final AcademicCalendar calendar = new AcademicCalendar(LocalDate.of(2027, 1, 2));

    @Test
    @DisplayName("First cycle: registration is open with no fee")
    void firstCycle() {
        LocalDate day = LocalDate.of(2027, 1, 4);
        assertTrue(calendar.isRegistrationOpen(day));
        assertEquals(0, calendar.lateFee(day));
    }

    @Test
    @DisplayName("BB-12: the second cycle is accepted with a Tk 500 fee")
    void secondCycle() {
        LocalDate day = LocalDate.of(2027, 1, 10);
        assertTrue(calendar.isRegistrationOpen(day));
        assertEquals(500, calendar.lateFee(day));
    }

    @Test
    @DisplayName("Boundary: day 7 has no fee and day 8 has the fee")
    void cycleBoundary() {
        assertEquals(0, calendar.lateFee(LocalDate.of(2027, 1, 8)));
        assertEquals(500, calendar.lateFee(LocalDate.of(2027, 1, 9)));
    }

    @Test
    @DisplayName("BB-14: the third cycle is closed")
    void thirdCycle() {
        assertFalse(calendar.isRegistrationOpen(LocalDate.of(2027, 1, 16)));
    }

    @Test
    @DisplayName("BB-13: registration is not open before the semester starts")
    void beforeTheSemester() {
        assertFalse(calendar.isRegistrationOpen(LocalDate.of(2027, 1, 1)));
    }
}
