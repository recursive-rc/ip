package ittybotty;

public final class AddTaskCommand extends UserCommand {
    private final Task taskToAdd;
    
    public AddTaskCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }
    
    public Task getTask() {
        return this.taskToAdd;
    }
    
    @Override
    public String toString() {
        return "ittybotty.AddTaskCommand{" +
                "taskToAdd='" + this.taskToAdd + '\'' +
                '}';
    }
}
