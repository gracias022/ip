package miffy.command;

import miffy.task.Todo;

/**
 * Represents a command that adds a {@link Todo} task to the task list.
 */
public class AddTodoCommand extends AddCommand {
    public AddTodoCommand(String description) {
        super(new Todo(description));
    }
}
