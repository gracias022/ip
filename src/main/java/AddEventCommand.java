import java.time.LocalDateTime;

public class AddEventCommand extends AddCommand {
    public AddEventCommand(String description, LocalDateTime from, LocalDateTime to) {
        super(new Event(description, from, to));
    }
}
