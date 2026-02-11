package miffy.command;

import miffy.task.Task;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Represents a command that marks a {@link Task} as done in the {@link TaskList}.
 * <p>
 * When executed, this command marks the task at the given index, saves the updated list
 * to storage, and shows a status change confirmation via the {@link Ui}.
 */
public class MarkCommand extends StatusChangeCommand {

    /**
     * Constructs a MarkCommand for the task at the given zero-based index.
     *
     * @param zeroBasedIndex Index of task to mark as done (0-based).
     */
    public MarkCommand(int zeroBasedIndex) {
        super(zeroBasedIndex);
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param tasks {@link TaskList} containing the task.
     * @param index Zero-based index of the task to mark.
     */
    @Override
    protected Task performStatusChange(TaskList tasks, int index) {
        return tasks.markAsDone(index);
    }
}
