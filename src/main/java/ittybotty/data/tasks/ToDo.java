package ittybotty.data.tasks;

public class ToDo extends Task {
    public ToDo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toCsvString() {
        return "T," + super.toCsvString();
    }
}
