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

    private static final String DEFAULT_FILE_PATH =
            "./data/tasklist.txt";
    private static final char DELIMETER = ',';
    
    private final OutputFormatter outputter;
    private String saveFilePath;
    private TaskList taskList;
    
    public IttyBotty(String saveFilePath) {
        this.saveFilePath = saveFilePath;
        this.outputter = new OutputFormatter();
        this.taskList = new TaskList();
    }
    
    public IttyBotty() {
        this(IttyBotty.DEFAULT_FILE_PATH);
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
            List<Task> listFromFile = IttyBotty.loadFromFile(
                    new File(this.saveFilePath));
            this.taskList = new TaskList(listFromFile);
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
                    this.saveToFile(new File(this.saveFilePath));
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

    private static List<Task> loadFromFile(File taskListFile) throws IOException {
        List<Task> taskListFromFile = new ArrayList<>();
        try (Scanner scanner = new Scanner(taskListFile)) {
            while (scanner.hasNext()) {
                String currentLine = scanner.nextLine();
                List<String> taskInfo = IttyBotty.parseCsvLine(currentLine);
                Task currentTask = switch (taskInfo.get(0)) {
                    case "T" -> new ToDo(taskInfo.get(2));
                    case "D" -> new TaskWithDeadline(taskInfo.get(2),
                            LocalDate.parse(taskInfo.get(3)));
                    case "E" -> new Event(taskInfo.get(2),
                            LocalDate.parse(taskInfo.get(3)),
                            LocalDate.parse(taskInfo.get(4)));
                    default -> throw new IOException(
                            "Task type info corrupted: " + currentLine);
                };
                if (Boolean.parseBoolean(taskInfo.get(1))) {
                    // TODO: handle corruption when neither true nor false
                    currentTask.markDone();
                }
                taskListFromFile.add(currentTask);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("Missing info for some task");
        }
        return taskListFromFile;
    }

    private static List<String> parseCsvLine(String line) {
        // Method implementation inspired by
        // https://stackoverflow.com/a/7800519
        Pattern pattern = Pattern.compile("(\"[^\"]+\"|[^,]+)");
        // To handle quotes and commas within quotes
        Matcher matcher = pattern.matcher(line);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            String item = matcher.group(1);
            if (item.matches("\".+\"")) {  // starts & ends with double quotes
                item = item.substring(1, item.length() - 1);
                // Remove double quotes at start and end
            }
            result.add(item);
        }
        return result;
    }

    private void saveToFile(File taskFileList) throws IOException {
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
            writer.write(this.taskList.toCsvString() + '\n');
        }
    }
    
    private void exit() {
        this.outputter.printFancyOutput("Bye. Hope to see you again soon!");
    }
}
