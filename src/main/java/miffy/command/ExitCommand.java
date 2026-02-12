package miffy.command;

import miffy.storage.Storage;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Handles execution logic to exit the Miffy application.
 * <p>
 * When executed, this command displays a goodbye message and closes the input scanner.
 */
public class ExitCommand extends Command {

    /**
     * Shows a goodbye message and closes the scanner.
     *
     * @param tasks Current {@link TaskList} (unused for exit).
     * @param ui {@link Ui} used to display messages and close scanner.
     * @param storage {@link Storage} for task data persistence (unused for exit).
     */
    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
        ui.closeScanner();
    }

    /**
     * Indicates that this command will exit the application.
     *
     * @return true
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
