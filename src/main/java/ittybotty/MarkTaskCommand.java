package ittybotty;

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
        return "ittybotty.MarkTaskCommand{" +
                "taskIndex=" + taskIndex +
                '}';
    }
}
