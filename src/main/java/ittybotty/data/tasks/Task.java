package ittybotty.data.tasks;

public abstract class Task {
    private final String name;
    private boolean isDone;
    
    private static final String DONE_CHECKBOX = "[X]";
    private static final String UNDONE_CHECKBOX = "[ ]";
    
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void markDone() {
        if (this.isDone) {
            throw new IllegalStateException("ittybotty.data.tasks.Task " + this.name
                    + " is already marked as done.");
        }
        this.isDone = true;
    }
    
    public void unmarkDone() {
        if (!this.isDone) {
            throw new IllegalStateException("ittybotty.data.tasks.Task " + this.name
                    + " has not been marked as done.");
        }
        this.isDone = false;
    }
    
    @Override
    public String toString() {
        String checkbox;
        if (this.isDone) {
            checkbox = Task.DONE_CHECKBOX;
        } else {
            checkbox = Task.UNDONE_CHECKBOX;
        }
        return checkbox + " " + this.name;
    }

    public String toCsvString() {
        return String.format("%b,\"%s\"", this.isDone, this.getName());
        // This method and its counterparts in subclass that override
        // it assume that there are no quotes in task descriptions or
        // dates/times
    }
}
