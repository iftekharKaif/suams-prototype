package bd.ac.ruet.suams.registration;

/** Permissions given by the Department Monitoring Committee (rule R2). */
public interface CommitteePermissions {
    boolean hasPermission(Student s, Course c);
}
