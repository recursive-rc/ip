package ittybotty.data.tasks;

import java.util.Comparator;

/**
 * Represents a task that is either done or yet to be done.
 */
public abstract class Task {
    private static final String DONE_CHECKBOX = "[X]";
    private static final String UNDONE_CHECKBOX = "[ ]";

    private static final Comparator<Task> ALPHABETICAL_COMPARATOR =
            Comparator.comparing(task -> task.name.toLowerCase());
    // Must convert to lower case, because String::compareTo uses
    // lexicographical order, not alphabetical order,
    // so "BBBB" will be considered "smaller" than "aaaa"

    private final String name;
    private boolean isDone;

    /**
     * Constructs a task with the given description.
     *
     * <p>By default, the task is marked as undone.</p>
     * @param name Description of the task.
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Marks the task as done.
     *
     * @throws IllegalStateException If the task is already marked
     *                               as done.
     */
    public void markDone() {
        if (this.isDone) {
            throw new IllegalStateException("ittybotty.data.tasks.Task " + this.name
                    + " is already marked as done.");
        }
        this.isDone = true;
    }

    /**
     * Unmarks the task, so that it is stored as yet to be done
     * or undone.
     *
     * @throws IllegalStateException If the task was undone before
     *                               this method was called.
     */
    public void unmarkDone() {
        if (!this.isDone) {
            throw new IllegalStateException("ittybotty.data.tasks.Task " + this.name
                    + " has not been marked as done.");
        }
        this.isDone = false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The string contains a checkbox indicating if the task
     * has been done, followed by its description.</p>
     */
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

    /**
     * Returns a string representation of the task to be
     * stored in a CSV file, so that it can be easily
     * parsed later to reconstruct an equivalent task object.
     *
     * <p>Uses commas as delimiters and double quotes
     * to allow commas and other special characters in
     * the task description. Currently, it does <strong>not</strong>
     * properly handle double quotes in task description.</p>
     */
    public String toCsvString() {
        return String.format("%b,\"%s\"", this.isDone, this.getName());
        // This method and its counterparts in subclass that override
        // it assume that there are no quotes in task descriptions or
        // dates/times
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) {
            return false;
        }

        return this.isDone == task.isDone && this.name.equals(task.name);
    }

    @Override
    public int hashCode() {
        int result = this.name.hashCode();
        result = 31 * result + Boolean.hashCode(this.isDone);
        return result;
    }
}
