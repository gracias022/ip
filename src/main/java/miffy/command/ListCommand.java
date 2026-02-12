package miffy.command;

import miffy.storage.Storage;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Represents a command that lists all tasks in a {@link TaskList}.
 * <p>
 * When executed, this command retrieves all tasks and displays them via the {@link Ui}.
 */
public class ListCommand extends Command {

    /**
     * Retrieves and shows all tasks to the user.
     *
     * @param tasks {@link TaskList} containing tasks to display.
     * @param ui {@link Ui} used to display task list.
     * @param storage {@link Storage} for task data persistence (unused for list operation).
     */
    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks.getAllTasks());
    }
}
