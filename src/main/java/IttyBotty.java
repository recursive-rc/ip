import java.util.Scanner;

public class IttyBotty {
    private static final String CHATBOT_NAME = "Itty-Botty";
    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final TaskList TASK_LIST = new TaskList();
    
    public static void main(String[] args) {
        IttyBotty.greetUser();
        
        boolean hasExited = false;
        Scanner scanner = new Scanner(System.in);
        while (!hasExited) {
            String userInput = scanner.nextLine();
            switch (userInput) {
                case IttyBotty.LIST_COMMAND ->
                        printFancyOutput(TASK_LIST.toString());
                case IttyBotty.EXIT_COMMAND -> {
                    IttyBotty.exit();
                    hasExited = true;
                }
                default -> {
                    Task newTask = new Task(userInput);
                    IttyBotty.TASK_LIST.addTask(newTask);
                    printFancyOutput("added: " + newTask);
                }
            }
        }
    }
    
    private static void greetUser() {
        String greeting = "Hello! I'm " + IttyBotty.CHATBOT_NAME
                + "\nWhat can I do for you?";
        printFancyOutput(greeting);
    }
    
    private static void exit() {
        printFancyOutput("Bye. Hope to see you again soon!");
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
