package ittybotty.commands;

/**
 * Represents a user command to unmark a particular task so that it
 * is labelled as undone.
 */
public class UnmarkTaskCommand extends UserCommand {
    private final int taskIndex;

    /**
     * Constructs a user command to unmark a particular task so that it
     * is labelled as undone.
     * @param taskIndex Index of the task to unmark (1-indexed).
     */
    public UnmarkTaskCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Returns index (1-indexed) of the task that the user wishes to
     * unmark.
     */
    public int getTaskIndex() {
        return taskIndex;
    }
    
    @Override
    public String toString() {
        return "ittybotty.commands.UnmarkTaskCommand{" +
                "taskIndex=" + taskIndex +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof UnmarkTaskCommand that)) {
            return false;
        }

        return this.taskIndex == that.taskIndex;
    }

    @Override
    public int hashCode() {
        return this.taskIndex;
    }
}
