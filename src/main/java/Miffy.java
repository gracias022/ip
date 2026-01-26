import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Miffy {

    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    private static final String FILE_PATH = "./data/miffy.txt";

    /** Formatter for parsing date-time input in 24-hour format. */
    public static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Miffy(String filePath) {
        storage = new Storage(filePath);
        ui = new Ui();

        try {
            taskList = new TaskList(storage.load());
        } catch (MiffyException e) {
            ui.showLoadingError();
            taskList = new TaskList();
        }
    }

    public static void main(String[] args) {
        Miffy miffy = new Miffy(FILE_PATH);
        String userInput;

        miffy.ui.showWelcome();

        while (true) {
            try {
                userInput = miffy.ui.readCommand();
                miffy.ui.showLine();

                String[] parts = userInput.split("\\s+", 2);
                String commandType = parts[0].toLowerCase();

                switch (commandType) {
                case "bye":
                    if (!userInput.equalsIgnoreCase("bye")) {
                        throw new MiffyException("Oops! Usage: bye");
                    }
                    miffy.ui.showGoodbye();
                    miffy.ui.closeScanner();
                    return; // exit main()
                case "list":
                    if (!userInput.equalsIgnoreCase("list")) {
                        throw new MiffyException("Oops! Usage: list");
                    }
                    miffy.ui.showList(miffy.taskList.getAllTasks());
                    break;
                case "mark":
                    miffy.handleMark(userInput);
                    break;
                case "unmark":
                    miffy.handleUnmark(userInput);
                    break;
                case "todo":
                    miffy.addTodo(userInput);
                    break;
                case "deadline":
                    miffy.addDeadline(userInput);
                    break;
                case "event":
                    miffy.addEvent(userInput);
                    break;
                case "delete":
                    miffy.handleDelete(userInput);
                    break;
                default:
                    throw new MiffyException("Sorry, I don't know what that means :(");
                }
            } catch (MiffyException e) {
                miffy.ui.showError(e.getMessage());
            } finally {
                miffy.ui.showLine();
            }
        }
    }

    private void handleDelete(String input) throws MiffyException {
        String[] parts = input.split("\\s+");

        if (parts.length == 1) {
            throw new MiffyException("Please specify which task to delete. Usage: delete <index>");
        }

        if (parts.length > 2) {
            throw new MiffyException("Oops! One task at a time please -_- Usage: delete <index>");
        }

        try {
            int index = Integer.parseInt(parts[1]);
            Task task = taskList.deleteTask(index); // 1-based index
            storage.save(taskList.getAllTasks());
            ui.showOpsConfirmation(task, "Noted. I've removed", taskList.getTaskCount());
        } catch (NumberFormatException e) {
            throw new MiffyException("Please enter a valid task number. Usage: 'delete <index>'");
        } catch (IndexOutOfBoundsException e) {
            throw new MiffyException("Oops! That task number doesn’t exist.");
        }
    }

    private void handleMark(String input) throws MiffyException {
        String[] parts = input.split("\\s+");

        if (parts.length == 1) {
            throw new MiffyException("Please specify which task to mark. Usage: mark <index>");
        }

        if (parts.length > 2) {
            throw new MiffyException("Oops! One task at a time please -_- Usage: mark <index>");
        }

        try {
            int index = Integer.parseInt(parts[1]);
            Task task = taskList.markAsDone(index); // 1-based index
            storage.save(taskList.getAllTasks());
            ui.showTaskStatusChanged(task);
        } catch (NumberFormatException e) {
            throw new MiffyException("Please enter a valid task number. Usage: 'mark <index>'");
        } catch (IndexOutOfBoundsException e) {
            throw new MiffyException("Oops! That task number doesn’t exist.");
        }
    }

    private void handleUnmark(String input) throws MiffyException {
        String[] parts = input.split("\\s+");

        if (parts.length == 1) {
            throw new MiffyException("Please specify which task to unmark. Usage: unmark <index>");
        }

        if (parts.length > 2) {
            throw new MiffyException("Oops! One task at a time please -_- Usage: unmark <index>");
        }

        try {
            int index = Integer.parseInt(parts[1]);
            Task task = taskList.unmark(index);
            storage.save(taskList.getAllTasks());
            ui.showTaskStatusChanged(task);
        } catch (NumberFormatException e) {
            throw new MiffyException("Please enter a valid task number. Usage: 'unmark <index>'");
        } catch (IndexOutOfBoundsException e) {
            throw new MiffyException("Oops! That task number doesn’t exist.");
        }
    }

    private void addTodo(String input) throws MiffyException {
        if (input.equalsIgnoreCase("todo")) {
            throw new MiffyException("Oops, the description of a todo cannot be empty!");
        }

        // remove "todo " prefix
        String desc = input.replaceFirst("(?i)^todo\\s*", "");

        Todo task = new Todo(desc);
        taskList.add(task);
        storage.save(taskList.getAllTasks());
        ui.showOpsConfirmation(task, "Got it. I've added", taskList.getTaskCount());
    }

    private void addDeadline(String input) throws MiffyException {
        if (input.equalsIgnoreCase("deadline")) {
            throw new MiffyException("Oops, the description and ending date/time of a deadline cannot be empty!\n"
                    + "  Usage: deadline <desc> /by <yyyy-MM-dd HHmm>\n"
                            + "  E.g. deadline return book /by 2026-01-16 1800");
        }

        String details = input.replaceFirst("(?i)^deadline\\s*", "");
        String[] parts = details.split("\\s+/by\\s+");

        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new MiffyException("Invalid input format! Usage: deadline <desc> /by <yyyy-MM-dd HHmm>\n"
                    + "  E.g. deadline return book /by 2026-01-16 1800");
        }

        try {
            LocalDateTime dateTime = LocalDateTime.parse(parts[1], INPUT_FORMATTER);
            Deadline task = new Deadline(parts[0], dateTime);
            taskList.add(task);
            storage.save(taskList.getAllTasks());
            ui.showOpsConfirmation(task, "Got it. I've added", taskList.getTaskCount());
        } catch (DateTimeParseException e) {
            throw new MiffyException("Invalid date format! Please use yyyy-MM-dd HHmm (e.g. 2026-01-16 1800)");
        }
    }

    private void addEvent(String input) throws MiffyException {
        if (input.equalsIgnoreCase("event")) {
            throw new MiffyException("Oops, the event description, start date/time and end date/time cannot be empty!\n"
                    + "  Usage: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        }

        String details = input.replaceFirst("(?i)^event\\s*", "");
        String[] parts = details.split("\\s+/from\\s+|\\s+/to\\s+");

        if (parts.length < 3) {
            throw new MiffyException(
                    "Invalid input format! Usage: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        }

        String desc = parts[0];
        String from = parts[1];
        String to = parts[2];

        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new MiffyException("Oops, the event description, start date/time and end date/time cannot be empty!");
        }

        try {
            LocalDateTime fromDate = LocalDateTime.parse(from, INPUT_FORMATTER);
            LocalDateTime toDate = LocalDateTime.parse(to, INPUT_FORMATTER);

            Event task = new Event(desc, fromDate, toDate);
            taskList.add(task);
            storage.save(taskList.getAllTasks());
            ui.showOpsConfirmation(task, "Got it. I've added", taskList.getTaskCount());
        } catch (DateTimeParseException e) {
            throw new MiffyException("Invalid date format! Please use yyyy-MM-dd HHmm (e.g. 2026-01-16 1800)");
        }
    }

}