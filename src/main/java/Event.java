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
        return "[E]" + super.toString() + " (from: " + this.startDate +
                " to: " + this.endDate + ")";
    }

    @Override
    public String toCsvString() {
        return String.format("E,%s,\"%s\",\"%s\"", super.toCsvString(),
                this.startDate, this.endDate);
    }
}
