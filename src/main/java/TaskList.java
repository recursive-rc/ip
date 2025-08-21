import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;
    
    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    
    public void addTask(Task newTask) {
        this.tasks.add(newTask);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            int taskNum = i + 1;
            builder.append(taskNum).append(". ")
                    .append(tasks.get(i));
            if (taskNum != tasks.size()) {
                builder.append('\n');
            }
        }
        return builder.toString();
    }
}
