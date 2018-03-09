import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TugOfWar {
    public boolean tug(int init) throws InterruptedException {
        final Lock lock = new ReentrantLock();
        final DM2AtomicInteger pos = new DM2AtomicInteger();
        pos.set(init);
        final DM2AtomicInteger obj = new DM2AtomicInteger();
        obj.set(init);
        obj.getAndDouble();

        Runnable runnert = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        lock.lock();
                        if (pos.get()<=0 ||pos.get() >= obj.get()) {
                            break;
                        } else {
                            pos.set(pos.get()+1);
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            }
        };

        Runnable runners = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        lock.lock();
                        if (pos.get()<=0 ||pos.get() >= obj.get()) {
                            break;
                        } else {
                            pos.set(pos.get()-1);
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            }
        };

        Thread t = new Thread(runnert);
        Thread s = new Thread(runners);

        t.start();
        s.start();

        t.join();
        s.join();

        return pos.get() == 0;

    }
}
