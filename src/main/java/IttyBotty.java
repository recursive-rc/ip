import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IttyBotty {
    private static final String CHATBOT_NAME = "Itty-Botty";
    
    private final OutputFormatter outputter;
    private TaskList taskList;
    private SaveFileManager saveManager;
    
    public IttyBotty() {
        this.outputter = new OutputFormatter();
        this.taskList = new TaskList();
        this.saveManager = new SaveFileManager();
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
    
    public void run() {
        try {
            this.saveManager.loadFromFile(this.taskList);
            this.greetUser(false, true);
        } catch (FileNotFoundException e) {
            this.greetUser(true, false);
        } catch (IOException e) {
            this.greetUser(false, false);
        }

        boolean hasExited = false;
        boolean hasListChanged;
        Scanner scanner = new Scanner(System.in);
        InputParser parser = new InputParser();
        while (!hasExited) {
            hasListChanged = false;
            String userInput = scanner.nextLine();
            UserCommand command;
            try {
                command = parser.parseInput(userInput);
            } catch (EmptyDescriptionException e) {
                this.outputter.printFancyOutput("Oh no! The " + e.getTaskType() +
                        " description is empty.");
                continue;
            } catch (IllegalArgumentException e) {
                this.outputter.printFancyOutput("Oh no! IttyBotty does not " +
                        "recognise this command.");
                continue;
            }
            // TODO: Use dynamic binding to replace instanceof checks below
            if (command instanceof AddTaskCommand addTaskCommand) {
                Task newTask = addTaskCommand.getTask();
                this.taskList.addTask(newTask);
                this.outputter.printFancyOutput(
                        "Successfully added the following task:\n" +
                                newTask +
                                "\nYou now have " + taskList.size() +
                                " tasks stored.");
                hasListChanged = true;
            } else if (command instanceof MarkTaskCommand markCommand) {
                Task markedTask = this.taskList.markTask(markCommand.getTaskIndex());
                // TODO: handle IndexOutOfBoundsException
                this.outputter.printFancyOutput("Good job! " +
                        "The task below is recorded as done!\n" +
                        markedTask);
                hasListChanged = true;
            } else if (command instanceof UnmarkTaskCommand unmarkCommand) {
                Task taskToUnmark = this.taskList.unmarkTask(unmarkCommand.getTaskIndex());
                // TODO: handle IndexOutOfBoundsException
                this.outputter.printFancyOutput("Alright, " +
                        "The task below has been unmarked!\n" +
                        taskToUnmark);
                hasListChanged = true;
            } else if (command instanceof ListCommand) {
                this.outputter.printFancyOutput(this.taskList.toString());
            } else if (command instanceof ExitCommand) {
                this.exit();
                hasExited = true;
            } else if (command instanceof DeleteCommand deleteCommand) {
                final Task deletedTask = this.taskList.removeTask(
                        deleteCommand.getTaskIndex());
                this.outputter.printFancyOutput("Successfully deleted:\n" +
                        deletedTask +
                        "\nYou have " + this.taskList.size() + " tasks remaining.");
                hasListChanged = true;
            } else {
                throw new IllegalStateException("Unknown user command.");
            }
            if (hasListChanged) {
                try {
                    this.saveManager.saveToFile(this.taskList);
                } catch (IOException e) {
                    this.outputter.printFancyOutput(
                            "Unfortunately, we could not save this " +
                            "change to your task list :(.");
                    // For debug only
                    // TODO: delete before production
                    System.err.println(e.getMessage());
                }
            }
        }
    }
    
    private void greetUser(boolean isFirstTime, boolean isLoadDataSuccess) {
        String greeting = "Hello! I'm " + IttyBotty.CHATBOT_NAME + "!";
        if (isFirstTime) {
            greeting += "\nNice to meet you for the first time!";
        } else if (isLoadDataSuccess) {
            greeting += "\nSuccessfully loaded task list!";
        } else {
            greeting += "Unfortunately, it looks like the previous " +
                    "save data has been corrupted,\nso we'll need to " +
                    "start from scratch.";
        }
        this.outputter.printFancyOutput(greeting);
    }

    private void exit() {
        this.outputter.printFancyOutput("Bye. Hope to see you again soon!");
    }
}
