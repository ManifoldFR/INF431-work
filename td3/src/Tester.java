package td3a;

public class Tester implements Runnable {
    private int Ninserts;
    private Stack<Integer> stack;

    public Tester(int n, Stack<Integer> stack) {
        Ninserts = n;
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 0; i< Ninserts; i++) stack.push(i);
    }
}
