package ittybotty.commands;

/**
 * Represents a command from the user requesting for a list
 * of current tasks.
 *
 * <p>This class is a singleton. To get a ListCommand object,
 * use {@link ListCommand#getInstance() ListCommand.getInstance()}.</p>
 */
public final class ListCommand extends UserCommand {
    private static final ListCommand INSTANCE = new ListCommand();

    private ListCommand() {
        // Suppress default constructor as singleton
    }

    /**
     * Returns a command representing a request for a list of
     * current tasks.
     */
    public static ListCommand getInstance() {
        return ListCommand.INSTANCE;
    }

    @Override
    public String toString() {
        return "ittybotty.commands.ListCommand{}";
    }
}
