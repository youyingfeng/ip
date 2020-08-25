package duke;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }
    
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String[] serialize() {
        String[] output = new String[3];
        output[0] = this.isDone
                ? "1"
                : "0";
        output[1] = "Todo";
        output[2] = description;

        return output;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}