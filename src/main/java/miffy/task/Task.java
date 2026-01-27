package miffy.task;

import java.time.format.DateTimeFormatter;

/**
 * Represents a generic task with a description and completion status.
 * <p>
 * This is an abstract class meant to be extended by task types
 * such as {@link Todo}, {@link Deadline}, and {@link Event}.
 * Provides common behavior such as marking as done and unmarking.
 */
public abstract class Task {
    /** Formatter for displaying date/time in a readable format. */
    protected static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM d yyyy h:mm a");

    /** Description of the task. */
    protected String description;

    /** Completion status of the task. */
    protected boolean isDone;

    /**
     * Constructs a new task with the given description.
     * The task is initially not marked as done.
     *
     * @param description Task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns a string representation of the task suitable for saving to storage.
     * Must be implemented by subclasses.
     *
     * @return Formatted string for the task.
     */
    public abstract String formatTaskForStorage();

    /**
     * Returns a user-friendly string representation of the task for display purposes.
     * Subclasses may extend this format to include additional information such as dates or time ranges.
     *
     * @return Formatted string for the task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

