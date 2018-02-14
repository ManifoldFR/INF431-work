package data;

import nodes.Node;

import java.util.concurrent.locks.ReentrantLock;

public class LockedListQueue implements MessageQueue {
    private static class Cell {
        public Message data;
        public Cell next;

        public Cell(Message data) {
            this.data = data;
            this.next = null;
        }
    }

    private Cell head, tail;
    private int length;
    private ReentrantLock lock;

    public LockedListQueue() {
        Cell sentinel = new Cell(null);
        this.head = this.tail = sentinel;
        this.length = 0;
        lock = new ReentrantLock();
    }

    @Override
    public boolean add(Message msg) {
        this.lock.lock();
        try {
            Cell newTail = new Cell(msg);
            this.tail.next = newTail;
            this.tail = newTail;
            this.length++;
            return true;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return this.length == 0;
    }

    @Override
    public Message remove() {
        while (true) {
            lock.lock();
            Cell next = this.head.next;
            if (null == next) {
                lock.unlock();
                Node.sleepUninterruptibly(10);
                continue;
            }

            try {
                this.head = next;
                Message ret = next.data;
                next.data = null;
                this.length--;
                return ret;
            } finally {
                lock.unlock();
            }
        }
    }
}
