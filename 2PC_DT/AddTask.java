public class AddTask implements Task {
    private int a, b;

    public AddTask(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String execute() {
        return "Task executed: Sum = " + (a + b);
    }
}
