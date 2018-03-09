import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DM2AtomicInteger extends AtomicInteger {
    private final Lock lock = new ReentrantLock();

    public int getAndDouble() {
        try {
            lock.lock();
            int temp = this.get();
            this.set(2*temp);
            return temp;
        } finally {
            lock.unlock();
        }
    }

    public boolean setIfSmaller(int newVal) {
        try {
            lock.lock();
            if (this.get() <= newVal) {
                this.set(newVal);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}
