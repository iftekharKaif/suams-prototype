package bd.ac.ruet.suams.registration;

import java.math.BigDecimal;
import java.util.List;

/** A course from the approved curriculum, with the courses it builds on. */
public record Course(String code, String title, BigDecimal credits, List<String> prerequisites) {

    public Course {
        prerequisites = List.copyOf(prerequisites);
    }
}
