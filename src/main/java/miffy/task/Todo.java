package miffy.task;

/**
 * Represents a task without any associated date-time.
 */
public class Todo extends Task {

    /**
     * Creates a todo task.
     *
     * @param description Task description.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String formatTaskForStorage() {
        return String.format("T | %d | %s\n", isDone ? 1 : 0, description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
