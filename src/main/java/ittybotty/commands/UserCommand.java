package ittybotty.commands;

import ittybotty.OutputFormatter;
import ittybotty.SaveFileManager;
import ittybotty.data.TaskList;

/**
 * Represents a user command.
 */
public abstract class UserCommand {
    public abstract CommandResult run(TaskList taskList,
                                      OutputFormatter formatter,
                                      SaveFileManager saveManager);

    /**
     * Represents results of running a user command, containing all
     * necessary information to be passed to
     * {@link ittybotty.IttyBotty}.
     *
     * <p>Outputting bot message to user and/or exiting the
     * program should be handled by different class(es) in accordance
     * with the single-responsibility principle.</p>
     *
     * @param botMessage Message for bot to send to user.
     * @param isToExit True if the program should quit after handling
     *                 the command, false otherwise.
     */
    public record CommandResult(String botMessage, boolean isToExit) {
        public CommandResult(String botMessage) {
            this(botMessage, false);
        }
    }
}
