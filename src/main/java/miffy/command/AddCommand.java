package miffy.command;

import miffy.exception.MiffyException;
import miffy.storage.Storage;
import miffy.task.Task;
import miffy.task.TaskList;
import miffy.ui.Ui;

public class AddCommand extends Command {
    private Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MiffyException {
        tasks.add(task);
        storage.save(tasks.getAllTasks());
        ui.showOpsConfirmation(task, "Got it. I've added", tasks.getTaskCount());
    }

}
