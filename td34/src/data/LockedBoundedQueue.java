package data;

import nodes.Node;

import java.util.concurrent.locks.ReentrantLock;

public class LockedBoundedQueue implements MessageQueue {

    private final Message[] queue;
    private final int bound;
    private final ReentrantLock lock;
    private int in, size;

    public LockedBoundedQueue(int max) {
        this.queue = new Message[max];
        this.bound = max;
        this.in = 0;
        this.size = 0;
        lock = new ReentrantLock();
    }

    public boolean isFull() {
        return this.size >= this.bound;
    }

    @Override
    public boolean add(Message msg) {
        lock.lock();
        /*
        The add operation increases the buffer size and modifies the queue structure
         */
        try {
            if (this.isFull()) return false;
            this.queue[this.in] = msg;
            this.in = (this.in + 1) % this.bound;
            ++this.size;
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Message remove() {
        while (true) {
            if (!this.isEmpty()) break;
            Node.sleepUninterruptibly(10);
        }
        /*
        Adding a message modifies the message queue and increases the buffer size
        So the computation of the output index can be messed up (so we remove the wrong message), OR the buffer threshold can be exceeded by a concurrent incoming add operation from another thread
        Thus we must lock the instance to prevent the in, bound, size and queue fields from being modified when doing the remove & return operation
         */
        lock.lock();
        int out = (this.in + this.bound - this.size) % this.bound;
        try {
            Message thing = this.queue[out];
            this.queue[out] = null;
            --this.size;
            return thing;
        } finally {
            lock.unlock();
        }
    }

}
