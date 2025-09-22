package ittybotty.commands;

import ittybotty.OutputFormatter;
import ittybotty.SaveFileManager;
import ittybotty.data.TaskList;

/**
 * Represents a command requesting to exit the application.
 *
 * <p>This class is a singleton. To get a ListCommand object,
 * use {@link ListCommand#getInstance() ListCommand.getInstance()}.</p>
 */
public final class ExitCommand extends UserCommand {
    private static final ExitCommand INSTANCE = new ExitCommand();

    private ExitCommand() {
        // Suppress default constructor as singleton
    }

    /**
     * Returns a command representing a requesting to exit the
     * application.
     */
    public static ExitCommand getInstance() {
        return ExitCommand.INSTANCE;
    }

    @Override
    public String toString() {
        return "ittybotty.commands.ExitCommand{}";
    }

    @Override
    public CommandResult run(TaskList taskList, OutputFormatter formatter,
                             SaveFileManager saveManager) {
        return new CommandResult("", true);
    }
}
