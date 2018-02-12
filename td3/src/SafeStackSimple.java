import java.util.concurrent.locks.Lock;

public class SafeStackSimple<E> extends Stack<E> {

    private Node top;
    private boolean DEBUG = false;
    private int count, lastCount;
    private VerySimpleLock lock;

    public SafeStackSimple(E el) {
        super(el);
        lock = new VerySimpleLock();
    }

    public SafeStackSimple() {
        super();
        count = 0;
        lastCount = 0;
        lock = new VerySimpleLock();
    }

    public void enableDebug() {
        this.DEBUG = true;
    }

    @Override
    public void push(E el) {
        lock.lock();
        try {
            top = new Node(el, top);
            count++;
            if (DEBUG && (count-lastCount >=1000)) {
                lastCount = count;
                System.out.println("Current # of els: " +count);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E pop() {
        lock.lock();
        Node n;
        try {
            n = top;
            top = n.next;
            count--;
        } finally {
            lock.unlock();
        }
        return n.data;
    }

    @Override
    public void print() {
        while (top != null) {
            System.out.print(pop()+ "; ");
        }
        System.out.println("\nPrinting done.");
    }

}
