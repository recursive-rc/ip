package ittybotty.data.tasks;

/**
 * Represents a task without a deadline nor any other
 * dates/times associated with it.
 */
public class ToDo extends Task {
    /**
     * Constructs a task without a deadline nor any other
     *  * dates/times associated with it.
     * @param name The ToDo description.
     */
    public ToDo(String name) {
        super(name);
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
