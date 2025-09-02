package ittybotty.data.tasks;

import ittybotty.OutputFormatter;

import java.time.LocalDate;

public class Event extends Task {
    private LocalDate startDate;
    private LocalDate endDate;
    // TODO: convert the above to proper date/time types
    
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
}
