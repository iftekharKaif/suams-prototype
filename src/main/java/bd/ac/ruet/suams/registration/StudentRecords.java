package bd.ac.ruet.suams.registration;

/** The part of the Student Records module that registration needs. */
public interface StudentRecords {
    boolean hasOutstandingDues(Student s);
}
