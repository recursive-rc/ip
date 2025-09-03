package ittybotty.data.tasks;

import ittybotty.OutputFormatter;

import java.time.LocalDate;

/**
 * Represents a task with a deadline.
 */
public class TaskWithDeadline extends Task {
    private final LocalDate deadline;

    /**
     * Constructs a task with the given deadline.
     *
     * @param name Task description.
     * @param deadline The deadline for the task.
     */
    public TaskWithDeadline(String name, LocalDate deadline) {
        super(name);
        this.deadline = deadline;
    }
    
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + OutputFormatter.formatDateForOutput(this.deadline)
                + ")";
        // TODO: change format of date output
    }

    @Override
    public String toCsvString() {
        return String.format("D,%s,\"%s\"", super.toCsvString(),
                this.deadline);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof TaskWithDeadline that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        return deadline.equals(that.deadline);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + deadline.hashCode();
        return result;
    }
}
