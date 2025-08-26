public final class InputParser {
    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND_REGEX = "mark\\s+[0-9]+";
    private static final String UNMARK_COMMAND_REGEX = "unmark\\s+[0-9]+";
    private static final String ADD_TODO_REGEX = "todo.*";
    private static final String ADD_DEADLINE_REGEX = "deadline.*";
    private static final String ADD_EVENT_REGEX =
            "event.*";
    
    /**
     * Parses user input to get corresponding user command object.
     *
     * <p>Whitespace characters at either end of user input are ignored.</p>
     *
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
        } else if (input.matches(InputParser.ADD_TODO_REGEX)) {
            if (input.strip().equals("todo")) {
                throw new EmptyDescriptionException("ToDo");
            }
            final String taskName = input.replaceFirst("todo\\s+", "");
            final ToDo newTask = new ToDo(taskName);
            return new AddTaskCommand(newTask);
        } else if (input.matches(InputParser.ADD_DEADLINE_REGEX)) {
            // Current assumption: only one instance of "/by" in input
            final String taskName = input.replaceFirst("deadline\\s+", "")
                    .replaceFirst("\\s+/by.+", "");
            final String deadline = input.replaceFirst(".+/by\\s+", "");
            final TaskWithDeadline newTask = new TaskWithDeadline(taskName, deadline);
            return new AddTaskCommand(newTask);
            // TODO: Handle empty description
            // TODO: Handle missing "/by"
            // TODO: Handle empty deadline
        } else if (input.matches(InputParser.ADD_EVENT_REGEX)) {
            final String taskName = input.replaceFirst("event\\s+", "")
                    .replaceFirst("\\s+/from.+", "");
            final String startDateTime = input.replaceFirst(".+/from\\s+", "")
                    .replaceFirst("/to.+", "")
                    .strip();
            final String endDateTime = input.replaceFirst(".+/to\\s+", "");
            final Event newTask = new Event(taskName, startDateTime, endDateTime);
            return new AddTaskCommand(newTask);
            // TODO: Handle empty description
            // TODO: Handle missing "/from" and/or "/to"
            // TODO: Handle empty start/end dates/times
        } else {
            throw new IllegalArgumentException("Invalid user input");
        }
    }
    
    // TODO: explore creating singletons for some commands (e.g., exit, list)
}
