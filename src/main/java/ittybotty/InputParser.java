package ittybotty;

import java.time.LocalDate;

import ittybotty.commands.AddTaskCommand;
import ittybotty.commands.DeleteCommand;
import ittybotty.commands.ExitCommand;
import ittybotty.commands.FindCommand;
import ittybotty.commands.ListCommand;
import ittybotty.commands.MarkTaskCommand;
import ittybotty.commands.UnmarkTaskCommand;
import ittybotty.commands.UserCommand;
import ittybotty.data.tasks.Event;
import ittybotty.data.tasks.TaskWithDeadline;
import ittybotty.data.tasks.ToDo;

/**
 * Parses user input.
 */
public final class InputParser {
    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    // TODO: make mark/unmark commands' regex more generic
    private static final String MARK_COMMAND_REGEX = "mark\\s+[0-9]+";
    private static final String UNMARK_COMMAND_REGEX = "unmark\\s+[0-9]+";
    private static final String ADD_TODO_REGEX = "todo.*";
    private static final String ADD_DEADLINE_REGEX = "deadline.*";
    private static final String ADD_EVENT_REGEX =
            "event.*";
    private static final String DELETE_COMMAND_REGEX = "delete.*";
    private static final String FIND_COMMAND_REGEX = "find\\s+\\S+";
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
            return ListCommand.getInstance();
        } else if (input.equals(InputParser.EXIT_COMMAND)) {
            return ExitCommand.getInstance();
        } else if (input.matches(InputParser.MARK_COMMAND_REGEX)) {
            final int taskIndex = Integer.parseInt(
                    input.replaceAll("\\D", ""));
            // TODO: handle missing/non-integer index
            return new MarkTaskCommand(taskIndex);
        } else if (input.matches(InputParser.UNMARK_COMMAND_REGEX)) {
            final int taskIndex = Integer.parseInt(
                    input.replaceAll("\\D", ""));
            // TODO: handle missing/non-integer index
            return new UnmarkTaskCommand(taskIndex);
        } else if (input.matches(InputParser.ADD_TODO_REGEX)) {
            if (input.strip().equals("todo")) {
                throw new EmptyDescriptionException("ittybotty.data.tasks.ToDo");
            }
            final String taskName = input.replaceFirst("todo\\s+", "");
            final ToDo newTask = new ToDo(taskName);
            return new AddTaskCommand(newTask);
        } else if (input.matches(InputParser.ADD_DEADLINE_REGEX)) {
            // Current assumption: only one instance of "/by" in input
            final String taskName = input.replaceFirst("deadline\\s+", "")
                    .replaceFirst("\\s+/by.+", "");
            final LocalDate deadline = this.parseDate(
                    input.replaceFirst(".+/by\\s+", ""));
            final TaskWithDeadline newTask = new TaskWithDeadline(taskName, deadline);
            return new AddTaskCommand(newTask);
            // TODO: Handle empty description
            // TODO: Handle missing "/by"
            // TODO: Handle empty deadline
        } else if (input.matches(InputParser.ADD_EVENT_REGEX)) {
            final String taskName = input.replaceFirst("event\\s+", "")
                    .replaceFirst("\\s+/from.+", "");
            final LocalDate startDate = this.parseDate(
                    input.replaceFirst(".+/from\\s+", "")
                    .replaceFirst("/to.+", "")
                    .strip());
            final LocalDate endDate = this.parseDate(
                    input.replaceFirst(".+/to\\s+", ""));
            final Event newTask = new Event(taskName, startDate, endDate);
            return new AddTaskCommand(newTask);
            // TODO: Handle empty description
            // TODO: Handle missing "/from" and/or "/to"
            // TODO: Handle empty start/end dates/times
        } else if (input.matches(InputParser.DELETE_COMMAND_REGEX)) {
            final int taskIndex = Integer.parseInt(
                    input.replaceAll("\\D", ""));
            // TODO: handle missing/non-integer index
            return new DeleteCommand(taskIndex);
        } else if (input.matches(InputParser.FIND_COMMAND_REGEX)) {
            final String searchTerm = input.replaceFirst("find\\s+", "");
            return new FindCommand(searchTerm);
        } else {
            throw new IllegalArgumentException("Invalid user input");
        }
    }

    private LocalDate parseDate(String dateAsString) {
        return LocalDate.parse(dateAsString);
        // TODO: allow more date formats
        // This method is created so more input date formats can be supported
        // in all different input scenarios just by changing this
        // method implementation.
    }
}
