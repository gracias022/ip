public class DeleteCommand extends Command {
    private final int zeroBasedIndex;

    public DeleteCommand(int zeroBasedIndex) {
        this.zeroBasedIndex = zeroBasedIndex;
    }

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
