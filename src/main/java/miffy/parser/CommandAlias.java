package miffy.parser;

import java.util.Map;

/**
 * Represents alias commands for the current set of full-length commands.
 */
public class CommandAlias {
    private static final Map<String, String> ALIASES = Map.of(
            "t", "todo",
            "d", "deadline",
            "e", "event",
            "l", "list",
            "f", "find",
            "del", "delete",
            "m", "mark",
            "um", "unmark"
    );

    /**
     * Expands a command alias to its full form.
     * Returns the input unchanged if it's not an alias.
     */
    public static String expand(String command) {
        return ALIASES.getOrDefault(command.toLowerCase(), command);
    }
}
