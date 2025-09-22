package ittybotty;

import ittybotty.commands.AddTaskCommand;
import ittybotty.commands.DeleteCommand;
import ittybotty.commands.ExitCommand;
import ittybotty.commands.ListCommand;
import ittybotty.commands.MarkTaskCommand;
import ittybotty.commands.UnmarkTaskCommand;
import ittybotty.data.tasks.Event;
import ittybotty.data.tasks.TaskWithDeadline;
import ittybotty.data.tasks.ToDo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link InputParser}.
 */
public class InputParserTest {

    private InputParser parser;

    /**
     * Sets up a fresh {@link InputParser} instance before each test.
     */
    @BeforeEach
    public void setUp() {
        this.parser = new InputParser();
    }

    /**
     * Tests that the {@code list} command is parsed into a
     * {@link ListCommand}.
     */
    @Test
    public void parseInput_listCommand_parsedCorrectly() {
        assertEquals(ListCommand.getInstance(), this.parser.parseInput("list"));
    }

    /**
     * Tests that the {@code bye} command is parsed into an
     * {@link ExitCommand}.
     */
    @Test
    public void parseInput_exitCommand_parsedCorrectly() {
        assertEquals(ExitCommand.getInstance(), this.parser.parseInput("bye"));
    }

    /**
     * Tests that the {@code mark <taskIndex>} command is parsed into a
     * {@link MarkTaskCommand}.
     */
    @Test
    public void parseInput_markCommand_parsedCorrectly() {
        assertEquals(new MarkTaskCommand(1), this.parser.parseInput("mark 1"));
        assertEquals(new MarkTaskCommand(123), this.parser.parseInput("mark 123"));
        assertEquals(new MarkTaskCommand(Integer.MAX_VALUE),
                this.parser.parseInput("mark " + Integer.MAX_VALUE));
    }

    /**
     * Tests that the {@code unmark <taskIndex>} command is parsed into an
     * {@link UnmarkTaskCommand}.
     */
    @Test
    public void parseInput_unmarkCommand_parsedCorrectly() {
        assertEquals(new UnmarkTaskCommand(1), this.parser.parseInput("unmark 1"));
        assertEquals(new UnmarkTaskCommand(123), this.parser.parseInput("unmark 123"));
        assertEquals(new UnmarkTaskCommand(Integer.MAX_VALUE),
                this.parser.parseInput("unmark " + Integer.MAX_VALUE));
    }

    /**
     * Tests that the {@code todo <description>} command is parsed into
     * an {@link AddTaskCommand} containing a {@link ToDo}.
     */
    @Test
    public void parseInput_addTodoCommand_parsedCorrectly() {
        // Test single-letter description
        assertEquals(new AddTaskCommand(new ToDo("x")),
                this.parser.parseInput("todo x"));

        // Test single-word description
        assertEquals(new AddTaskCommand(new ToDo("exercise")),
                this.parser.parseInput("todo exercise"));

        // Test multiple-word description
        assertEquals(new AddTaskCommand(new ToDo("buy fruits and vegetables to eat")),
                this.parser.parseInput("todo buy fruits and vegetables to eat"));
    }

    /**
     * Tests that the {@code deadline <description> /by <date>} command
     * is parsed into an {@link AddTaskCommand} containing a
     * {@link TaskWithDeadline}.
     */
    @Test
    public void parseInput_addDeadlineCommand_parsedCorrectly() {
        assertEquals(new AddTaskCommand(
                new TaskWithDeadline("y", LocalDate.of(2025, 1, 1))),
                this.parser.parseInput("deadline y /by 2025-01-01"));
        assertEquals(new AddTaskCommand(
                        new TaskWithDeadline("submit presentation slides",
                                LocalDate.of(2020, 12, 31))),
                this.parser.parseInput("deadline submit presentation slides /by 2020-12-31"));
        assertEquals(new AddTaskCommand(
                        new TaskWithDeadline("abc", LocalDate.EPOCH)),
                this.parser.parseInput("deadline abc /by 1970-01-01"));
        assertEquals(new AddTaskCommand(
                        new TaskWithDeadline("abc", LocalDate.of(9999, 12, 31))),
                this.parser.parseInput("deadline abc /by 9999-12-31"));
        assertEquals(new AddTaskCommand(
                        new TaskWithDeadline("celebrate leap day", LocalDate.of(2028, 2, 29))),
                this.parser.parseInput("deadline celebrate leap day /by 2028-02-29"));
    }

    /**
     * Tests that the {@code event <description> /from <startDate>
     *     /to <endDate>} command is parsed into an
     *     {@link AddTaskCommand} containing an {@link Event}.
     */
    @Test
    public void parseInput_addEventCommand_parsedCorrectly() {
        // Single-day event
        assertEquals(new AddTaskCommand(new Event("birthday celebration",
                LocalDate.of(2026, 2, 7), LocalDate.of(2026, 2, 7))),
                this.parser.parseInput("event birthday celebration /from 2026-02-07 /to 2026-02-07"));

        // Regular event
        assertEquals(new AddTaskCommand(new Event("staycation",
                        LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 8))),
                this.parser.parseInput("event staycation /from 2026-06-01 /to 2026-06-08"));

        // Edge case
        assertEquals(new AddTaskCommand(new Event("abc",
                        LocalDate.EPOCH, LocalDate.of(9999, 12, 31))),
                this.parser.parseInput("event abc /from 1970-01-01 /to 9999-12-31"));
    }

    /**
     * Tests that the {@code delete <taskIndex>} command is parsed into
     * a {@link DeleteCommand}.
     */
    @Test
    public void parseInput_deleteCommand_parsedCorrectly() {
        assertEquals(new DeleteCommand(1), this.parser.parseInput("delete 1"));
        assertEquals(new DeleteCommand(123), this.parser.parseInput("delete 123"));
        assertEquals(new DeleteCommand(Integer.MAX_VALUE),
                this.parser.parseInput("delete " + Integer.MAX_VALUE));
    }

    /**
     * Tests that invalid input commands throw an
     * {@link IllegalArgumentException}.
     */
    @Test
    public void parseInput_invalidCommand_exceptionThrown() {
        final String expectedMessage = "Invalid user input";
        final String[] testIllegalCommands =
                {"hello world", "add CS2103T homework", "x", "I LOVE CS",
                        "12345", "/////"};
        // Code inspired from https://www.baeldung.com/junit-assert-exception
        for (String command : testIllegalCommands) {
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    this.parser.parseInput("hello"));
            assertEquals(expectedMessage, exception.getMessage());
        }
    }

    /**
     * Tests that extra spaces or tab whitespace in user input are
     * trimmed correctly.
     */
    @Test
    public void parseInput_extraSpaces_trimsSpaces() {
        assertEquals(ListCommand.getInstance(), this.parser.parseInput("list   "));
        assertEquals(ListCommand.getInstance(), this.parser.parseInput("     list"));
        assertEquals(ListCommand.getInstance(), this.parser.parseInput("  list  "));
//        // test tab white-space
        assertEquals(ListCommand.getInstance(), this.parser.parseInput("    list"));
        assertEquals(ListCommand.getInstance(), this.parser.parseInput("list"));
        assertEquals(ListCommand.getInstance(), this.parser.parseInput("        list    "));
    }
}
