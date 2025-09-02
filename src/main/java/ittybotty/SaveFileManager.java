package ittybotty;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveFileManager {
    private static final String DEFAULT_FILE_PATH = "./data/tasklist.txt";
    private File saveFile;
    
    public SaveFileManager() {
        this(SaveFileManager.DEFAULT_FILE_PATH);
    }
    
    public SaveFileManager(String saveFilePath) {
        this.saveFile = new File(saveFilePath);
    }
    
    public void setSaveFilePath(String saveFilePath) {
        // TODO: check if parameter file path is valid
        this.saveFile = new File(saveFilePath);
    }
    
    public void saveToFile(TaskList taskList) throws IOException {
        this.createFileIfDoesNotExistYet();
        
        try (FileWriter writer = new FileWriter(saveFile)) {
            writer.write(taskList.toCsvString() + '\n');
        }
    }
    
    private void createFileIfDoesNotExistYet() throws IOException {
        // Code below inspired by https://stackoverflow.com/a/7469050
        if (!this.saveFile.getParentFile().exists()) {
            this.saveFile.getParentFile().mkdirs();
        }
        if (!this.saveFile.exists()) {
            this.saveFile.createNewFile();
        }
    }
    
    public void loadFromFile(TaskList taskList) throws IOException {
        List<Task> taskListFromFile = new ArrayList<>();
        try (Scanner scanner = new Scanner(this.saveFile)) {
            while (scanner.hasNext()) {
                String currentLine = scanner.nextLine();
                Task currentTask = this.getTaskFromCsvLine(currentLine);
                taskListFromFile.add(currentTask);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("Missing info for some task");
        }
        taskList.addAll(taskListFromFile);
    }
    
    private Task getTaskFromCsvLine(String csvLine) throws IOException {
        List<String> taskInfo = this.parseCsvLine(csvLine);
        String taskType = taskInfo.get(0);
        Task currentTask = switch (taskType) {
            case "T" -> new ToDo(taskInfo.get(2));
            case "D" -> new TaskWithDeadline(taskInfo.get(2),
                    LocalDate.parse(taskInfo.get(3)));
            case "E" -> new Event(taskInfo.get(2),
                    LocalDate.parse(taskInfo.get(3)),
                    LocalDate.parse(taskInfo.get(4)));
            default -> throw new IOException("ittybotty.Task type info corrupted.");
        };
        if (Boolean.parseBoolean(taskInfo.get(1))) {
            // TODO: handle corruption when neither true nor false
            currentTask.markDone();
        }
        return currentTask;
    }
    
    private List<String> parseCsvLine(String line) {
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
}
