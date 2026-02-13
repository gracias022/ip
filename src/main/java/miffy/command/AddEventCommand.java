package miffy.command;

import java.time.LocalDateTime;

import miffy.task.Event;

/**
 * Represents a command that adds an {@link Event} task to the task list.
 */
public class AddEventCommand extends AddCommand {
    public static final String EVENT_USAGE =
            "Usage: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>\n"
                    + "E.g. event meeting /from 2026-01-16 1400 /to 2026-01-16 1600";

    public AddEventCommand(String description, LocalDateTime from, LocalDateTime to) {
        super(new Event(description, from, to));
    }
}
