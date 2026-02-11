package miffy.command;

import miffy.exception.MiffyException;
import miffy.storage.Storage;
import miffy.task.Task;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Represents a command that removes a {@link Task} from a {@link TaskList}.
 * <p>
 * When executed, this command removes the task at the specified index from the list,
 * saves the updated list to storage, and notifies the user via the {@link Ui}.
 */
public class DeleteCommand extends Command {
    private final int zeroBasedIndex;

    /**
     * Constructs a DeleteCommand for the task at the given zero-based index.
     *
     * @param zeroBasedIndex Index of task to delete (0-based).
     */
    public DeleteCommand(int zeroBasedIndex) {
        this.zeroBasedIndex = zeroBasedIndex;
    }

    /**
     * Removes the task from the list, saves the updated list
     * to storage, and displays a confirmation message via the UI.
     *
     * @param tasks {@link TaskList} from which the task will be removed.
     * @param ui {@link Ui} for showing feedback to user.
     * @param storage {@link Storage} for persisting the updated task list.
     * @throws MiffyException If specified index is invalid or saving fails.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MiffyException {
        if (zeroBasedIndex < 0 || zeroBasedIndex >= tasks.getTaskCount()) {
            throw new MiffyException("Oops! This task number doesn’t exist :(");
        }
        Task task = tasks.deleteTask(zeroBasedIndex);
        storage.save(tasks.getAllTasks());
        ui.showOpsConfirmation(task, "Noted. I've removed", tasks.getTaskCount());
    }
}
