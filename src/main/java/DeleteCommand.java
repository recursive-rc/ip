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
        return "DeleteCommand{" +
                "taskIndex=" + this.taskIndex +
                '}';
    }
}
