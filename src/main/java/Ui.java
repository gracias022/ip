import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        showLine();
        System.out.println("  Hello! I'm Miffy ^_^");
        System.out.println("  What can I do for you?");
        showLine();
    }

    public void showGoodbye() {
        System.out.println("  Bye. Hope to see you again soon!");
    }

    /**
     * Reads the next line of input from the user.
     *
     * @return Trimmed input string.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void closeScanner() {
        scanner.close();
    }

    public void showList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("  No tasks yet. Add one now!");
            return;
        }

        System.out.println("  Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + tasks.get(i));
        }
    }

    public void showLoadingError() {
        System.out.println("Oops! I had trouble loading your saved tasks.");
        System.out.println("We'll start fresh for now.");
    }

    public void showError(String errorMsg) {
        System.out.println("  " + errorMsg);
    }

    public void showOpsConfirmation(Task t, String action, int taskCount) {
        System.out.println("  " + action + " this task:");
        System.out.println("  " + t);
        System.out.printf("  Now you have %d %s in the list.\n", taskCount,
                taskCount != 1 ? "tasks" : "task");
    }

    public void showTaskStatusChanged(Task task) {
        String message = task.isDone() ? "Nice! I've marked this as done:"
                : "OK, I've marked this as not done:";
        System.out.println("  " + message);
        System.out.println("  " + task);
    }

    public void showLine() {
        System.out.println("  ________________________________________________________________________________");
    }
}
