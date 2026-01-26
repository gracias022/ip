import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getAllTasks() {
        return this.tasks;
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task markAsDone(int index) {
        Task task = tasks.get(index);
        task.markAsDone();
        return task;
    }

    public Task unmark(int index) {
        Task task = tasks.get(index);
        task.unmark();
        return task;
    }

    public Task deleteTask(int index) {
        Task t = tasks.get(index);
        tasks.remove(index);
        return t;
    }

    public int getTaskCount() {
        return tasks.size();
    }
}