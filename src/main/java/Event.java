import java.time.LocalDateTime;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String formatTaskForStorage() {
        return String.format("E | %d | %s | %s | %s\n", isDone ? 1 : 0, description, from, to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(Task.DISPLAY_FORMATTER)
                + " to: " + to.format(Task.DISPLAY_FORMATTER) + ")";
    }
}