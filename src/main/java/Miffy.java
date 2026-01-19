import java.util.Scanner;

public class Miffy {
    private String[] strArray = new String[100];
    private int index = 0;

    public static void main(String[] args) {
        Miffy chatbot = new Miffy();

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
            } else if (userInput.equals("list")) {
                for (int i = 0; i < chatbot.index; i++) {
                    System.out.println("  " + (i + 1) + ". " + chatbot.strArray[i]);
                }
                System.out.println("  ____________________________________________________________");
                continue;
            }

            chatbot.strArray[chatbot.index] = userInput;
            chatbot.index++;
            System.out.println("  added: " + userInput);
            System.out.println("  ____________________________________________________________");
        }

    }
}
