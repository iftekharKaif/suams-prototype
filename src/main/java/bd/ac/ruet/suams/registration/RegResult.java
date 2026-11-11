package bd.ac.ruet.suams.registration;

/** Outcome of a registration request. */
public record RegResult(Status status, String reason, Enrollment enrollment, int waitingPosition) {

    public enum Status { ENROLLED, WAITLISTED, REJECTED }

    public static RegResult enrolled(Enrollment e) {
        return new RegResult(Status.ENROLLED, null, e, 0);
    }

    public static RegResult waitlist(int position) {
        return new RegResult(Status.WAITLISTED, null, null, position);
    }

    public static RegResult reject(String reason) {
        return new RegResult(Status.REJECTED, reason, null, 0);
    }
}
