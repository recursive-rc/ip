package ittybotty;

public class EmptyDescriptionException extends RuntimeException {
    private final String taskType;

    public EmptyDescriptionException(String taskType) {
        this.taskType = taskType;
    }

    public EmptyDescriptionException(String message, String taskType) {
        super(message);
        this.taskType = taskType;
    }

    public String getTaskType() {
        return this.taskType;
    }
}
