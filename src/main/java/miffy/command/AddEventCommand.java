package miffy.command;

import java.time.LocalDateTime;

import miffy.task.Event;

/**
 * Represents a command that adds an {@link Event} task to the task list.
 */
public class AddEventCommand extends AddCommand {
    public AddEventCommand(String description, LocalDateTime from, LocalDateTime to) {
        super(new Event(description, from, to));
    }
}
