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
}
