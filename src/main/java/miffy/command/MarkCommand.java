package miffy.command;

import miffy.exception.MiffyException;
import miffy.storage.Storage;
import miffy.task.Task;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Represents a command that marks a {@link Task} as done in the {@link TaskList}.
 * <p>
 * When executed, this command marks the task at the given index, saves the updated list
 * to storage, and shows a status change confirmation via the {@link Ui}.
 */
public class MarkCommand extends Command {
    private final int zeroBasedIndex;

    /**
     * Constructs a MarkCommand for the task at the given zero-based index.
     *
     * @param zeroBasedIndex Index of task to mark as done (0-based).
     */
    public MarkCommand(int zeroBasedIndex) {
        this.zeroBasedIndex = zeroBasedIndex;
    }

    /**
     * Marks the task as done, saves the updated list, and displays a confirmation message.
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
        Task task = tasks.markAsDone(zeroBasedIndex);
        storage.save(tasks.getAllTasks());
        ui.showTaskStatusChanged(task);
    }
}
