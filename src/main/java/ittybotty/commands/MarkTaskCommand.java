package ittybotty.commands;

/**
 * Represents a user command to mark a particular task as done.
 */
public final class MarkTaskCommand extends UserCommand {
    private final int taskIndex;

    /**
     * Constructs a user command to mark a particular task
     * as done.
     * @param taskIndex Index of the task to mark (1-indexed).
     */
    public MarkTaskCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Returns index (1-indexed) of the task that the user wishes to
     * mark as done.
     */
    public int getTaskIndex() {
        return taskIndex;
    }

    @Override
    public String toString() {
        return "ittybotty.commands.MarkTaskCommand{"
                + "taskIndex=" + taskIndex
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MarkTaskCommand that)) {
            return false;
        }

        return this.taskIndex == that.taskIndex;
    }

    @Override
    public int hashCode() {
        return this.taskIndex;
    }
}
