package miffy.command;

import java.util.ArrayList;
import java.util.stream.Collectors;

import miffy.storage.Storage;
import miffy.task.Task;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Represents a command that searches the task list for tasks whose descriptions
 * contain the specified keyword and displays the matching results to the user.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a command that searches for tasks containing the given keyword.
     *
     * @param keyword Keyword used to match task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Finds all tasks whose descriptions contain the given keyword
     * and displays these tasks via the UI.
     *
     * @param tasks Task list to search.
     * @param ui UI for displaying results.
     * @param storage Storage handler (not used by this command).
     */
    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> allMatches = tasks.getAllTasks().stream()
                .filter(task -> task.hasKeyword(keyword))
                .collect(Collectors.toCollection(ArrayList::new));

        ui.showFindResults(allMatches);
    }
}
