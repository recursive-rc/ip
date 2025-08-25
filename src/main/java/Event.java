public class Event extends Task {
    private String startDateTime;
    private String endDateTime;
    // TODO: convert the above to proper date/time types
    
    public Event(String name, String startDateTime, String endDateTime) {
        super(name);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
    
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.startDateTime +
                " to: " + this.endDateTime;
    }
}
