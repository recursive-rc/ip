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
    
    private OutputFormatter() {}  // Ensure no instantiation
    
    public static String formatDateForOutput(LocalDate date) {
        return date.format(OutputFormatter.DEFAULT_DATE_FORMAT);
    }
    
    // TODO: move IttyBotty::printFancyOutput() to here
}
