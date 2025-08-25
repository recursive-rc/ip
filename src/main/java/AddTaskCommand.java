public final class AddTaskCommand extends UserCommand {
    private final String taskName;
    
    public AddTaskCommand(String taskName) {
        this.taskName = taskName;
    }
    
    @Override
    public String toString() {
        return "AddTaskCommand{" +
                "taskName='" + taskName + '\'' +
                '}';
    }
}
