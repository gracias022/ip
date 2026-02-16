package miffy.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import miffy.command.AddDeadlineCommand;
import miffy.command.AddEventCommand;
import miffy.command.AddTodoCommand;
import miffy.command.Command;
import miffy.command.CommandType;
import miffy.command.DeleteCommand;
import miffy.command.ExitCommand;
import miffy.command.FindCommand;
import miffy.command.ListAliasesCommand;
import miffy.command.ListCommand;
import miffy.command.MarkCommand;
import miffy.command.SetAliasCommand;
import miffy.command.UnmarkCommand;
import miffy.exception.MiffyException;

/**
 * Parses user input strings into {@link Command} objects for execution by Miffy.
 * <p>
 * Handles commands including: todo, deadline, event, mark, unmark, find, delete, list, and bye.
 * Also validates input formats, including checking for non-empty task descriptions
 * and correct date-time formats.
 */
public class Parser {

    /**
     * Formatter for parsing date-time input in 24-hour format.
     * <p>
     * Example: 2026-01-16 1800
    */
    public static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private static final String MESSAGE_DATE_FORMAT =
            "Oops, wrong date format! Please use yyyy-MM-dd HHmm (e.g. 2026-01-16 1800)";
    /**
     * Parses a full command string entered by the user and returns the corresponding {@link Command} object.
     * <p>
     * Performs input validation, including
     * command type recognition, syntax checks for all task types,
     * date-time format validation for deadlines and events,
     * and syntax checks for find, mark, unmark, and delete commands
     *
     * @param fullCommand Input string entered by the user, with leading and trailing whitespaces removed.
     * @return A {@link Command} object representing the action to be performed.
     * @throws MiffyException If the input is invalid, unknown, or contains improperly formatted data.
     */
    public static Command parse(String fullCommand) throws MiffyException {
        String trimmedCommand = fullCommand.trim();
        String[] parts = trimmedCommand.split("\\s+", 2);
        CommandType commandType = CommandAlias.expand(parts[0].toLowerCase());

        return switch (commandType) {
            case SETALIAS -> getSetAliasCommand(parts);
            case ALIASES -> getListAliasesCommand(parts);
            case BYE -> getExitCommand(parts);
            case LIST -> getListCommand(parts);
            case TODO -> getAddTodoCommand(parts);
            case DEADLINE -> getAddDeadlineCommand(parts);
            case EVENT -> getAddEventCommand(parts);
            case MARK, UNMARK, DELETE -> getMarkOrDeleteCommand(parts, commandType);
            case FIND -> getFindCommand(parts);
        };
    }

    private static SetAliasCommand getSetAliasCommand(String[] parts) throws MiffyException {
        String[] details = getAliasDetails(parts);
        String alias = details[0];
        String command = details[1];

        if (isDetailsInvalid(details)) {
            throw new MiffyException("Oops! " + SetAliasCommand.MESSAGE_USAGE);
        }

        return new SetAliasCommand(alias, command);
    }

    private static String[] getAliasDetails(String[] parts) throws MiffyException {
        String[] details;

        /* Check for "setalias", "setalias " */
        if (isPartsTooShort(parts)) {
            throw new MiffyException("Oops! " + SetAliasCommand.MESSAGE_USAGE);
        }

        /* Split part[1] into <alias> and <command> */
        details = parts[1].split("\\s+", 2);
        return details;
    }

    private static ListAliasesCommand getListAliasesCommand(String[] parts) throws MiffyException {
        if (parts.length > 1) {
            throw new MiffyException("Oops! Usage: aliases");
        }
        return new ListAliasesCommand();
    }

    private static ExitCommand getExitCommand(String[] parts) throws MiffyException {
        if (parts.length > 1) {
            throw new MiffyException("Oops! Usage: bye");
        }
        return new ExitCommand();
    }

    private static ListCommand getListCommand(String[] parts) throws MiffyException {
        if (parts.length > 1) {
            throw new MiffyException("Oops! Usage: list");
        }
        return new ListCommand();
    }

    private static FindCommand getFindCommand(String[] parts) throws MiffyException {
        if (isPartsTooShort(parts)) {
            throw new MiffyException("Oops! Usage: find <keyword>\n" + "e.g. find meeting");
        }

        return new FindCommand(parts[1]);
    }

    private static AddTodoCommand getAddTodoCommand(String[] parts) throws MiffyException {
        if (isPartsTooShort(parts)) {
            throw new MiffyException("Oops, the description of a todo cannot be empty!\n"
                    + "Usage: todo <desc> (e.g. todo read book)");
        }
        return new AddTodoCommand(parts[1]);
    }

    private static AddDeadlineCommand getAddDeadlineCommand(String[] parts) throws MiffyException {
        String[] details = getDeadlineDetails(parts);

        if (isDetailsInvalid(details)) {
            throw new MiffyException("Oops! " + AddDeadlineCommand.DEADLINE_USAGE);
        }

        LocalDateTime dateTime = parseDateTime(details[1]);
        return new AddDeadlineCommand(details[0], dateTime);
    }

    private static String[] getDeadlineDetails(String[] parts) throws MiffyException {
        String[] details;

        /* Check for "deadline", "deadline " */
        if (isPartsTooShort(parts)) {
            throw new MiffyException("Oops, the description and ending date/time of a deadline cannot be empty!\n"
                    + AddDeadlineCommand.DEADLINE_USAGE);
        }

        /* Split task contents into <description> and <due date> */
        details = parts[1].split("\\s+/by\\s+");
        return details;
    }

    private static AddEventCommand getAddEventCommand(String[] parts) throws MiffyException {
        String[] details = getEventDetails(parts);
        validateEventDetails(details);

        String desc = details[0];
        String from = details[1];
        String to = details[2];

        LocalDateTime fromDate = parseDateTime(from);
        LocalDateTime toDate = parseDateTime(to);

        if (!fromDate.isBefore(toDate)) {
            throw new MiffyException(
                    "Oops, event start time must be before end time!"
            );
        }

        return new AddEventCommand(desc, fromDate, toDate);
    }

    private static String[] getEventDetails(String[] parts) throws MiffyException {
        String[] details;

        /* Check for "event", "event " */
        if (isPartsTooShort(parts)) {
            throw new MiffyException(
                    "Oops, the event description, start date/time and end date/time cannot be empty!\n"
                            + AddEventCommand.EVENT_USAGE);
        }

        details = parts[1].split("\\s+/from\\s+|\\s+/to\\s+");
        return details;
    }

    private static void validateEventDetails(String[] details) throws MiffyException {
        if (details.length < 3) {
            throw new MiffyException("Oops! " + AddEventCommand.EVENT_USAGE);
        }

        if (details[0].isBlank() || details[1].isBlank() || details[2].isBlank()) {
            throw new MiffyException(
                    "Oops, the event description, start date/time and end date/time cannot be empty!");
        }
    }

    private static Command getMarkOrDeleteCommand(String[] parts, CommandType commandType) throws MiffyException {
        String command = commandType.name().toLowerCase();

        if (isPartsTooShort(parts)) {
            throw new MiffyException(
                    "Oops! Please specify which task to %s. Usage: %s <index>".formatted(command, command));
        }

        boolean hasExtraArgument = parts.length > 2;
        if (hasExtraArgument) {
            throw new MiffyException(
                    "Oops! One task at a time please -_- Usage: %s <index>".formatted(command));
        }

        int zeroBasedIndex = parseZeroBasedIndex(parts[1], command);

        return switch (commandType) {
            case MARK -> new MarkCommand(zeroBasedIndex);
            case UNMARK -> new UnmarkCommand(zeroBasedIndex);
            case DELETE -> new DeleteCommand(zeroBasedIndex);
            default -> throw new MiffyException("Oops! Unknown command: " + command);
        };
    }

    private static int parseZeroBasedIndex(String input, String commandType) throws MiffyException {
        try {
            int userIndex = Integer.parseInt(input);
            return userIndex - 1;
        } catch (NumberFormatException e) {
            throw new MiffyException(
                    "Oops! Please enter a valid task number. Usage: %s <index>"
                            .formatted(commandType));
        }
    }

    private static LocalDateTime parseDateTime(String dateTimeString) throws MiffyException {
        try {
            return LocalDateTime.parse(dateTimeString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new MiffyException(MESSAGE_DATE_FORMAT);
        }
    }

    private static boolean isPartsTooShort(String[] parts) {
        return parts.length < 2 || parts[1].isBlank();
    }

    private static boolean isDetailsInvalid(String[] details) {
        return details.length != 2 || details[0].isBlank() || details[1].isBlank();
    }
}
