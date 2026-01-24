public enum Command {
    BYE,
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    UNKNOWN;

    /**
     * Parses the user's input and returns the corresponding {@code Command}.
     * The first word of the input (after trimming leading whitespace) is treated
     * as the command keyword. If it does not match any predefined command,
     * {@code UNKNOWN} is returned.
     *
     * @param input Raw user input string.
     * @return The matching {@code Command}, or {@code UNKNOWN} if no match is found.
     */
    public static Command fromInput(String input) {
        if (input == null || input.isBlank()) {
            return UNKNOWN;
        }

        String keyword = input.trim().split("\\s+")[0];

        try {
            return Command.valueOf(keyword.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}