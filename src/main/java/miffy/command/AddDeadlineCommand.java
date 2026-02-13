package miffy.command;

import java.time.LocalDateTime;

import miffy.task.Deadline;

/**
 * Represents a command that adds a {@link Deadline} task to the task list.
 */
public class AddDeadlineCommand extends AddCommand {
    public static final String DEADLINE_USAGE =
            "Usage: deadline <desc> /by <yyyy-MM-dd HHmm>\n"
                    + "E.g. deadline return book /by 2026-01-16 1800";

    public AddDeadlineCommand(String description, LocalDateTime by) {
        super(new Deadline(description, by));
    }
}
