package miffy.command;

import miffy.task.Task;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Represents a command that unmarks a {@link Task} in the {@link TaskList}.
 * <p>
 * When executed, this command unmarks the task at the given index, saves the updated list
 * to storage, and shows a status change confirmation via the {@link Ui}.
 */
public class UnmarkCommand extends StatusChangeCommand {

    /**
     * Constructs a UnmarkCommand for the task at the given zero-based index.
     *
     * @param zeroBasedIndex Index of task to unmark (0-based).
     */
    public UnmarkCommand(int zeroBasedIndex) {
        super(zeroBasedIndex);
    }

    /**
     * Unmarks the task at the given index.
     *
     * @param tasks {@link TaskList} containing the task.
     * @param index Zero-based index of the task to unmark.
     */
    @Override
    protected Task performStatusChange(TaskList tasks, int index) {
        return tasks.unmark(index);
    }
}
