package td3a;

import java.util.concurrent.atomic.AtomicBoolean;

public class VerySimpleLock {
    AtomicBoolean locked = new AtomicBoolean(false);

    public void lock() {
        while (!locked.compareAndSet(false,true)) {; }
    }

    public void unlock() {
        locked.getAndSet(false);
    }
}
