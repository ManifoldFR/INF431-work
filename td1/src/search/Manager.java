package search;
import java.io.BufferedReader;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.*;

public class Manager {
    public static LinkedBlockingQueue<String> simpleSearch(BufferedReader data, String query, int num) throws InterruptedException {
        LinkedBlockingQueue<String> result = new LinkedBlockingQueue<>();
        Searcher s = new Searcher(data, query, result);
        s.setName("searcher");
        s.start();
        Thread.sleep(100);
        s.printStatus();
        return result;
    }

    public static LinkedBlockingQueue<String> pollingSearch(BufferedReader data, String query, int num) throws InterruptedException {
        LinkedBlockingQueue<String> result = new LinkedBlockingQueue<>();
        Searcher s = new Searcher(data,query,result);
        s.setName("Polling searcher");
        s.start();
        while(true) {
            Thread.sleep(100);
            s.printStatus();
            if (!s.isAlive()) break;
        }
        return result;
    }

    public static LinkedBlockingQueue<String> waitingSearch(BufferedReader data, String query, int num) throws InterruptedException {
        LinkedBlockingQueue<String> result = new LinkedBlockingQueue<>();
        Searcher s = new Searcher(data,query,result);
        s.setName("Waiting searcher");
        s.start();

        s.join();
        s.printStatus();

        return result;
    }

    public static LinkedBlockingQueue<String> pipelinedSearch(BufferedReader data, String query, int num) throws InterruptedException {
        LinkedBlockingQueue<String> result = new LinkedBlockingQueue<>();
        Searcher s = new Searcher(data,query,result);
        // The result is the Printer p's input queue "in"
        Printer p = new Printer(result);

        s.setName("Pipelined searcher -- SEARCHER");
        p.setName("Pipelined searcher -- PRINTER");

        p.start();
        s.start();

        p.join();
        s.join();

        p.printStatus();
        s.printStatus();

        return result;
    }

    public static LinkedBlockingQueue<String> interruptingSearch(BufferedReader data, String query, int num) throws InterruptedException {
        LinkedBlockingQueue<String> result = new LinkedBlockingQueue<>();

        Searcher s = new Searcher(data,query,result);
        Printer p = new Printer(result,num);

        s.setName("Interrupting search -- SEARCHER");
        p.setName("Interrupting search -- PRINTER");

        s.start();
        p.start();

        p.join();
        s.interrupt();
        s.join();

        s.printStatus();
        p.printStatus();

        return result;
    }

    public static LinkedBlockingQueue<String> concurrentSearch(BufferedReader data, String query, int num) throws InterruptedException {
        LinkedBlockingQueue<String> result = new LinkedBlockingQueue<>();

        Searcher s1 = new Searcher(data,query,result);
        Searcher s2 = new Searcher(data,query,result);
        s1.setName("Concurrent search -- SEARCHER 1");
        s2.setName("Concurrent search -- SEARCHER 2");
        Printer p = new Printer(result, num);
        p.setName("Concurrent search -- PRINTER");

        s1.start();
        s2.start();
        p.start();

        p.join();
        s1.interrupt();
        s2.interrupt();
        s1.join();
        s2.join();

        s1.printStatus();
        s2.printStatus();
        p.printStatus();




        return result;
    }

    public static LinkedBlockingQueue<String> search(BufferedReader data, String query, int num){
        try {
            return concurrentSearch(data, query, num);
        } catch (InterruptedException e) {
            System.out.println("Search interrupted.");
            throw new RuntimeException("Unexpected search interruption");
        }
    }

    public static Map<String,Integer> count(BufferedReader data, String[] queries) throws InterruptedException {
        LinkedBlockingQueue<String> result = new LinkedBlockingQueue<>();

        MapFrequency map1 = new MapFrequency(data,queries,result);
        MapFrequency map2 = new MapFrequency(data,queries,result);
        ReduceFrequency red = new ReduceFrequency(result);

        map1.setName("Map-Reduce -- map1");
        map2.setName("Map-Reduce -- map2");
        red.setName("Map-reduce -- reduce");

        red.start();
        map1.start();
        map2.start();

        map1.join();
        map2.join();
        red.join();

        map1.printStatus();
        map2.printStatus();
        red.printStatus();

        return red.count;
    }
}
