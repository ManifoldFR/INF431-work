package data;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingBoundedQueue implements MessageQueue {

    private final Message[] queue;
    private final int bound;
    private int in, size;

    // This queue's protecting lock...
    private final Lock lock = new ReentrantLock();
    // and its two condition variables to help it wait to become nonempty/nonfull
    private final Condition empty = lock.newCondition();
    private final Condition full = lock.newCondition();

    public BlockingBoundedQueue(int max) {
        this.queue = new Message[max];
        this.bound = max;
        this.in = 0;
        this.size = 0;
    }

    public boolean isFull() {
        return this.size >= this.bound;
    }

    @Override
    public boolean add(Message msg) {
        lock.lock();
        while (this.isFull()) {
            full.awaitUninterruptibly();
        }
        try {
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
        lock.lock();
        while (this.isEmpty()) {
            empty.awaitUninterruptibly();
        }
        try {
            int out = (this.in + this.bound - this.size) % this.bound;
            Message thing = this.queue[out];
            this.queue[out] = null;
            --this.size;
            return thing;
        } finally { lock.unlock(); }
    }

}
