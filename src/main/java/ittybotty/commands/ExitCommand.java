package ittybotty.commands;

public final class ExitCommand extends UserCommand {
    private static final ExitCommand INSTANCE = new ExitCommand();

    private ExitCommand() {
        // Suppress default constructor as singleton
    }

    public static ExitCommand getInstance() {
        return ExitCommand.INSTANCE;
    }

    @Override
    public String toString() {
        return "ittybotty.commands.ExitCommand{}";
    }
}
