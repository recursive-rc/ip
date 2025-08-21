import java.util.Scanner;

public class IttyBotty {
    private static final String CHATBOT_NAME = "Itty-Botty";
    private static final String EXIT_COMMAND = "bye";
    
    public static void main(String[] args) {
        IttyBotty.greetUser();
        
        boolean hasExited = false;
        Scanner scanner = new Scanner(System.in);
        while (!hasExited) {
            String userInput = scanner.nextLine();
            if (userInput.equals(IttyBotty.EXIT_COMMAND)) {
                IttyBotty.exit();
                hasExited = true;
            } else {
                IttyBotty.echo(userInput);
            }
        }
    }
    
    private static void greetUser() {
        IttyBotty.printHorizontalLine();
        System.out.println("Hello! I'm " + IttyBotty.CHATBOT_NAME);
        System.out.println("What can I do for you?");
        IttyBotty.printHorizontalLine();
    }
    
    private static void echo(String userInput) {
        IttyBotty.printHorizontalLine();
        System.out.println(userInput);
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
