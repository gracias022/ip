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
public class Miffy {
    private static final String FILE_PATH = "./data/miffy.txt";

    private Storage storage;
    private TaskList taskList;
    private Ui ui;


    /**
     * Constructs a Miffy application instance with the given file path for storage.
     */
    public Miffy() {
        storage = new Storage(FILE_PATH);
        ui = new Ui();

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
        boolean isExit = false;

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
        return "Miffy heard: " + input;
    }
}
