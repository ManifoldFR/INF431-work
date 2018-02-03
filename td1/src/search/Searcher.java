package search;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class Searcher extends Thread {
    public static final String EOF = "--EOF--";
    private final BufferedReader data;
    private final String query;
    private final LinkedBlockingQueue<String> result;
    private int processedItems = 0;
    private int matchesFound = 0;

    public Searcher(BufferedReader data,
                    String query,
                    LinkedBlockingQueue<String> result){
        this.data = data;
        this.query = query;
        this.result = result;
    }

    public void printStatus() {
        System.out.format("Thread \"%s\" processed %d items and found %d matching results.\n",
                          this.getName(), this.processedItems, this.matchesFound);
    }

    @Override
    public void run() {
        String currentLine;
        while (true) {
            if (this.isInterrupted()) {
                System.out.format("Thread \"%s\" receiving interruption order.\n", this.getName());
                break;
            } else {
                try {
                    currentLine = data.readLine();
                    if (currentLine == null) {
                        result.offer(Searcher.EOF);
                        break;
                    }

                    if (currentLine.indexOf(query) >= 0) {
                        matchesFound++;
                        result.offer(currentLine);
                    }
                } catch (IOException ex) {
                    result.offer(Searcher.EOF);
                }
                processedItems++;
            }
        }
    }

}
