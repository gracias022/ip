package miffy.command;

import miffy.exception.MiffyException;
import miffy.storage.Storage;
import miffy.task.Task;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Handles execution logic for unmarking a {@link Task} in the {@link TaskList}.
 * <p>
 * When executed, this command unmarks the task at the given index, saves the updated list
 * to storage, and shows a status change confirmation via the {@link Ui}.
 */
public class UnmarkCommand extends Command {
    private final int zeroBasedIndex;

    /**
     * Constructs a UnmarkCommand for the task at the given zero-based index.
     *
     * @param zeroBasedIndex Index of task to unmark (0-based).
     */
    public UnmarkCommand(int zeroBasedIndex) {
        this.zeroBasedIndex = zeroBasedIndex;
    }

    /**
     * Unmarks the task, saves the updated list, and displays a confirmation message.
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
        Task task = tasks.unmark(zeroBasedIndex);
        storage.save(tasks.getAllTasks());
        ui.showTaskStatusChanged(task);
    }
}
