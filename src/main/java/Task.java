import java.time.format.DateTimeFormatter;

public abstract class Task {
    protected static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM d yyyy h:mm a");
    protected String description;
    protected boolean isDone;

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

    public abstract String formatTaskForStorage();

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

