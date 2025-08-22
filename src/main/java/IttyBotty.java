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
            IttyBotty.printHorizontalLine();
            switch (userInput) {
                case IttyBotty.LIST_COMMAND -> System.out.println(TASK_LIST);
                case IttyBotty.EXIT_COMMAND -> {
                    IttyBotty.exit();
                    hasExited = true;
                }
                default -> {
                    Task newTask = new Task(userInput);
                    IttyBotty.TASK_LIST.addTask(newTask);
                    System.out.println("added: " + newTask);
                }
            }
        }
    }
    
    private static void greetUser() {
        IttyBotty.printHorizontalLine();
        System.out.println("Hello! I'm " + IttyBotty.CHATBOT_NAME);
        System.out.println("What can I do for you?");
        IttyBotty.printHorizontalLine();
    }
    
    private static void exit() {
        IttyBotty.printHorizontalLine();
        System.out.println("Bye. Hope to see you again soon!");
        IttyBotty.printHorizontalLine();
    }
    
    private static void printHorizontalLine() {
        System.out.println("_____________________________________________" +
                "_______________");
    }
}
