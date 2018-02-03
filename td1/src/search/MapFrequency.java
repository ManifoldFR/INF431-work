package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class MapFrequency extends Thread {
    private BufferedReader data;
    private String[] queries;
    private LinkedBlockingQueue<String> result;
    private int processedItems;
    private int matchesFound;


    public MapFrequency(BufferedReader data, String[] queries, LinkedBlockingQueue<String> result) {
        this.data = data;
        this.queries = queries;
        this.result = result;
    }

    public void printStatus() {
        System.out.format("Thread \"%s\" processed %d items, found %d matches.\n",this.getName(),processedItems,matchesFound);
    }


    @Override
    public void run() {
        String currentLine;
        while(true) {

            try {
                currentLine = data.readLine();
                if (currentLine == null) {
                    result.offer(Searcher.EOF);
                    break;
                }

                for(String q: queries) {
                    if (currentLine.contains(q)) {
                        matchesFound++;
                        result.offer(q);
                    }
                }
            } catch (IOException ex) {
                result.offer(Searcher.EOF);
            }
            processedItems++;
        }
    }

}
