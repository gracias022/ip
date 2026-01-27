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
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws MiffyException;

    public boolean isExit() {
        return false;
    }
}