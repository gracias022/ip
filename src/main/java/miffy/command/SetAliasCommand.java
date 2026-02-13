package miffy.command;

import miffy.exception.MiffyException;
import miffy.parser.CommandAlias;
import miffy.storage.Storage;
import miffy.task.TaskList;
import miffy.ui.Ui;

/**
 * Represents a command that creates or updates a custom alias
 * for an existing command.
 * <p>
 * The alias maps a short keyword to a full command string.
 */
public class SetAliasCommand extends Command {
    public static final String MESSAGE_USAGE = "Usage: setalias <alias> <command>\n" + "e.g. setalias td todo";
    public static final String MESSAGE_SUCCESS = "Alias set: '%s' → '%s'";

    private final String alias;
    private final String command;

    /**
     * Constructs a SetAliasCommand with the specified alias and command.
     *
     * @param alias Shortcut keyword to be created or updated.
     * @param command Full command that the alias maps to.
     */
    public SetAliasCommand(String alias, String command) {
        this.alias = alias;
        this.command = command;
    }

    /**
     * Registers the alias and displays a confirmation message.
     *
     * @param tasks Current task list (not modified).
     * @param ui User interface used to display output.
     * @param storage Storage component (not used).
     */
    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) throws MiffyException {
        CommandAlias.setAlias(alias, command);
        ui.showAliasSet(MESSAGE_SUCCESS, alias, command);
    }
}
