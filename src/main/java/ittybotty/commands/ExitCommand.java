package ittybotty.commands;

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
}
