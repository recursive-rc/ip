package ittybotty.commands;

import ittybotty.data.tasks.Task;

/**
 * Represents a user command to add a task.
 */
public final class AddTaskCommand extends UserCommand {
    private final Task taskToAdd;

    /**
     * Constructs a command to request to add a task.
     * @param taskToAdd Task the user wishes to add.
     */
    public AddTaskCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

    public Task getTask() {
        return this.taskToAdd;
    }

    @Override
    public String toString() {
        return "ittybotty.commands.AddTaskCommand{"
                + "taskToAdd='" + this.taskToAdd + '\''
                + '}';
    }

    // The equals method is overridden for JUnit testing
    // to ensure JUnit's assertEquals() works as intended.
    // The hasCode() method is thus also overridden per
    // the general contract for Object::hasCode.

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AddTaskCommand that)) {
            return false;
        }

        return this.taskToAdd.equals(that.taskToAdd);
    }

    @Override
    public int hashCode() {
        return this.taskToAdd.hashCode();
    }
}
