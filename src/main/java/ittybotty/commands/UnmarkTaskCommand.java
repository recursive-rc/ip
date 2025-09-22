package ittybotty.commands;

import java.io.IOException;

import ittybotty.OutputFormatter;
import ittybotty.SaveFileManager;
import ittybotty.data.TaskList;
import ittybotty.data.tasks.Task;

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
        return "ittybotty.commands.UnmarkTaskCommand{"
                + "taskIndex=" + taskIndex
                + '}';
    }

    // The equals method is overridden for JUnit testing
    // to ensure JUnit's assertEquals() works as intended.
    // The hasCode() method is thus also overridden per
    // the general contract for Object::hasCode.

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

    @Override
    public CommandResult run(TaskList taskList, OutputFormatter formatter,
                             SaveFileManager saveManager) {
        Task taskToUnmark = taskList.unmarkTask(this.taskIndex);
        // TODO: handle IndexOutOfBoundsException
        String botOutput = "Alright, The task below has been unmarked!\n"
                + taskToUnmark;

        try {
            saveManager.saveToFile(taskList);
        } catch (IOException e) {
            botOutput += formatter.getFileIoErrorMessage();
        }

        return new CommandResult(botOutput);
    }
}
