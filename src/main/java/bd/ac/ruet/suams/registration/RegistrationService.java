package bd.ac.ruet.suams.registration;

import java.time.LocalDate;

/** Course Registration: adds one course to a student's registration (rules R1, R2 and R4). */
public class RegistrationService {
    private final AcademicCalendar calendar;
    private final StudentRecords records;
    private final PrerequisiteChecker prereq;
    private final CommitteePermissions committee;
    private final EnrollmentRepository enrollments;

    public RegistrationService(AcademicCalendar calendar, StudentRecords records, PrerequisiteChecker prereq,
                               CommitteePermissions committee, EnrollmentRepository enrollments) {
        this.calendar = calendar;
        this.records = records;
        this.prereq = prereq;
        this.committee = committee;
        this.enrollments = enrollments;
    }

    public RegResult addCourse(Student s, Section sec, LocalDate today) {
        if (!calendar.isRegistrationOpen(today))          // cycles 1 and 2 only
            return RegResult.reject("REGISTRATION_CLOSED");
        if (records.hasOutstandingDues(s))
            return RegResult.reject("DUES_OUTSTANDING");
        Course c = sec.course();
        if (!prereq.hasPassed(s, c.prerequisites())
                && !committee.hasPermission(s, c))         // Department Monitoring Committee
            return RegResult.reject("PREREQUISITE_NOT_MET");
        if (sec.seatsTaken() >= sec.capacity())
            return RegResult.waitlist(sec.joinWaitingList(s));
        Enrollment e = enrollments.save(new Enrollment(s, sec, calendar.lateFee(today)));
        return RegResult.enrolled(e);
    }
}
