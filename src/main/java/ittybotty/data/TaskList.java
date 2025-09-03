package ittybotty.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ittybotty.data.tasks.Task;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty list of tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Constructs a list of tasks from the given list.
     *
     * <p>Does not create a deep copy of the given list.</p>
     * @param tasks List of tasks to store.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Add a task to the end of the list.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Add all tasks from the given collection to this list.
     * @param c Collection of tasks to add.
     * @return true if the list is changed as a result of
     *         calling this method.
     */
    public boolean addAll(Collection<? extends Task> c) {
        return tasks.addAll(c);
    }

    /**
     * Removes task at the given index (1-indexed) from the list.
     * @param index Index of the task to be deleted (1-indexed).
     * @return The task that was removed.
     */
    public Task removeTask(int index) {
        return this.tasks.remove(index - 1); // `- 1` because 1-indexed
    }

    /**
     * Returns the task at the given index (0-indexed).
     * @param index Index of the task to return (0-indexed).
     */
    public Task getTaskAt(int index) {
        return tasks.get(index);
    }

    /**
     * Marks the task at given index (1-indexed) as complete.
     * @param index Index of task to be marked (1-indexed).
     * @return The task marked as complete.
     */
    public Task markTask(int index) {
        Task markedTask = this.tasks.get(index - 1); // `- 1` because 1-indexed
        markedTask.markDone();
        return markedTask;
    }

    /**
     * Returns number of tasks in the list.
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Unmarks the task at the given index (1-indexed), so that
     * it is no longer marked as complete.
     * @param index Index of task to be unmarked (1-indexed).
     * @return The task unmarked.
     */
    public Task unmarkTask(int index) {
        Task unmarkedTask = this.tasks.get(index - 1); // `- 1` because 1-indexed
        unmarkedTask.unmarkDone();
        return unmarkedTask;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Each task is printed in a separate line, prefixed with the
     * task's index followed by a period (such as "2." for the
     * 2nd task).</p>
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            int taskNum = i + 1;
            builder.append(taskNum).append(". ")
                    .append(this.tasks.get(i));
            if (taskNum != this.tasks.size()) {
                builder.append('\n');
            }
        }
        return builder.toString();
    }

    public String toCsvString() {
        StringBuilder builder = new StringBuilder();
        for (Task task : this.tasks) {
            builder.append(task.toCsvString()).append('\n');
        }
        return builder.toString();
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof TaskList taskList)) {
            return false;
        }

        return this.tasks.equals(taskList.tasks);
    }

    @Override
    public int hashCode() {
        return this.tasks.hashCode();
    }
}
