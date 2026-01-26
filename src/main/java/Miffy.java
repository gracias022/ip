public class Miffy {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    private static final String FILE_PATH = "./data/miffy.txt";

    public Miffy(String filePath) {
        storage = new Storage(filePath);
        ui = new Ui();

        try {
            taskList = new TaskList(storage.load());
        } catch (MiffyException e) {
            ui.showLoadingError();
            taskList = new TaskList();
        }
    }

    public static void main(String[] args) {
        Miffy miffy = new Miffy(FILE_PATH);
        boolean isExit = false;

        miffy.ui.showWelcome();

        while (!isExit) {
            try {
                String fullCommand = miffy.ui.readCommand();
                miffy.ui.showLine();
                Command command = Parser.parse(fullCommand);
                command.execute(miffy.taskList, miffy.ui, miffy.storage);
                isExit = command.isExit();
            } catch (MiffyException e) {
                miffy.ui.showError(e.getMessage());
            } finally {
                miffy.ui.showLine();
            }
        }
    }
}