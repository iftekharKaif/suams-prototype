package bd.ac.ruet.suams.grading;

import java.util.HashMap;
import java.util.Map;

/** Maps a curriculum and a course type to one grading policy. There is no default policy. */
public class PolicyRegistry {
    private final Map<String, GradingPolicy> policies = new HashMap<>();

    public PolicyRegistry register(String curriculum, String courseType, GradingPolicy policy) {
        policies.put(key(curriculum, courseType), policy);
        return this;
    }

    public GradingPolicy policyFor(String curriculum, String courseType) {
        GradingPolicy policy = policies.get(key(curriculum, courseType));
        if (policy == null) {
            throw new IllegalStateException("no grading policy for " + curriculum + " " + courseType);
        }
        return policy;
    }

    private static String key(String curriculum, String courseType) {
        return curriculum + "/" + courseType;
    }
}
