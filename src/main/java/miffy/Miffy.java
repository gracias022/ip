package miffy;

import miffy.command.Command;
import miffy.exception.MiffyException;
import miffy.parser.Parser;
import miffy.storage.Storage;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Main application class for Miffy.
 * <p>
 * Handles initialization of storage, task list, and UI, and runs
 * the main application loop to process user input.
 */
@SuppressWarnings("checkstyle:Regexp")
public class Miffy {
    private static final String FILE_PATH = "./data/miffy.txt";

    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private String commandType;
    private boolean isExit;


    /**
     * Constructs a Miffy application instance with the given file path for storage.
     */
    public Miffy() {
        storage = new Storage(FILE_PATH);
        ui = new Ui();
        isExit = false;

        try {
            taskList = new TaskList(storage.load());
        } catch (MiffyException e) {
            ui.showLoadingError();
            taskList = new TaskList();
        }
    }

    /**
     * Runs the main application loop until the user exits.
     * Prints the welcome message, reads user commands, parses and executes them,
     * handles exceptions, and prints feedback to the user.
     */
    public void run() {
        ui.showWelcome();
        isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(taskList, ui, storage);
                isExit = c.isExit();
            } catch (MiffyException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Main entry point for the Miffy application.
     *
     * @param args Command-line arguments (ignored)
     */
    public static void main(String[] args) {
        new Miffy().run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            c.execute(taskList, ui, storage);
            commandType = c.getClass().getSimpleName();
            isExit = c.isExit();
            return ui.getLastMessage();
        } catch (MiffyException e) {
            commandType = "Error";
            return e.getMessage();
        }
    }

    public String getCommandType() {
        return commandType;
    }

    /**
     * Displays the welcome message to the console and returns it for the GUI.
     * @return the welcome message that was displayed
     */
    public String getWelcomeMessage() {
        ui.showWelcome();
        return ui.getLastMessage();
    }

    public boolean isExit() {
        return isExit;
    }

}
