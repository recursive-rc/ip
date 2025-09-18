package ittybotty.commands;

/**
 * Represents a user command to find a task
 * by searching with a keyword.
 */
public class FindCommand extends UserCommand {
    private final String searchTerm;

    /**
     * Constructs a user command to find a task
     * by searching with the given search term.
     */
    public FindCommand(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return this.searchTerm;
    }

    @Override
    public String toString() {
        return "FindCommand{"
                + "searchTerm='" + this.searchTerm + '\''
                + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof FindCommand that)) {
            return false;
        }

        return this.searchTerm.equals(that.searchTerm);
    }

    @Override
    public int hashCode() {
        return this.searchTerm.hashCode();
    }
}
