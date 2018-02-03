package search;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class ReduceFrequency extends Thread {
    private LinkedBlockingQueue<String> in;
    public Map<String,Integer> count;
    private int items;

    public ReduceFrequency(LinkedBlockingQueue<String> in) {
        this.in = in;
        items = 0;
        count = new HashMap<>();
    }

    public void printStatus() {
        System.out.format("Thread \"%s\" processed %d items.\n",
                this.getName(),this.items);
    }

    @Override
    public void run() {
        String currentRes;
        int val;
        while(true) {
            try {
                currentRes = in.take();
                items++;
                val = count.get(currentRes);
                count.put(currentRes, val+1);
            } catch (InterruptedException exc) {
                System.out.println("Unexpected interrupt.");
            }
        }
    }
}
