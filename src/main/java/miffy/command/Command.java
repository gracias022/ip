package miffy.command;

import miffy.exception.MiffyException;
import miffy.storage.Storage;
import miffy.task.TaskList;
import miffy.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws MiffyException;

    public boolean isExit() {
        return false;
    }
}