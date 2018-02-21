package td3a;

import java.util.concurrent.locks.ReentrantLock;

public class SafeStack<E> extends Stack<E> {

    private Integer count, lastCount;
    private boolean DEBUG = false;
    private ReentrantLock lock;
    private Node top;

    public SafeStack() {
        super();
        lock = new ReentrantLock();
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

}
