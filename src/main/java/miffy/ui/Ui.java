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

    private static final String INDENT = "  ";

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
        lastMessage = INDENT + "Hello! I'm Miffy ^_^\n  What can I do for you?";
        System.out.println(lastMessage);
        showLine();
    }

    /**
     * Prints the goodbye message.
     */
    public void showGoodbye() {
        lastMessage = INDENT + "Bye. Hope to see you again soon!";
        System.out.println(lastMessage);
    }

    /**
     * Reads the next line of input from the user.
     *
     * @return Raw input string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Closes the scanner used for reading user input.
     */
    public void closeScanner() {
        scanner.close();
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
        StringBuilder sb = formatWithIndent(message);
        lastMessage = removeTrailingNewline(sb);
        System.out.println(lastMessage);
    }

    private StringBuilder formatWithIndent(String message) {
        String[] lines = message.split("\n");
        StringBuilder sb = new StringBuilder();

        for (String line : lines) {
            sb.append(INDENT).append(line).append("\n");
        }

        return sb;
    }

    /**
     * Confirms that an operation (add/delete) has been performed on a task.
     *
     * @param task Task affected.
     * @param action Action performed.
     * @param taskCount Current number of tasks in list.
     */
    public void showOpsConfirmation(Task task, String action, int taskCount) {
        lastMessage = formatOpsConfirmation(task, action, taskCount);
        System.out.println(lastMessage);
    }

    private String formatOpsConfirmation(Task task, String action, int taskCount) {
        return INDENT + action + " this task:\n"
                + INDENT + task + "\n"
                + INDENT + formatTaskCountMessage(taskCount);
    }

    private String formatTaskCountMessage(int taskCount) {
        String plural = taskCount != 1 ? "tasks" : "task";
        return String.format("Now you have %d %s in the list.", taskCount, plural);
    }

    /**
     * Prints a confirmation message after a task has been marked or unmarked.
     *
     * @param task Task whose completion status has changed.
     */
    public void showTaskStatusChanged(Task task) {
        String message = task.isDone() ? "Nice! I've marked this as done:"
                : "OK, I've marked this as not done:";
        lastMessage = INDENT + message + "\n  " + task;
        System.out.println(lastMessage);
    }

    /**
     * Displays a numbered list of tasks.
     *
     * @param tasks List of tasks to display.
     */
    public void showList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            lastMessage = getNoTasksMessage();
            System.out.println(lastMessage);
            return;
        }

        lastMessage = buildTaskList(tasks, "Here are the tasks in your list:");
        System.out.println(lastMessage);
    }

    /**
     * Displays tasks whose descriptions match the given keyword.
     *
     * @param matches List of matching tasks.
     */
    public void showFindResults(ArrayList<Task> matches) {
        if (matches.isEmpty()) {
            lastMessage = getNoMatchesMessage();
            System.out.println(lastMessage);
            return;
        }

        lastMessage = buildTaskList(matches, "Here are the matching tasks in your list:");
        System.out.println(lastMessage);
    }

    private String getNoTasksMessage() {
        return INDENT + "No tasks yet. Add one now!";
    }

    private String getNoMatchesMessage() {
        return INDENT + "Oops! No matching tasks found :(";
    }

    private String buildTaskList(ArrayList<Task> tasks, String header) {
        StringBuilder sb = new StringBuilder(INDENT + header + "\n");
        appendNumberedTasks(sb, tasks);
        return removeTrailingNewline(sb);
    }

    private void appendNumberedTasks(StringBuilder sb, ArrayList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(INDENT)
                    .append(i + 1)
                    .append(". ")
                    .append(tasks.get(i))
                    .append("\n");
        }
    }

    private String removeTrailingNewline(StringBuilder sb) {
        int lastCharIndex = sb.length() - 1;
        boolean isLastCharNewline = sb.charAt(lastCharIndex) == '\n';

        if (!sb.isEmpty() && isLastCharNewline) {
            sb.deleteCharAt(lastCharIndex);
        }

        return sb.toString();
    }

    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * Prints a horizontal line separator.
     */
    public void showLine() {
        System.out.println(INDENT + "________________________________________________________________________________");
    }
}
