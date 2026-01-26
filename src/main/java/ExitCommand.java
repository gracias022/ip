public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
        ui.closeScanner();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
