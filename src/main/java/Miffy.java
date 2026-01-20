import java.util.Scanner;

public class Miffy {
    private TaskList taskList;
    private Scanner scanner;

    public Miffy() {
        taskList = new TaskList();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Miffy miffy = new Miffy();
        String userInput;

        delimiter();
        System.out.println("  Hello! I'm Miffy ^_^");
        System.out.println("  What can I do for you?");
        delimiter();

        while (true) {
            try {
                userInput = miffy.scanner.nextLine();
                delimiter();

                if (userInput.equals("bye")) {
                    System.out.println("  Bye. Hope to see you again soon!");
                    delimiter();
                    miffy.scanner.close();
                    break;
                } else if (userInput.equals("list")) {
                    miffy.taskList.list();
                    delimiter();
                } else if (userInput.equals("mark") || userInput.startsWith("mark ")) {
                    miffy.handleMark(userInput);
                } else if (userInput.equals("unmark") || userInput.startsWith("unmark ")) {
                    miffy.handleUnmark(userInput);
                } else if (userInput.equals("todo") || userInput.startsWith("todo ")) {
                    miffy.addTodo(userInput);
                } else if (userInput.equals("deadline") || userInput.startsWith("deadline ")) {
                    miffy.addDeadline(userInput);
                } else if (userInput.equals("event") || userInput.startsWith("event ")) {
                    miffy.addEvent(userInput);
                } else {
                    throw new MiffyException("Sorry, I don't know what that means :( ");
                }
            } catch (MiffyException e) {
                System.out.println("  " + e.getMessage());
                delimiter();
            }
        }
    }

    private void handleMark(String input) throws MiffyException {
        if (input.equals("mark")) {
            throw new MiffyException("Please specify which task to mark. Usage: mark <index>");
        }

        try {
            int index = Integer.parseInt(input.substring(5));
            Task task = taskList.markAsDone(index); // 1-based index
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
        if (input.equals("unmark")) {
            throw new MiffyException("Please specify which task to unmark. Usage: unmark <index>");
        }

        try {
            int index = Integer.parseInt(input.substring(7));
            Task task = taskList.unmark(index);
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
        if (input.trim().equals("todo")) {
            throw new MiffyException("Oops, the description of a todo cannot be empty!");
        }
        Todo task = new Todo(input.substring(5));
        taskList.add(task);
        this.printAddConfirmation(task);
    }

    private void addDeadline(String input) throws MiffyException {
        if (input.trim().equals("deadline")) {
            throw new MiffyException("Oops, the description and ending date/time of a deadline cannot be empty!\n" +
                    "  Usage: deadline <desc> /by <date/time>");
        }

        String[] arr = input.substring(9).split(" /by ");
        if (arr.length != 2 || arr[0].isBlank() || arr[1].isBlank()) {
            throw new MiffyException("Invalid input format! Usage: deadline <desc> /by <date/time>");
        }
        Deadline task = new Deadline(arr[0], arr[1]);
        taskList.add(task);
        this.printAddConfirmation(task);
    }

    private void addEvent(String input) throws MiffyException {
        if (input.trim().equals("event")) {
            throw new MiffyException("Oops, the event description, start date/time and end date/time cannot be empty!\n" +
                    "  Usage: event <desc> /from <start> /to <end>");
        }
        int div1 = input.indexOf(" /from ");
        int div2 = input.indexOf(" /to ");
        if (div1 == -1 || div2 == -1 || div1 + 7 > div2) { // e.g. error case: event ski /from /to 12pm, div1 = 9, div2 = 15
            throw new MiffyException("Invalid input format! Usage: event <desc> /from <start> /to <end>");
        }
        String desc = input.substring(6, div1);
        String from = input.substring(div1 + 7, div2);
        String to = input.substring(div2 + 5);

        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new MiffyException("Oops, the event description, start date/time and end date/time cannot be empty!");
        }

        Event task = new Event(desc, from, to);
        taskList.add(task);
        this.printAddConfirmation(task);
    }

    private void printAddConfirmation(Task t) {
        System.out.println("  Got it. I've added this task:");
        System.out.println("  " + t);
        int numTasks = taskList.getTaskCount();
        System.out.printf("  Now you have %d %s in the list.\n", numTasks,
                numTasks > 1 ? "tasks" : "task" );
        delimiter();
    }

    private static void delimiter() {
        System.out.println("  ________________________________________________________________________________");
    }
}
