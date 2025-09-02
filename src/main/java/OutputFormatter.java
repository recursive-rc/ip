import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class that handles formatting output for user.
 *
 * <p>All methods in this class are static.</p>
 */
public class OutputFormatter {
    private static final DateTimeFormatter DEFAULT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("EEE d MMM uuuu");
    private DateTimeFormatter dateTimeFormatter = OutputFormatter.DEFAULT_DATE_FORMAT;
    
    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }
    
    public static String formatDateForOutput(LocalDate date) {
        // TODO: change to instance method
        return date.format(OutputFormatter.DEFAULT_DATE_FORMAT);
    }
    
    /**
     * Prints output with indents and surrounded by
     * horizontal lines.
     *
     * @param output String to be formatted.
     */
    public void printFancyOutput(String output) {
        this.printIndent();
        this.printHorizontalLine();
        for (String line : output.split("\n")) {
            this.printIndent();
            System.out.println(line);
        }
        this.printIndent();
        this.printHorizontalLine();
    }
    
    /**
     * Prints a horizontal line of 60 underscores,
     * followed by a new line.
     * Does not include an indent at the front,
     * use {@link OutputFormatter#printIndent()} for that.
     */
    private void printHorizontalLine() {
        System.out.println("____________________________________________________________");
    }
    
    /**
     * Prints an indent of four spaces,
     * <strong>without</strong> a new line at the end.
     */
    private void printIndent() {
        System.out.print("    ");
    }
}
