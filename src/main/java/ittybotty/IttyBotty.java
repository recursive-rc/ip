package ittybotty;

import java.io.FileNotFoundException;
import java.io.IOException;

import ittybotty.commands.UserCommand;
import ittybotty.commands.UserCommand.CommandResult;
import ittybotty.data.TaskList;
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

    public String handleInputAndGetOutput(String userInput) {
        InputParser parser = new InputParser();
        UserCommand command;

        try {
            command = parser.parseInput(userInput);
        } catch (EmptyDescriptionException e) {
            return "Oh no! The " + e.getTaskType() + " description is empty.";
        } catch (IllegalArgumentException e) {
            return "Oh no! ittybotty.IttyBotty does not " + "recognise this command.";
        }

        CommandResult result = command.run(this.taskList,
                this.outputter, this.saveManager);

        this.isToExit = result.isToExit();
        return result.botMessage();
    }
}
