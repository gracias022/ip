package miffy.task;

import java.time.LocalDateTime;

/**
 * Represents a task that must be completed by a specific date-time.
 */
public class Deadline extends Task {

    protected LocalDateTime by;

    /**
     * Creates a deadline task.
     *
     * @param description Task description.
     * @param by Due date-time.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String formatTaskForStorage() {
        return String.format("D | %d | %s | %s\n", isDone ? 1 : 0, description, by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " +
                by.format(Task.DISPLAY_FORMATTER) + ")";

    }
}