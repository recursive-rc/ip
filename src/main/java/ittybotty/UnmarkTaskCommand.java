package ittybotty;

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
        return "ittybotty.UnmarkTaskCommand{" +
                "taskIndex=" + taskIndex +
                '}';
    }
}
