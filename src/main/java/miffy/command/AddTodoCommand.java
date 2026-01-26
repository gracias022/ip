package miffy.command;

import miffy.task.Todo;

public class AddTodoCommand extends AddCommand {
    public AddTodoCommand(String description) {
        super(new Todo(description));
    }
}
