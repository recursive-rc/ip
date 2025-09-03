package ittybotty.data;

import ittybotty.data.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public boolean addAll(Collection<? extends Task> c) {
        return tasks.addAll(c);
    }

    /**
     * Removes task at the given index (1-indexed) from the list.
     * @param index Index of the task to be deleted (1-indexed).
     * @return The task that was removed.
     */
    public Task removeTask(int index) {
        return this.tasks.remove(index - 1);  // `- 1` because 1-indexed
    }

    public Task getTaskAt(int index) {
        return tasks.get(index);
    }

    /**
     * Marks the task at given index (1-indexed) as complete.
     * @param index Index of task to be marked (1-indexed).
     * @return The task marked as complete.
     */
    public Task markTask(int index) {
        Task markedTask = this.tasks.get(index - 1);  // `- 1` because 1-indexed
        markedTask.markDone();
        return markedTask;
    }

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
        Task unmarkedTask = this.tasks.get(index - 1);  // `- 1` because 1-indexed
        unmarkedTask.unmarkDone();
        return unmarkedTask;
    }

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

    public List<Task> getTasksMatching(String searchTerm) {
        return this.tasks.stream()
                .filter(t -> t.getName().contains(searchTerm))
                .toList();
    }

    /**
     * Returns a string representation of the task that
     * includes its index (in the list, 1-indexed) at the start.
     *
     * <p>For use when printing a subset of the list or when
     * printing the list in a different order, so that user
     * can see the correct index for commands such as mark/delete.</p>
     *
     * @param task Task to print. Must have already been added to the
     *             list.
     * @throws RuntimeException If given task is not inside the task
     *                          list.
     */
    public String getTaskWithIndex(Task task) {
        if (!this.tasks.contains(task)) {
            throw new RuntimeException("Task " + task + " not in task list.");
            // TODO: consider creating a new exception subclass
        }

        int taskIndex = this.tasks.indexOf(task) + 1; // 1-indexed
        assert taskIndex > 0;
        return taskIndex + ". " + task;
    }
}
