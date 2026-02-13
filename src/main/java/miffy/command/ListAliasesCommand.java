package miffy.command;

import java.util.Map;

import miffy.parser.CommandAlias;
import miffy.storage.Storage;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Represents a command that displays all available aliases.
 * <p>
 * This includes both user-defined (custom) aliases and built-in default aliases.
 */
public class ListAliasesCommand extends Command {

    /**
     * Displays the list of aliases.
     *
     * @param tasks Current task list (not modified).
     * @param ui User interface used to display output.
     * @param storage Storage component (not used).
     */
    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) {
        Map<String, CommandType> custom = CommandAlias.getCustomAliases();
        Map<String, CommandType> defaults = CommandAlias.getDefaultAliases();
        ui.showAliases(custom, defaults);
    }
}
