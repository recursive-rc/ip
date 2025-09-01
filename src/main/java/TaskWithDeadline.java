import java.time.LocalDate;

public class TaskWithDeadline extends Task {
    private final LocalDate deadline;
    
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
}
