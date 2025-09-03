package ittybotty.commands;

public final class ListCommand extends UserCommand {
    private static final ListCommand INSTANCE = new ListCommand();

    private ListCommand() {
        // Suppress default constructor as singleton
    }

    public static ListCommand getInstance() {
        return ListCommand.INSTANCE;
    }

    @Override
    public String toString() {
        return "ittybotty.commands.ListCommand{}";
    }
}
