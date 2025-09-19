package ittybotty.commands;

/**
 * Represents a command from the user to delete a particular
 * task.
 */
public class DeleteCommand extends UserCommand {
    private int taskIndex;

    /**
     * Constructs a user command to delete a particular task.
     * @param taskIndex Index of the task to delete (1-indexed).
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Returns index (1-indexed) of the task that the user wishes to
     * delete.
     */
    public int getTaskIndex() {
        return this.taskIndex;
    }

    @Override
    public String toString() {
        return "ittybotty.commands.DeleteCommand{"
                + "taskIndex=" + this.taskIndex
                + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof DeleteCommand that)) {
            return false;
        }

        return this.taskIndex == that.taskIndex;
    }

    @Override
    public int hashCode() {
        return this.taskIndex;
    }
}
