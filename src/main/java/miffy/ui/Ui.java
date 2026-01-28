package miffy.ui;

import miffy.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user interactions in the Miffy application.
 * <p>
 * Provides methods to display messages, read input, and print task lists.
 */
public class Ui {
    private final Scanner scanner;

    public static final String DEADLINE_USAGE =
            "Usage: deadline <desc> /by <yyyy-MM-dd HHmm>\n" +
                    "E.g. deadline return book /by 2026-01-16 1800";

    public static final String EVENT_USAGE =
            "Usage: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>\n" +
                    "E.g. event meeting /from 2026-01-16 1400 /to 2026-01-16 1600";

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints the welcome message.
     */
    public void showWelcome() {
        showLine();
        System.out.println("  Hello! I'm Miffy ^_^");
        System.out.println("  What can I do for you?");
        showLine();
    }

    /**
     * Prints the goodbye message.
     */
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

    /**
     * Closes the scanner used for reading user input.
     */
    public void closeScanner() {
        scanner.close();
    }

    /**
     * Displays a numbered list of tasks.
     *
     * @param tasks List of tasks to display.
     */
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

    /**
     * Prints a message when loading tasks from storage fails.
     */
    public void showLoadingError() {
        System.out.println("Oops! I had trouble loading your saved tasks.");
        System.out.println("We'll start fresh for now.");
    }

    /**
     * Prints a custom error message to the console.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        String[] lines = message.split("\n");

        // Print each line with a 2-space padding
        for (String line : lines) {
            System.out.println("  " + line);
        }
    }

    /**
     * Confirms that an operation (add/delete) has been performed on a task.
     *
     * @param t Task affected.
     * @param action Action performed.
     * @param taskCount Current number of tasks in list.
     */
    public void showOpsConfirmation(Task t, String action, int taskCount) {
        System.out.println("  " + action + " this task:");
        System.out.println("  " + t);
        System.out.printf("  Now you have %d %s in the list.\n", taskCount,
                taskCount != 1 ? "tasks" : "task");
    }

    /**
     * Prints a confirmation message after a task has been marked or unmarked.
     *
     * @param task Task whose completion status has changed.
     */
    public void showTaskStatusChanged(Task task) {
        String message = task.isDone() ? "Nice! I've marked this as done:"
                : "OK, I've marked this as not done:";
        System.out.println("  " + message);
        System.out.println("  " + task);
    }

    /**
     * Displays tasks whose descriptions match the given keyword.
     *
     * @param matches List of matching tasks.
     */
    public void showFindResults(ArrayList<Task> matches) {
        if (matches.isEmpty()) {
            System.out.println("  Oops! No matching tasks found :(");
            return;
        }

        System.out.println("  Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + matches.get(i));
        }
    }

    /**
     * Prints a horizontal line separator.
     */
    public void showLine() {
        System.out.println("  ________________________________________________________________________________");
    }
}
