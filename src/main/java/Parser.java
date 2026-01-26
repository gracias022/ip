import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    /** Formatter for parsing date-time input in 24-hour format. */
    public static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public static Command parse(String fullCommand) throws MiffyException {
        String[] parts = fullCommand.split("\\s+", 2);
        String commandType = parts[0].toLowerCase();
        String[] details;

        switch (commandType) {
        case "bye":
            if (!fullCommand.equalsIgnoreCase("bye")) {
                throw new MiffyException("Oops! Usage: bye");
            }
            return new ExitCommand();
        case "list":
            if (!fullCommand.equalsIgnoreCase("list")) {
                throw new MiffyException("Oops! Usage: list");
            }
            return new ListCommand();
        case "todo":
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new MiffyException("Oops, the description of a todo cannot be empty!\n"
                        + "  Usage: todo <desc> (e.g. todo read book)");
            }
            return new AddTodoCommand(parts[1]);
        case "deadline":
            /* Check for "deadline", "deadline " */
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new MiffyException("Oops, the description and ending date/time of a deadline cannot be empty!\n" +
                        "  Usage: deadline <desc> /by <yyyy-MM-dd HHmm>\n"
                        + "  E.g. deadline return book /by 2026-01-16 1800");
            }

            /* Split task contents into <description> and <due date> */
            details = parts[1].split("\\s+/by\\s+");

            if (details.length != 2 || details[0].isBlank() || details[1].isBlank()) {
                throw new MiffyException("Invalid input format! Usage: deadline <desc> /by <yyyy-MM-dd HHmm>\n"
                        + "  E.g. deadline return book /by 2026-01-16 1800");
            }

            try {
                LocalDateTime dateTime = LocalDateTime.parse(details[1], INPUT_FORMATTER);
                return new AddDeadlineCommand(parts[0], dateTime);
            } catch (DateTimeParseException e) {
                throw new MiffyException("Invalid date format! Please use yyyy-MM-dd HHmm (e.g. 2026-01-16 1800)");
            }
        case "event":
            /* Check for "event", "event " */
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new MiffyException(
                        "Oops, the event description, start date/time and end date/time cannot be empty!\n"
                                + "  Usage: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>\n"
                                        + "  E.g. event meeting /from 2026-01-16 1400 /to 2026-01-16 1600");
            }

            details = parts[1].split("\\s+/from\\s+|\\s+/to\\s+");

            if (details.length < 3) {
                throw new MiffyException(
                        "Invalid input format! Usage: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>\n"
                                + "  E.g. event meeting /from 2026-01-16 1400 /to 2026-01-16 1600");
            }

            String desc = details[0];
            String from = details[1];
            String to = details[2];

            if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new MiffyException(
                        "Oops, the event description, start date/time and end date/time cannot be empty!");
            }

            try {
                LocalDateTime fromDate = LocalDateTime.parse(from, INPUT_FORMATTER);
                LocalDateTime toDate = LocalDateTime.parse(to, INPUT_FORMATTER);

                return new AddEventCommand(desc, fromDate, toDate);
            } catch (DateTimeParseException e) {
                throw new MiffyException("Invalid date format! Please use yyyy-MM-dd HHmm (e.g. 2026-01-16 1800)");
            }
        case "mark", "unmark", "delete":
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new MiffyException(
                        "Please specify which task to %s. Usage: %s <index>".formatted(commandType, commandType));
            }

            if (parts.length > 2) {
                throw new MiffyException(
                        "Oops! One task at a time please -_- Usage: %s <index>".formatted(commandType));
            }

            try {
                int userIndex = Integer.parseInt(parts[1]);
                int zeroBasedIndex = userIndex - 1;
                return switch (commandType) {
                    case "mark" -> new MarkCommand(zeroBasedIndex);
                    case "unmark" -> new UnmarkCommand(zeroBasedIndex);
                    case "delete" -> new DeleteCommand(zeroBasedIndex);
                    default -> throw new MiffyException("Unknown command: " + commandType);
                };
            } catch (NumberFormatException e) {
                throw new MiffyException("Please enter a valid task number. Usage: %s <index>".formatted(commandType));
            }
        default:
            throw new MiffyException("Sorry, I don't know what that means :(");
        }
    }
}
