package bd.ac.ruet.suams.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bd.ac.ruet.suams.grading.Grade;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** White-box tests WB-01 to WB-06: one test per basis path of addCourse. */
class RegistrationServiceBasisPathTest {
    private static final LocalDate FIRST_DAY = LocalDate.of(2027, 1, 2);            // a Saturday
    private static final LocalDate LAST_DAY_OF_CYCLE_2 = FIRST_DAY.plusDays(13);
    private static final LocalDate FIRST_DAY_OF_CYCLE_3 = FIRST_DAY.plusDays(14);

    private final Student stu = new Student("stu-001");
    private final Course se = new Course("CSE 3205", "Software Engineering", new BigDecimal("3.00"),
            List.of("CSE 1203"));

    private boolean dues;                    // outstanding dues of stu-001
    private Grade oop = Grade.B;             // grade of stu-001 in CSE 1203
    private boolean permission;              // Department Monitoring Committee
    private int duesChecks;
    private int prerequisiteChecks;

    private RegistrationService service() {
        StudentRecords records = s -> { duesChecks++; return dues; };
        TranscriptRepository transcripts = (s, code) -> { prerequisiteChecks++; return Optional.of(oop); };
        CommitteePermissions committee = (s, c) -> permission;
        return new RegistrationService(new AcademicCalendar(FIRST_DAY), records,
                new PrerequisiteChecker(transcripts), committee, new InMemoryEnrollmentRepository());
    }

    private Section sectionWith(int seatsTaken) {
        return new Section(se, "A", 60, seatsTaken);
    }

    @Test
    @DisplayName("WB-01, path 1-3-5-8-10-11: every check passes and a seat is free")
    void wb01() {
        Section sec = sectionWith(42);
        RegResult r = service().addCourse(stu, sec, LAST_DAY_OF_CYCLE_2);
        assertEquals(RegResult.Status.ENROLLED, r.status());
        assertEquals(43, sec.seatsTaken());
    }

    @Test
    @DisplayName("WB-02, path 1-2-11: registration has closed")
    void wb02() {
        RegResult r = service().addCourse(stu, sectionWith(42), FIRST_DAY_OF_CYCLE_3);
        assertEquals("REGISTRATION_CLOSED", r.reason());
        assertEquals(0, duesChecks);
        assertEquals(0, prerequisiteChecks);
    }

    @Test
    @DisplayName("WB-03, path 1-3-4-11: the student has outstanding dues")
    void wb03() {
        dues = true;
        RegResult r = service().addCourse(stu, sectionWith(42), LAST_DAY_OF_CYCLE_2);
        assertEquals("DUES_OUTSTANDING", r.reason());
    }

    @Test
    @DisplayName("WB-04, path 1-3-5-6-8-10-11: prerequisite failed, the committee permits the course")
    void wb04() {
        oop = Grade.F;
        permission = true;
        Section sec = sectionWith(42);
        RegResult r = service().addCourse(stu, sec, LAST_DAY_OF_CYCLE_2);
        assertEquals(RegResult.Status.ENROLLED, r.status());
        assertEquals(43, sec.seatsTaken());
    }

    @Test
    @DisplayName("WB-05, path 1-3-5-6-7-11: prerequisite failed and no permission")
    void wb05() {
        oop = Grade.F;
        RegResult r = service().addCourse(stu, sectionWith(42), LAST_DAY_OF_CYCLE_2);
        assertEquals("PREREQUISITE_NOT_MET", r.reason());
    }

    @Test
    @DisplayName("WB-06, path 1-3-5-8-9-11: the section is full")
    void wb06() {
        Section sec = sectionWith(60);
        RegResult r = service().addCourse(stu, sec, LAST_DAY_OF_CYCLE_2);
        assertEquals(RegResult.Status.WAITLISTED, r.status());
        assertEquals(1, r.waitingPosition());
        assertEquals(60, sec.seatsTaken());
    }
}
