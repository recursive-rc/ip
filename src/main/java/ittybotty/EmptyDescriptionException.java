package ittybotty;

/**
 * Represents an error when user input for adding a task
 * is missing a description or has an empty one.
 */
public class EmptyDescriptionException extends RuntimeException {
    private final String taskType;

    /**
     * Constructs a new exception to indicate empty task description
     * in user input.
     *
     * @param taskType The type of task added as a String.
     */
    public EmptyDescriptionException(String taskType) {
        this.taskType = taskType;
    }

    /**
     * Constructs a new exception to indicate empty task description
     * in user input, with a custom message.
     *
     * @param message The detail message, for retrieval using
     *                {@link EmptyDescriptionException#getMessage()}.
     * @param taskType The type of task added as a String.
     */
    public EmptyDescriptionException(String message, String taskType) {
        super(message);
        this.taskType = taskType;
    }

    public String getTaskType() {
        return this.taskType;
    }
}
