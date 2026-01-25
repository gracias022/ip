import java.util.Scanner;

public class Miffy {
    private Storage storage;
    private TaskList taskList;
    private Scanner scanner;
    private static final String FILE_PATH = "./data/miffy.txt";

    public Miffy(String filePath) {
        storage = new Storage(filePath);
        scanner = new Scanner(System.in);

        try {
            taskList = new TaskList(storage.load());
        } catch (MiffyException e) {
            System.out.println("Uh oh, something went wrong: " + e.getMessage());
            taskList = new TaskList();
        }
    }

    public static void main(String[] args) {
        Miffy miffy = new Miffy(FILE_PATH);
        String userInput;

        delimiter();
        System.out.println("  Hello! I'm Miffy ^_^");
        System.out.println("  What can I do for you?");
        delimiter();

        while (true) {
            try {
                userInput = miffy.scanner.nextLine().trim();
                delimiter();

                Command command = Command.fromInput(userInput);

                switch (command) {
                case BYE:
                    if (!userInput.equalsIgnoreCase("bye")) {
                        throw new MiffyException("Oops! Usage: bye");
                    }
                    System.out.println("  Bye. Hope to see you again soon!");
                    delimiter();
                    miffy.scanner.close();
                    return; // exit main()
                case LIST:
                    if (!userInput.equalsIgnoreCase("list")) {
                        throw new MiffyException("Oops! Usage: list");
                    }
                    miffy.taskList.list();
                    delimiter();
                    break;
                case MARK:
                    miffy.handleMark(userInput);
                    break;
                case UNMARK:
                    miffy.handleUnmark(userInput);
                    break;
                case TODO:
                    miffy.addTodo(userInput);
                    break;
                case DEADLINE:
                    miffy.addDeadline(userInput);
                    break;
                case EVENT:
                    miffy.addEvent(userInput);
                    break;
                case DELETE:
                    miffy.handleDelete(userInput);
                    break;
                default:
                    throw new MiffyException("Sorry, I don't know what that means :(");
                }
            } catch (MiffyException e) {
                System.out.println("  " + e.getMessage());
                delimiter();
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
            this.printOpsConfirmation(task, "Noted. I've removed");
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
            System.out.println("  Nice! I've marked this task as done:");
            System.out.println("  " + task);
            delimiter();
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
            System.out.println("  OK, I've marked this task as not done yet:");
            System.out.println("  " + task);
            delimiter();
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
        this.printOpsConfirmation(task, "Got it. I've added");
    }

    private void addDeadline(String input) throws MiffyException {
        if (input.equalsIgnoreCase("deadline")) {
            throw new MiffyException("Oops, the description and ending date/time of a deadline cannot be empty!\n" +
                    "  Usage: deadline <desc> /by <date/time>");
        }

        String details = input.replaceFirst("(?i)^deadline\\s*", "");
        String[] parts = details.split("\\s+/by\\s+");

        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new MiffyException("Invalid input format! Usage: deadline <desc> /by <date/time>");
        }

        Deadline task = new Deadline(parts[0], parts[1]);
        taskList.add(task);
        storage.save(taskList.getAllTasks());
        this.printOpsConfirmation(task, "Got it. I've added");
    }

    private void addEvent(String input) throws MiffyException {
        if (input.equalsIgnoreCase("event")) {
            throw new MiffyException("Oops, the event description, start date/time and end date/time cannot be empty!\n" +
                    "  Usage: event <desc> /from <start> /to <end>");
        }

        String details = input.replaceFirst("(?i)^event\\s*", "");
        String[] parts = details.split("\\s+/from\\s+|\\s+/to\\s+");

        if (parts.length < 3) {
            throw new MiffyException("Invalid input format! Usage: event <desc> /from <start> /to <end>");
        }

        String desc = parts[0];
        String from = parts[1];
        String to = parts[2];

        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new MiffyException("Oops, the event description, start date/time and end date/time cannot be empty!");
        }

        Event task = new Event(desc, from, to);
        taskList.add(task);
        storage.save(taskList.getAllTasks());
        this.printOpsConfirmation(task, "Got it. I've added");
    }

    private void printOpsConfirmation(Task t, String action) {
        System.out.println("  " + action + " this task:");
        System.out.println("  " + t);
        int numTasks = taskList.getTaskCount();
        System.out.printf("  Now you have %d %s in the list.\n", numTasks,
                numTasks != 1 ? "tasks" : "task");
        delimiter();
    }

    private static void delimiter() {
        System.out.println("  ________________________________________________________________________________");
    }
}
