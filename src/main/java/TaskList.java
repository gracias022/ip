public class TaskList {
    private Task[] tasks = new Task[100];
    private int taskCount = 0;

    public void add(Task t) {
        if (taskCount < 100) {
            tasks[taskCount] = t;
            taskCount++;
        }
    }

    public void list() {
        if (taskCount == 0) {
            System.out.println("  No tasks yet. Add one now!");
            return;
        }

        System.out.println("  Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("  " + (i + 1) + ". " + tasks[i]);
        }
    }

    public Task markAsDone(int index) throws IndexOutOfBoundsException {
        index--;
        if (index < 0 || index >= taskCount) {
            throw new IndexOutOfBoundsException();
        }
        Task task = tasks[index];
        task.markAsDone();
        return task;
    }

    public Task unmark(int index) throws IndexOutOfBoundsException {
        index--;
        if (index < 0 || index >= taskCount) {
            throw new IndexOutOfBoundsException();
        }
        Task task = tasks[index];
        task.unmark();
        return task;
    }

    public int getTaskCount() {
        return taskCount;
    }
}