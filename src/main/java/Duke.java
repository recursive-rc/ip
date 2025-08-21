public class Duke {
    private static final String CHATBOT_NAME = "Itty-Botty";
    
    public static void main(String[] args) {
        Duke.greetUser();
    }
    
    private static void greetUser() {
        Duke.printHorizontalLine();
        System.out.println("Hello! I'm " + Duke.CHATBOT_NAME);
        System.out.println("What can I do for you?");
        Duke.printHorizontalLine();
    }
    
    private static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }
    
    private static void printHorizontalLine() {
        System.out.println("_____________________________________________" +
                "_______________");
    }
}
