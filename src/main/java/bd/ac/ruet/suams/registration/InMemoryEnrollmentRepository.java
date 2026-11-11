package bd.ac.ruet.suams.registration;

import java.util.ArrayList;
import java.util.List;

/** Stand-in for the PostgreSQL repository. Saving an enrollment also takes the seat. */
public class InMemoryEnrollmentRepository implements EnrollmentRepository {
    private final List<Enrollment> rows = new ArrayList<>();

    @Override
    public Enrollment save(Enrollment e) {
        e.section().takeSeat();
        rows.add(e);
        return e;
    }

    @Override
    public List<Enrollment> findAll() {
        return List.copyOf(rows);
    }
}
