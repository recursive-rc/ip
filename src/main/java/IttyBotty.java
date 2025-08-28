import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class IttyBotty {
    private static final String CHATBOT_NAME = "Itty-Botty";
    private static final List<Task> taskList = new ArrayList<>();

    private static final String DEFAULT_FILE_PATH =
            "./data/tasklist.txt";
    private static final char DELIMETER = ',';
    
    public static void main(String[] args) {
        IttyBotty.greetUser();
        
        boolean hasExited = false;
        Scanner scanner = new Scanner(System.in);
        InputParser parser = new InputParser();
        while (!hasExited) {
            String userInput = scanner.nextLine();
            UserCommand command;
            try {
                command = parser.parseInput(userInput);
            } catch (EmptyDescriptionException e) {
                IttyBotty.printFancyOutput("Oh no! The " + e.getTaskType() +
                        " description is empty.");
                continue;
            } catch (IllegalArgumentException e) {
                IttyBotty.printFancyOutput("Oh no! IttyBotty does not " +
                        "recognise this command.");
                continue;
            }
            // TODO: Use dynamic binding to replace instanceof checks below
            if (command instanceof AddTaskCommand addTaskCommand) {
                Task newTask = addTaskCommand.getTask();
                taskList.add(newTask);
                IttyBotty.printFancyOutput(
                        "Successfully added the following task:\n" +
                                newTask +
                                "\nYou now have " + taskList.size() +
                                " tasks stored.");
            } else if (command instanceof MarkTaskCommand markCommand) {
                Task taskToMark = taskList.get(markCommand.getTaskIndex() - 1);
                // TODO: handle IndexOutOfBoundsException
                // - 1 because 0-indexed
                taskToMark.markDone();
                IttyBotty.printFancyOutput("Good job! " +
                        "The task below is recorded as done!\n" +
                        taskToMark);
            } else if (command instanceof UnmarkTaskCommand unmarkCommand) {
                Task taskToUnmark = taskList.get(unmarkCommand.getTaskIndex() - 1);
                // TODO: handle IndexOutOfBoundsException
                // - 1 because 0-indexed
                taskToUnmark.unmarkDone();
                IttyBotty.printFancyOutput("Alright, " +
                        "The task below has been unmarked!\n" +
                        taskToUnmark);
            } else if (command instanceof ListCommand) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < taskList.size(); i++) {
                    int taskNum = i + 1;
                    builder.append(taskNum).append(". ")
                            .append(taskList.get(i));
                    if (taskNum != taskList.size()) {
                        builder.append('\n');
                    }
                }
                IttyBotty.printFancyOutput(builder.toString());
            } else if (command instanceof ExitCommand) {
                IttyBotty.exit();
                hasExited = true;
            } else if (command instanceof DeleteCommand deleteCommand) {
                final int deleteIndex = deleteCommand.getTaskIndex() - 1;
                // - 1 because 0-indexed
                final Task deletedTask = taskList.get(deleteIndex);
                taskList.remove(deleteIndex);
                IttyBotty.printFancyOutput("Successfully deleted:\n" +
                        deletedTask +
                        "\nYou have " + taskList.size() + " tasks remaining.");
            } else {
                throw new IllegalStateException("Unknown user command.");
            }
        }
    }
    
    private static void greetUser() {
        String greeting = "Hello! I'm " + IttyBotty.CHATBOT_NAME
                + "\nWhat can I do for you?";
        printFancyOutput(greeting);
    }

    private static List<Task> loadFromFile(File taskListFile) {
        return new ArrayList<>();  // TODO: implement properly
    }

    private static void saveToFile(File taskFileList) throws IOException {
        // Code below inspired by https://stackoverflow.com/a/7469050
        if (!taskFileList.getParentFile().exists()) {
            boolean isMkdrsSuccessful =
                    taskFileList.getParentFile().mkdirs();
            if (!isMkdrsSuccessful) {
                throw new IOException(
                        "Unable to create necessary parent directories.");
            }
        }
        if (!taskFileList.exists()) {
            taskFileList.createNewFile();
        }

        try (FileWriter writer = new FileWriter(taskFileList)) {
            for (Task task : IttyBotty.taskList) {
                writer.write(task.toCsvString() + '\n');
            }
        }
    }
    
    private static void exit() {
        try {
            IttyBotty.saveToFile(new File(IttyBotty.DEFAULT_FILE_PATH));
            printFancyOutput("Your tasks have been successfully saved!\n" +
                    "Bye. Hope to see you again soon!");
        } catch (IOException e) {
            printFancyOutput(
                    "Unfortunately, we could not save your task list :(.");
            // For debug only
            // TODO: delete before production
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Prints output with indents and surrounded by
     * horizontal lines.
     *
     * @param output String to be formatted.
     */
    private static void printFancyOutput(String output) {
        final String indent = "    ";
        final String horizontalLine =
                "____________________________________________________________";
        final StringBuilder builder = new StringBuilder();
        builder.append(indent).append(horizontalLine).append('\n');
        for (String line : output.split("\n")) {
            builder.append(indent).append(line).append('\n');
        }
        builder.append(indent).append(horizontalLine);
        System.out.println(builder);
    }
}
