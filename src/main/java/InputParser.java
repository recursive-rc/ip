public final class InputParser {
    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND_REGEX = "mark\\s+[0-9]+";
    private static final String UNMARK_COMMAND_REGEX = "unmark\\s+[0-9]+";
    
    /**
     * Parses user input to get corresponding user command object.
     *
     * <p>Parsing based on the following rules:</p>
     *
     * <ul>
     *     <li>If input contains <em>only</em> the word "list",
     *     then returns a {@link ListCommand} object.</li>
     *     <li>If input contains <em>only</em> the word "bye",
     *     then returns a {@link ExitCommand} object.</li>
     *     <li>If input is "mark" or "unmark" followed by an integer,
     *     then returns a {@link MarkTaskCommand} or
     *     {@link UnmarkTaskCommand} respectively with the
     *     integer as the index of the task to be removed. Multiple
     *     whitespaces between "mark"/"unmark" and the integer
     *     are allowed to accommodate for user typos.</li>
     *     <li>Otherwise, returns an {@link AddTaskCommand}
     *     with the input as the name of the task to be added.</li>
     * </ul>
     *
     * Whitespace characters at either end of user input are ignored.
     * @param input User input to parse.
     */
    public UserCommand parseInput(String input) {
        input = input.strip();
        // Code to get task index from input string
        // inspired from https://stackoverflow.com/a/2338819
        if (input.equals(InputParser.LIST_COMMAND)) {
            return new ListCommand();
        } else if (input.equals(InputParser.EXIT_COMMAND)) {
            return new ExitCommand();
        } else if (input.matches(InputParser.MARK_COMMAND_REGEX)) {
            final int taskIndex = Integer.parseInt(
                    input.replaceAll("\\D", ""));
            return new MarkTaskCommand(taskIndex);
        } else if (input.matches(InputParser.UNMARK_COMMAND_REGEX)) {
            final int taskIndex = Integer.parseInt(
                    input.replaceAll("\\D", ""));
            return new UnmarkTaskCommand(taskIndex);
        } else {
            return new AddTaskCommand(input);
        }
    }
    
    // TODO: explore creating singletons for some commands (e.g., exit, list)
}
