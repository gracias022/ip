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

    public Task markAsDone(int index) throws IndexOutOfBoundsException {
        int zeroBasedIndex = getZeroBasedIndex(index);
        Task task = tasks.get(zeroBasedIndex);
        task.markAsDone();
        return task;
    }

    public Task unmark(int index) throws IndexOutOfBoundsException {
        int zeroBasedIndex = getZeroBasedIndex(index);
        Task task = tasks.get(zeroBasedIndex);
        task.unmark();
        return task;
    }

    public Task deleteTask(int index) throws IndexOutOfBoundsException {
        int zeroBasedIndex = getZeroBasedIndex(index);
        Task t = tasks.get(zeroBasedIndex);
        tasks.remove(zeroBasedIndex);
        return t;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    private int getZeroBasedIndex(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > tasks.size()) {
            throw new IndexOutOfBoundsException();
        }
        return index - 1;
    }
}