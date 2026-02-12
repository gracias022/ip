package miffy.command;

import miffy.exception.MiffyException;
import miffy.storage.Storage;
import miffy.task.Task;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Handles execution logic for adding a task to list.
 * <p>
 * When executed, this command adds a new {@link Task} to the {@link TaskList},
 * saves the updated list to storage, and notifies the user via the {@link Ui}.
 */
public class AddCommand extends Command {
    private Task task;

    /**
     * Constructs an AddCommand for the specified task.
     *
     * @param task {@link Task} to be added to task list.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Adds the task to the list, saves the updated list
     * to storage, and displays a confirmation message via the UI.
     *
     * @param tasks {@link TaskList} to which the task will be added.
     * @param ui {@link Ui} for showing feedback to user.
     * @param storage {@link Storage} for persisting the updated task list.
     * @throws MiffyException If saving to storage fails.
     */
    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) throws MiffyException {
        tasks.add(task);
        storage.save(tasks.getAllTasks());
        ui.showOpsConfirmation(task, "Got it. I've added", tasks.getTaskCount());
    }

}
