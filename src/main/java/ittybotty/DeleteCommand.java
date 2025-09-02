package ittybotty;

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
        return "ittybotty.DeleteCommand{" +
                "taskIndex=" + this.taskIndex +
                '}';
    }
}
