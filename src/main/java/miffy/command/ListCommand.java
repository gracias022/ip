package miffy.command;

import miffy.storage.Storage;
import miffy.task.TaskList;
import miffy.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks.getAllTasks());
    }
}
