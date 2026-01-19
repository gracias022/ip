public class TaskList {
    private String[] tasks = new String[100];
    private int taskCount = 0;

    public void add(String text) {
        if (taskCount < 100) {
            tasks[taskCount] = text;
            taskCount++;
        }
    }

    public void list() {
        if (taskCount == 0) {
            System.out.println("  No tasks yet. Add one now!");
        }
        for (int i = 0; i < taskCount; i++) {
            System.out.println("  " + (i + 1) + ". " + tasks[i]);
        }
    }
}