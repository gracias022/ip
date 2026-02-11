package miffy.command;

import java.time.LocalDateTime;

import miffy.task.Deadline;

/**
 * Represents a command that adds a {@link Deadline} task to the task list.
 */
public class AddDeadlineCommand extends AddCommand {
    public AddDeadlineCommand(String description, LocalDateTime by) {
        super(new Deadline(description, by));
    }
}
