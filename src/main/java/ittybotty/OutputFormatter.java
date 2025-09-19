package ittybotty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ittybotty.data.TaskList;
import ittybotty.data.tasks.Task;

/**
 * Handles formatting output for user.
 *
 * <p>All methods in this class are static.</p>
 */
public class OutputFormatter {
    private static final DateTimeFormatter DEFAULT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("EEE d MMM uuuu");
    private DateTimeFormatter dateTimeFormatter = OutputFormatter.DEFAULT_DATE_FORMAT;

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public static String formatDateForOutput(LocalDate date) {
        // TODO: change to instance method
        return date.format(OutputFormatter.DEFAULT_DATE_FORMAT);
    }

    /**
     * Returns String representation of search results.
     *
     * @param searchResults The search results only. All search results
     *                      should be inside in the {@code taskList}.
     * @param taskList Complete list of all tasks of the user, not just
     *                 the search results.
     * @return String representation of bot message with search results,
     *                including their index in the {@code taskList}.
     * @throws RuntimeException If at least one of the tasks in
     *                          {@code searchResults} cannot be found
     *                          in {@code taskList}.
     */
    public String getFormattedSearchResults(List<Task> searchResults, TaskList taskList) {
        String output;
        if (searchResults.isEmpty()) {
            output = "No matching tasks found. :(";
        } else {
            output = "Found these matching tasks!\n";
            for (Task t : searchResults) {
                output += taskList.getTaskWithIndex(t) + "\n";
            }
        }
        return output;
    }
}
