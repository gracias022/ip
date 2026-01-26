package miffy;

import miffy.command.Command;
import miffy.exception.MiffyException;
import miffy.parser.Parser;
import miffy.storage.Storage;
import miffy.task.TaskList;
import miffy.ui.Ui;

public class Miffy {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    private static final String FILE_PATH = "./data/miffy.txt";

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

    public static void main(String[] args) {
        new Miffy(FILE_PATH).run();
    }
}