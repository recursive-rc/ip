package ittybotty.commands;

public class DeleteCommand extends UserCommand {
    private int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

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
