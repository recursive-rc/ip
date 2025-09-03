package ittybotty.commands;

import ittybotty.data.tasks.Task;

public final class AddTaskCommand extends UserCommand {
    private final Task taskToAdd;

    public AddTaskCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

    public Task getTask() {
        return this.taskToAdd;
    }

    @Override
    public String toString() {
        return "ittybotty.commands.AddTaskCommand{" +
                "taskToAdd='" + this.taskToAdd + '\'' +
                '}';
    }

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
