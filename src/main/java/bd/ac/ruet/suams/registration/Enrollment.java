package bd.ac.ruet.suams.registration;

/** One student in one section, with the late fee charged in taka (rule R1). */
public record Enrollment(Student student, Section section, int lateFee) { }
