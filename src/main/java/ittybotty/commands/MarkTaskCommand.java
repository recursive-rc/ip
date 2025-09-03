package ittybotty.commands;

public final class MarkTaskCommand extends UserCommand {
    private final int taskIndex;
    
    public MarkTaskCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }
    
    public int getTaskIndex() {
        return taskIndex;
    }
    
    @Override
    public String toString() {
        return "ittybotty.commands.MarkTaskCommand{" +
                "taskIndex=" + taskIndex +
                '}';
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
