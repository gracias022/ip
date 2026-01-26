import java.time.LocalDateTime;

public class AddDeadlineCommand extends AddCommand {
    public AddDeadlineCommand(String description, LocalDateTime by) {
        super(new Deadline(description, by));
    }
}
