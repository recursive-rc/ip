package ittybotty.commands;

import java.io.IOException;

import ittybotty.OutputFormatter;
import ittybotty.SaveFileManager;
import ittybotty.data.TaskList;
import ittybotty.data.tasks.Task;

public class SortCommand extends UserCommand {
    private Task.KeyForComparison keyForComparison;

    public SortCommand(Task.KeyForComparison keyForComparison) {
        this.keyForComparison = keyForComparison;
    }

    public Task.KeyForComparison getKeyForComparison() {
        return keyForComparison;
    }

    @Override
    public String toString() {
        return "SortCommand{"
                + "keyForComparison=" + this.keyForComparison
                + '}';
    }

    // The equals method is overridden for JUnit testing
    // to ensure JUnit's assertEquals() works as intended.
    // The hasCode() method is thus also overridden per
    // the general contract for Object::hasCode.

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof SortCommand that)) {
            return false;
        }

        return this.keyForComparison == that.keyForComparison;
    }

    @Override
    public int hashCode() {
        return this.keyForComparison.hashCode();
    }

    @Override
    public CommandResult run(TaskList taskList, OutputFormatter formatter,
                             SaveFileManager saveManager) {
        taskList.sort(this.keyForComparison);
        String botOutput = "Task list sorted!\nSorted list:\n" + taskList;

        try {
            saveManager.saveToFile(taskList);
        } catch (IOException e) {
            botOutput += formatter.getFileIoErrorMessage();
        }

        return new CommandResult(botOutput);
    }
}
