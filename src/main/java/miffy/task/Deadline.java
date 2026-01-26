package miffy.task;

import java.time.LocalDateTime;

public class Deadline extends Task {

    protected LocalDateTime by;

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