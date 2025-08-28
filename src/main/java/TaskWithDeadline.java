public class TaskWithDeadline extends Task {
    private String deadline;  // TODO: change to date/time type
    
    public TaskWithDeadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }
    
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }

    @Override
    public String toCsvString() {
        return String.format("D,%s,\"%s\"", super.toCsvString(),
                this.deadline);
    }
}
