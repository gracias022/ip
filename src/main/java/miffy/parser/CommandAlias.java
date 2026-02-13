package miffy.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import miffy.command.CommandType;
import miffy.exception.MiffyException;

/**
 * Represents alias commands for the current set of full-length commands.
 */
public class CommandAlias {

    /**
     * Immutable map representing the default alias-command mapping.
     */
    private static final Map<String, CommandType> ALIASES = Map.of(
            "t", CommandType.TODO,
            "d", CommandType.DEADLINE,
            "e", CommandType.EVENT,
            "l", CommandType.LIST,
            "f", CommandType.FIND,
            "del", CommandType.DELETE,
            "m", CommandType.MARK,
            "um", CommandType.UNMARK,
            "sa", CommandType.SETALIAS,
            "al", CommandType.ALIASES
    );

    // User-defined aliases (mutable, in-memory only)
    private static final Map<String, CommandType> customAliases = new HashMap<>();

    /**
     * Expands a command or alias into its corresponding {@link CommandType}.
     *
     * @param lowercaseCommand Lowercase command entered by user.
     * @throws MiffyException If the command does not match any known command or alias.
     */
    public static CommandType expand(String lowercaseCommand) throws MiffyException {
        if (customAliases.containsKey(lowercaseCommand)) {
            return customAliases.get(lowercaseCommand);
        }

        if (ALIASES.containsKey(lowercaseCommand)) {
            return ALIASES.get(lowercaseCommand);
        }

        try {
            return CommandType.valueOf(lowercaseCommand.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MiffyException("Sorry, I don't know what that means :(");
        }
    }

    /**
     * Sets a custom alias. Overrides default alias if conflicts arise.
     *
     * @param alias Alias entered by user.
     * @param command User-typed full command that the alias maps to.
     * @throws MiffyException If user-typed command does not exist.
     */
    public static void setAlias(String alias, String command) throws MiffyException {
        if (!isValidCommand(command)) {
            throw new MiffyException("Oops, the command you typed doesn't exist!");
        }

        customAliases.put(alias.toLowerCase(), CommandType.valueOf(command.toUpperCase()));
    }

    /**
     * Checks if the given user command matches any defined {@link CommandType}.
     *
     * @param command Raw command string entered by the user.
     * @return True if {@code command} corresponds to a CommandType, false otherwise.
     */
    public static boolean isValidCommand(String command) {
        return Arrays.stream(CommandType.values())
                .anyMatch(c -> c.name().equalsIgnoreCase(command));
    }

    public static Map<String, CommandType> getCustomAliases() {
        return customAliases;
    }

    public static Map<String, CommandType> getDefaultAliases() {
        return ALIASES;
    }
}
