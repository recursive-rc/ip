import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IttyBotty {
    private static final String CHATBOT_NAME = "Itty-Botty";
    private static final List<Task> taskList = new ArrayList<>();
    
    public static void main(String[] args) {
        IttyBotty.greetUser();
        
        boolean hasExited = false;
        Scanner scanner = new Scanner(System.in);
        InputParser parser = new InputParser();
        while (!hasExited) {
            String userInput = scanner.nextLine();
            UserCommand command = parser.parseInput(userInput);
            System.out.println(command);  // TODO: actually handle command
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
