package miffy.command;

import miffy.exception.MiffyException;
import miffy.storage.Storage;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Represents a user command in the Miffy application.
 * <p>
 * Concrete subclasses implement specific actions such as adding tasks,
 * marking/unmarking tasks, deleting tasks, listing tasks, or exiting the application.
 */
public abstract class Command {

    /**
     * Executes a command using the given task list, user interface, and storage.
     * <p>
     * This method performs common precondition checks to ensure that the provided
     * {@link TaskList}, {@link Ui}, and {@link Storage} objects are not {@code null}.
     * These checks are implemented using assertions and represent internal program
     * invariants rather than user-facing error handling.
     * <p>
     * Subclasses should not override this method; instead, they must implement
     * {@link #executeCommand(TaskList, Ui, Storage)} to provide command-specific
     * behavior.
     *
     * @param tasks {@link TaskList} containing the current tasks; must not be {@code null}.
     * @param ui {@link Ui} used for displaying output to the user; must not be {@code null}.
     * @param storage {@link Storage} responsible for persisting task data; must not be {@code null}.
     * @throws MiffyException If command execution fails.
     */
    public final void execute(TaskList tasks, Ui ui, Storage storage) throws MiffyException {
        assert tasks != null : "TaskList must not be null";
        assert ui != null : "UI must not be null";
        assert storage != null : "Storage must not be null";

        executeCommand(tasks, ui, storage);
    }

    public abstract void executeCommand(TaskList tasks, Ui ui, Storage storage)
            throws MiffyException;

    public boolean isExit() {
        return false;
    }
}
