package miffy.ui;

import java.util.ArrayList;
import java.util.Scanner;

import miffy.task.Task;

/**
 * Handles all user interactions in the Miffy application.
 * <p>
 * Provides methods to display messages, read input, and print task lists.
 */
public class Ui {
    public static final String DEADLINE_USAGE =
            "Usage: deadline <desc> /by <yyyy-MM-dd HHmm>\n"
                    + "E.g. deadline return book /by 2026-01-16 1800";

    public static final String EVENT_USAGE =
            "Usage: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>\n"
                    + "E.g. event meeting /from 2026-01-16 1400 /to 2026-01-16 1600";

    private final Scanner scanner;
    private String lastMessage;


    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints the welcome message.
     */
    public void showWelcome() {
        showLine();
        lastMessage = "  Hello! I'm Miffy ^_^\n  What can I do for you?";
        System.out.println(lastMessage);
        showLine();
    }

    /**
     * Prints the goodbye message.
     */
    public void showGoodbye() {
        lastMessage = "  Bye. Hope to see you again soon!";
        System.out.println(lastMessage);
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
    @SuppressWarnings("checkstyle:Regexp")
    public void showList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            lastMessage = "  No tasks yet. Add one now!";
            System.out.println(lastMessage);
            return;
        }

        StringBuilder sb = new StringBuilder("  Here are the tasks in your list:\n");

        for (int i = 0; i < tasks.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }

        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '\n') {
            sb.deleteCharAt(sb.length() - 1);
        }

        lastMessage = sb.deleteCharAt(sb.length() - 1).toString();;
        System.out.println(lastMessage);
    }

    /**
     * Prints a message when loading tasks from storage fails.
     */
    public void showLoadingError() {
        lastMessage = "Oops! I had trouble loading your saved tasks.\n\"We'll start fresh for now.";
        System.out.println(lastMessage);
    }

    /**
     * Prints a custom error message to the console.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        String[] lines = message.split("\n");

        StringBuilder sb = new StringBuilder();

        // Print each line with a 2-space padding
        for (String line : lines) {
            sb.append("  ").append(line).append("\n");
        }

        if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == '\n') {
            sb.deleteCharAt(sb.length() - 1);
        }

        lastMessage = sb.toString();
        System.out.println(lastMessage);
    }

    /**
     * Confirms that an operation (add/delete) has been performed on a task.
     *
     * @param t Task affected.
     * @param action Action performed.
     * @param taskCount Current number of tasks in list.
     */
    public void showOpsConfirmation(Task t, String action, int taskCount) {
        lastMessage = "  " + action + " this task:\n"
                + "  " + t + "\n"
                        + String.format("  Now you have %d %s in the list.",
                                taskCount, taskCount != 1 ? "tasks" : "task");
        System.out.println(lastMessage);
    }

    /**
     * Prints a confirmation message after a task has been marked or unmarked.
     *
     * @param task Task whose completion status has changed.
     */
    public void showTaskStatusChanged(Task task) {
        String message = task.isDone() ? "Nice! I've marked this as done:"
                : "OK, I've marked this as not done:";
        lastMessage = "  " + message + "\n  " + task;
        System.out.println(lastMessage);
    }

    /**
     * Displays tasks whose descriptions match the given keyword.
     *
     * @param matches List of matching tasks.
     */
    public void showFindResults(ArrayList<Task> matches) {
        if (matches.isEmpty()) {
            lastMessage = "  Oops! No matching tasks found :(";
            System.out.println(lastMessage);
            return;
        }

        StringBuilder sb = new StringBuilder("  Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(matches.get(i)).append("\n");
        }

        if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == '\n') {
            sb.deleteCharAt(sb.length() - 1);
        }

        lastMessage = sb.toString();
        System.out.println(lastMessage);
    }

    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * Prints a horizontal line separator.
     */
    public void showLine() {
        System.out.println("  ________________________________________________________________________________");
    }
}
