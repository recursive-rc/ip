package ittybotty.commands;

public class UnmarkTaskCommand extends UserCommand {
    private final int taskIndex;

    public UnmarkTaskCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

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
