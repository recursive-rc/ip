public class IttyBotty {
    private static final String CHATBOT_NAME = "Itty-Botty";
    
    public static void main(String[] args) {
        IttyBotty.greetUser();
    }
    
    private static void greetUser() {
        IttyBotty.printHorizontalLine();
        System.out.println("Hello! I'm " + IttyBotty.CHATBOT_NAME);
        System.out.println("What can I do for you?");
        IttyBotty.printHorizontalLine();
    }
    
    private static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }
    
    private static void printHorizontalLine() {
        System.out.println("_____________________________________________" +
                "_______________");
    }
}
