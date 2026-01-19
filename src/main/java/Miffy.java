import java.util.Scanner;

public class Miffy {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        String userInput;

        System.out.println("  ____________________________________________________________");
        System.out.println("  Hello! I'm Miffy ^_^");
        System.out.println("  What can I do for you?");
        System.out.println("  ____________________________________________________________");

        while (true) {
            userInput = myScanner.nextLine();
            System.out.println("  ____________________________________________________________");

            if (userInput.equals("bye")) {
                System.out.println("  Bye. Hope to see you again soon!");
                System.out.println("  ____________________________________________________________");
                break;
            }

            System.out.println("  " + userInput);
            System.out.println("  ____________________________________________________________");
        }

    }
}
