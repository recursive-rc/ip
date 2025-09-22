package ittybotty.data.tasks;

import java.time.LocalDate;

/**
 * Represents a task without a deadline nor any other
 * dates/times associated with it.
 */
public class ToDo extends Task {
    /**
     * Constructs a task without a deadline nor any other
     * dates/times associated with it.
     * @param name The ToDo description.
     */
    public ToDo(String name) {
        super(name);
    }

    @Override
    public LocalDate getDateForComparison() {
        return LocalDate.MAX;
        // These tasks have no deadlines, so they are the least urgent.
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toCsvString() {
        return "T," + super.toCsvString();
    }
}
