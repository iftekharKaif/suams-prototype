package bd.ac.ruet.suams.registration;

import java.util.ArrayList;
import java.util.List;

/** One section of a course with a fixed number of seats (rule R4). */
public class Section {
    private final Course course;
    private final String name;
    private final int capacity;
    private int seatsTaken;
    private final List<Student> waitingList = new ArrayList<>();

    public Section(Course course, String name, int capacity, int seatsTaken) {
        this.course = course;
        this.name = name;
        this.capacity = capacity;
        this.seatsTaken = seatsTaken;
    }

    public Course course() { return course; }
    public String name() { return name; }
    public int capacity() { return capacity; }
    public int seatsTaken() { return seatsTaken; }

    void takeSeat() { seatsTaken++; }

    /** Adds the student to the waiting list and returns the position, starting at 1. */
    public int joinWaitingList(Student s) {
        waitingList.add(s);
        return waitingList.size();
    }
}
