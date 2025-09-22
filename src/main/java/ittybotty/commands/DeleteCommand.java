package ittybotty.commands;

import java.io.IOException;

import ittybotty.OutputFormatter;
import ittybotty.SaveFileManager;
import ittybotty.data.TaskList;
import ittybotty.data.tasks.Task;

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

    // The equals method is overridden for JUnit testing
    // to ensure JUnit's assertEquals() works as intended.
    // The hasCode() method is thus also overridden per
    // the general contract for Object::hasCode.

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

    @Override
    public CommandResult run(TaskList taskList, OutputFormatter formatter,
                             SaveFileManager saveManager) {
        final Task deletedTask = taskList.removeTask(this.taskIndex);
        String botOutput = "Successfully deleted:\n" + deletedTask
                + "\nYou have " + taskList.size() + " tasks remaining.";
        try {
            saveManager.saveToFile(taskList);
        } catch (IOException e) {
            botOutput += formatter.getFileIoErrorMessage();
        }

        return new CommandResult(botOutput);
    }
}
