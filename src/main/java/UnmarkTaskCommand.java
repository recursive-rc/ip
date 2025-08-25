public class UnmarkTaskCommand extends UserCommand {
    private final int taskIndex;
    
    public UnmarkTaskCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }
    
    @Override
    public String toString() {
        return "UnmarkTaskCommand{" +
                "taskIndex=" + taskIndex +
                '}';
    }
}
