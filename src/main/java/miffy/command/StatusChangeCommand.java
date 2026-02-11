package miffy.command;

import miffy.exception.MiffyException;
import miffy.storage.Storage;
import miffy.task.Task;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Abstract base class for commands that modify the completion status of a task.
 * Handles common logic for index validation, list saving, and UI confirmation.
 */
public abstract class StatusChangeCommand extends Command {
    protected final int zeroBasedIndex;

    /**
     * Constructs a StatusChangeCommand with the given zero-based index.
     *
     * @param zeroBasedIndex Index of the task to modify (0-based).
     */
    public StatusChangeCommand(int zeroBasedIndex) {
        this.zeroBasedIndex = zeroBasedIndex;
    }

    /**
     * Executes the status change command.
     * Performs index validation, calls an abstract method for the specific task operation,
     * saves the updated list to storage, and displays a status change confirmation via the Ui.
     *
     * @param tasks {@link TaskList} containing the task.
     * @param ui {@link Ui} for showing feedback to user.
     * @param storage {@link Storage} for persisting the updated task list.
     * @throws MiffyException If specified index is invalid or saving fails.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MiffyException {
        if (zeroBasedIndex < 0 || zeroBasedIndex >= tasks.getTaskCount()) {
            throw new MiffyException("Oops! This task number doesn’t exist :(");
        }
        Task affectedTask = performStatusChange(tasks, zeroBasedIndex);
        storage.save(tasks.getAllTasks());
        ui.showTaskStatusChanged(affectedTask);
    }

    /**
     * Abstract method to be implemented by subclasses to perform the specific status change operation.
     *
     * @param tasks {@link TaskList} containing the task.
     * @param index Zero-based index of the task to modify.
     * @return Task object that was modified.
     */
    protected abstract Task performStatusChange(TaskList tasks, int index);
}
