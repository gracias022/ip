package miffy.task;

import java.util.ArrayList;

/**
 * Represents a mutable collection of {@link Task} objects.
 * <p>
 * Provides operations to add, remove, mark, unmark, retrieve and count tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list initialized with existing tasks.
     *
     * @param tasks Initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns all tasks in the list.
     *
     * @return List of tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return this.tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param t Task to add.
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index Zero-based index of the task.
     * @return Updated task.
     */
    public Task markAsDone(int index) {
        Task task = tasks.get(index);
        task.markAsDone();
        return task;
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index Zero-based index of the task.
     * @return Updated task.
     */
    public Task unmark(int index) {
        Task task = tasks.get(index);
        task.unmark();
        return task;
    }

    /**
     * Removes the task at the given index.
     *
     * @param index Zero-based index of the task.
     * @return Removed task.
     */
    public Task deleteTask(int index) {
        Task t = tasks.get(index);
        tasks.remove(index);
        return t;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Task count.
     */
    public int getTaskCount() {
        return tasks.size();
    }
}