public class MarkCommand extends Command {
    private final int zeroBasedIndex;

    public MarkCommand(int zeroBasedIndex) {
        this.zeroBasedIndex = zeroBasedIndex;
    }

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
