import java.util.Scanner;

public class Miffy {
    private static void delimiter() {
        System.out.println("  ____________________________________________________________");
    }

    public static void main(String[] args) {
        TaskList taskList = new TaskList();

        Scanner myScanner = new Scanner(System.in);
        String userInput;

        delimiter();
        System.out.println("  Hello! I'm Miffy ^_^");
        System.out.println("  What can I do for you?");
        delimiter();

        while (true) {
            userInput = myScanner.nextLine();
            delimiter();

            if (userInput.equals("bye")) {
                System.out.println("  Bye. Hope to see you again soon!");
                delimiter();
                break;
            } else if (userInput.equals("list")) {
                taskList.list();
                delimiter();
                continue;
            } else if (userInput.startsWith("mark ")) { // include check for space after mark
                try {
                    int index = Integer.parseInt(userInput.substring(5));
                    Task task = taskList.markAsDone(index); // 1-based index
                    System.out.println("  Nice! I've marked this task as done:");
                    System.out.println("  " + task);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("  Oops! That task number doesn’t exist.");
                } catch (Exception e) {
                    System.out.println("  Invalid input format: Please use 'mark <index>'");
                } finally {
                    delimiter();
                }
            } else if (userInput.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(userInput.substring(7));
                    Task task = taskList.unmark(index);
                    System.out.println("  OK, I've marked this task as not done yet:");
                    System.out.println("  " + task);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("  Oops! That task number doesn’t exist.");
                } catch (Exception e) {
                    System.out.println("  Invalid input format: Please use 'unmark <index>'");
                } finally {
                    delimiter();
                }
            } else {
                taskList.add(new Task(userInput));
                System.out.println("  added: " + userInput);
                delimiter();
            }

        }

    }
}
