package search;

import java.util.concurrent.LinkedBlockingQueue;

public class Printer extends Thread {
    LinkedBlockingQueue<String> in;
    int items;
    int num;

    public Printer(LinkedBlockingQueue<String> in) {
        this.in = in;
        items = 0;
    }

    public Printer(LinkedBlockingQueue<String> in, int num) {
        this.in = in;
        items = 0;
        this.num = num;
    }

    public void printStatus() {
        System.out.format("Thread \"%s\" done, printed %d items (including EOF).\n",
                this.getName(),items);
    }

    public void run() {
        String currentRes;
        while (true) {

            if (num > 0 && items >= num) {
                this.interrupt();
                break;
            }

            try {
                currentRes = in.take();
                System.out.println(currentRes);
                items++;
            } catch (InterruptedException interrupt) {
                System.out.println("Printing interrupted.");
                throw new RuntimeException("Unexpected runtime interruption.");
            }

            if (currentRes.equals(Searcher.EOF)) {
                break;
            }
        }
    }
}
