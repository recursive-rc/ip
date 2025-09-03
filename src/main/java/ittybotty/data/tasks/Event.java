package ittybotty.data.tasks;

import ittybotty.OutputFormatter;

import java.time.LocalDate;

/**
 * Represents an event with a start and end date.
 */
public class Event extends Task {
    private LocalDate startDate;
    private LocalDate endDate;
    // TODO: convert the above to proper date/time types

    /**
     * Constructs an event.
     * @param name Event description.
     * @param startDate Event start date.
     * @param endDate Event end date.
     */
    public Event(String name, LocalDate startDate, LocalDate endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + OutputFormatter.formatDateForOutput(this.startDate) +
                " to: " + OutputFormatter.formatDateForOutput(this.endDate) + ")";
    }

    @Override
    public String toCsvString() {
        return String.format("E,%s,\"%s\",\"%s\"", super.toCsvString(),
                this.startDate, this.endDate);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Event event)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        return startDate.equals(event.startDate) && endDate.equals(event.endDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}
