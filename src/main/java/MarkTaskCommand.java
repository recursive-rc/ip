public final class MarkTaskCommand extends UserCommand {
    private final int taskIndex;
    
    public MarkTaskCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }
    
    @Override
    public String toString() {
        return "MarkTaskCommand{" +
                "taskIndex=" + taskIndex +
                '}';
    }
}
