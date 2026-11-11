package bd.ac.ruet.suams.registration;

import java.util.List;

/** Stores enrollments; the real system keeps them in PostgreSQL. */
public interface EnrollmentRepository {
    Enrollment save(Enrollment e);

    List<Enrollment> findAll();
}
