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
            userInput = miffy.scanner.nextLine();
            delimiter();

            if (userInput.equals("bye")) {
                System.out.println("  Bye. Hope to see you again soon!");
                delimiter();
                break;
            } else if (userInput.equals("list")) {
                miffy.taskList.list();
                delimiter();
                continue;
            } else if (userInput.startsWith("mark ")) { // include check for space after mark
                try {
                    int index = Integer.parseInt(userInput.substring(5));
                    Task task = miffy.taskList.markAsDone(index); // 1-based index
                    System.out.println("  Nice! I've marked this task as done:");
                    System.out.println("  " + task);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("  Oops! That task number doesn’t exist.");
                } catch (Exception e) {
                    System.out.println("  Invalid input format: Please use 'mark <index>'");
                }
                delimiter();

            } else if (userInput.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(userInput.substring(7));
                    Task task = miffy.taskList.unmark(index);
                    System.out.println("  OK, I've marked this task as not done yet:");
                    System.out.println("  " + task);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("  Oops! That task number doesn’t exist.");
                } catch (Exception e) {
                    System.out.println("  Invalid input format: Please use 'unmark <index>'");
                }
                delimiter();
            } else if (userInput.startsWith("todo ")) {
                Todo task = new Todo(userInput.substring(5));
                miffy.taskList.add(task);
                printAddConfirmation(task, miffy.taskList);
            } else if (userInput.startsWith("deadline ")) {
                addDeadline(userInput.substring(9), miffy.taskList);
            } else if (userInput.startsWith("event ")) {
                addEvent(userInput.substring(6), miffy.taskList);
            }

        }

    }

    private static void addDeadline(String input, TaskList tl) {
        try {
            String[] arr = input.split(" /by ");
            if (arr.length != 2) {
                throw new Exception();
            }
            Deadline task = new Deadline(arr[0], arr[1]);
            tl.add(task);
            printAddConfirmation(task, tl);
        } catch (Exception e) {
            System.out.println("  Invalid input format: Please use deadline <desc> /by <date/time>");
        }
    }

    private static void addEvent(String input, TaskList tl) {
        try {
            int div1 = input.indexOf(" /from ");
            int div2 = input.indexOf(" /to ");
            if (div1 == -1 || div2 == -1) {
                throw new Exception();
            }
            String desc = input.substring(0, div1);
            String from = input.substring(div1 + 7, div2);
            String to = input.substring(div2 + 5);
            Event task = new Event(desc, from, to);
            tl.add(task);
            printAddConfirmation(task, tl);
        } catch (Exception e) {
            System.out.println("  Invalid input format: Please use event <desc> /from <start> /to <end>");
        }
    }

    private static void printAddConfirmation(Task t, TaskList tl) {
        System.out.println("  Got it. I've added this task:");
        System.out.println("  " + t);
        int numTasks = tl.getTaskCount();
        System.out.printf("  Now you have %d %s in the list.\n", numTasks,
                numTasks > 1 ? "tasks" : "task" );
        delimiter();
    }

    private static void delimiter() {
        System.out.println("  ____________________________________________________________");
    }
}
