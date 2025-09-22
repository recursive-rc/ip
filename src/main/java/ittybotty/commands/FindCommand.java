package ittybotty.commands;

import java.util.List;

import ittybotty.OutputFormatter;
import ittybotty.SaveFileManager;
import ittybotty.data.TaskList;
import ittybotty.data.tasks.Task;

/**
 * Represents a user command to find a task
 * by searching with a keyword.
 */
public class FindCommand extends UserCommand {
    private final String searchTerm;

    /**
     * Constructs a user command to find a task
     * by searching with the given search term.
     */
    public FindCommand(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return this.searchTerm;
    }

    @Override
    public String toString() {
        return "FindCommand{"
                + "searchTerm='" + this.searchTerm + '\''
                + '}';
    }

    // The equals method is overridden for JUnit testing
    // to ensure JUnit's assertEquals() works as intended.
    // The hasCode() method is thus also overridden per
    // the general contract for Object::hasCode.

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof FindCommand that)) {
            return false;
        }

        return this.searchTerm.equals(that.searchTerm);
    }

    @Override
    public int hashCode() {
        return this.searchTerm.hashCode();
    }

    @Override
    public CommandResult run(TaskList taskList, OutputFormatter formatter,
                             SaveFileManager saveManager) {
        List<Task> searchResults = taskList.getTasksMatching(this.searchTerm);
        String botOutput = formatter.getFormattedSearchResults(searchResults,
                taskList);
        return new CommandResult(botOutput);

    }
}
