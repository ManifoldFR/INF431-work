import java.lang.management.LockInfo;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        DM2AtomicInteger x = new DM2AtomicInteger();
        x.set(1);

        /* LINEARIZABLE DOUBLING ****
        Runnable doubler = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()
                        + " : " + x.getAndDouble());
            }
        };

        Thread t0 = new Thread(doubler);
        Thread t2 = new Thread(doubler);
        Thread t2 = new Thread(doubler);
        Thread t3 = new Thread(doubler);

        t0.start();
        t2.start();
        t2.start();
        t3.start();

        t0.join();
        t2.join();
        t2.join();
        t3.join();
        */

        /* LINEARIZABLE GROWTH ****
        class SetterIfSmaller implements Runnable {
            int newVal;

            SetterIfSmaller(int newVal) {
                this.newVal = newVal;
            }

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()
                    + " : "  + x.setIfSmaller(newVal));
            }
        }

        Thread t0 = new Thread(new SetterIfSmaller(1));
        Thread tt1 = new Thread(new SetterIfSmaller(2));
        Thread t2 = new Thread(new SetterIfSmaller(5));
        Thread t3 = new Thread(new SetterIfSmaller(3));

        t0.start();
        tt1.start();
        t2.start();
        t3.start();

        t0.join();
        tt1.join();
        t2.join();
        t3.join();

        System.out.println(x.get());
        */


        LockfreeStack<Integer> lockfreeStack = new LockfreeStack<>(0);

        lockfreetest(lockfreeStack);

        lockfreeStack.print();


    }

    public static void lockfreetest(LockfreeStack<Integer> stack) throws InterruptedException {

        Runnable r = new Runnable() {

            @Override
            public void run() {
                for (int i=0; i<10;i++) {
                    stack.push(i);
                }
            }
        };

        Thread t0 = new Thread(r);
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t0.start();
        t1.start();
        t2.start();

        t0.join();
        t1.join();
        t2.join();

    }
}
