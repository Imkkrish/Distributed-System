public class PrintTask implements Task {
    private String message;

    public PrintTask(String message) {
        this.message = message;
    }

    @Override
    public String execute() {
        return "Task executed: " + message;
    }
}
