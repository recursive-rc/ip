package ittybotty;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import ittybotty.commands.AddTaskCommand;
import ittybotty.commands.DeleteCommand;
import ittybotty.commands.ExitCommand;
import ittybotty.commands.FindCommand;
import ittybotty.commands.ListCommand;
import ittybotty.commands.MarkTaskCommand;
import ittybotty.commands.UnmarkTaskCommand;
import ittybotty.commands.UserCommand;
import ittybotty.data.TaskList;
import ittybotty.data.tasks.Task;
import ittybotty.gui.MainWindow;

public class IttyBotty {
    private static final String CHATBOT_NAME = "Itty-Botty";

    private final OutputFormatter outputter;
    private TaskList taskList;
    private SaveFileManager saveManager;

    private MainWindow mainWindow;

    private boolean isToExit;

    public IttyBotty() {
        this.outputter = new OutputFormatter();
        this.taskList = new TaskList();
        this.saveManager = new SaveFileManager();
        this.isToExit = false;
    }

    public IttyBotty(String saveFilePath) {
        this();
        this.saveManager.setSaveFilePath(saveFilePath);
    }

    public static void main(String[] args) {
        IttyBotty bot;
        if (args.length == 0) {
            bot = new IttyBotty();
        } else {
            // Allow specifying specific file for testing purposes
            bot = new IttyBotty(args[0]);
        }
        bot.run();
    }

    public boolean isToExit() {
        return this.isToExit;
    }

    public void run() {
        try {
            this.saveManager.loadFromFile(this.taskList);
            this.greetUser(false, true);
        } catch (FileNotFoundException e) {
            this.greetUser(true, false);
        } catch (IOException e) {
            this.greetUser(false, false);
        }

    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    private void greetUser(boolean isFirstTime, boolean isLoadDataSuccess) {
        String greeting = "Hello! I'm " + IttyBotty.CHATBOT_NAME + "!";
        if (isFirstTime) {
            greeting += "\nNice to meet you for the first time!";
        } else if (isLoadDataSuccess) {
            greeting += "\nSuccessfully loaded task list!";
        } else {
            greeting += "Unfortunately, it looks like the previous "
                    + "save data has been corrupted,\nso we'll need to "
                    + "start from scratch.";
        }
        this.mainWindow.sendBotMessage(greeting);
    }

    private void exit() {
        this.outputter.printFancyOutput("Bye. Hope to see you again soon!");
    }

    public String handleInputAndGetOutput(String userInput) {
        boolean hasListChanged;
        InputParser parser = new InputParser();
        hasListChanged = false;
        UserCommand command;

        try {
            command = parser.parseInput(userInput);
        } catch (EmptyDescriptionException e) {
            return "Oh no! The " + e.getTaskType() + " description is empty.";
        } catch (IllegalArgumentException e) {
            return "Oh no! ittybotty.IttyBotty does not " + "recognise this command.";
        }

        String botOutput = "";

        // TODO: Use dynamic binding to replace instanceof checks below
        if (command instanceof AddTaskCommand addTaskCommand) {
            Task newTask = addTaskCommand.getTask();
            this.taskList.addTask(newTask);
            botOutput = "Successfully added the following task:\n"
                            + newTask
                            + "\nYou now have " + taskList.size()
                            + " tasks stored.";
            hasListChanged = true;
        } else if (command instanceof MarkTaskCommand markCommand) {
            Task markedTask = this.taskList.markTask(markCommand.getTaskIndex());
            // TODO: handle IndexOutOfBoundsException
            botOutput = "Good job! The task below is recorded as done!\n"
                    + markedTask;
            hasListChanged = true;
        } else if (command instanceof UnmarkTaskCommand unmarkCommand) {
            Task taskToUnmark = this.taskList.unmarkTask(unmarkCommand.getTaskIndex());
            // TODO: handle IndexOutOfBoundsException
            botOutput = "Alright, The task below has been unmarked!\n"
                    + taskToUnmark;
            hasListChanged = true;
        } else if (command instanceof ListCommand) {
            botOutput = this.taskList.toString();
        } else if (command instanceof ExitCommand) {
            this.isToExit = true;
        } else if (command instanceof DeleteCommand deleteCommand) {
            final Task deletedTask = this.taskList.removeTask(
                    deleteCommand.getTaskIndex());
            botOutput = "Successfully deleted:\n" + deletedTask
                    + "\nYou have " + this.taskList.size() + " tasks remaining.";
            hasListChanged = true;
        } else if (command instanceof FindCommand findCommand) {
            final String searchTerm = findCommand.getSearchTerm();
            final List<Task> searchResults = this.taskList.getTasksMatching(searchTerm);
            botOutput = this.outputter.getFormattedSearchResults(searchResults,
                    this.taskList);
        } else {
            throw new IllegalStateException("Unknown user command.");
        }

        if (hasListChanged) {
            try {
                this.saveManager.saveToFile(this.taskList);
            } catch (IOException e) {
                this.outputter.printFancyOutput(
                        "Unfortunately, we could not save this "
                                + "change to your task list :(.");
                // For debug only
                // TODO: delete before production
                System.err.println(e.getMessage());
            }
        }

        return botOutput;
    }
}
