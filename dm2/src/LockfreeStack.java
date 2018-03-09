import java.util.concurrent.atomic.AtomicReference;

public class LockfreeStack<E> {

    public class Node<E> {
        public final E data;
        public Node<E> next = null;

        public Node(E data) { this.data = data; }
    }

    private final AtomicReference<Node<E>> stack = new AtomicReference<>();

    public LockfreeStack(E data) {
        stack.set(new Node(data));
    }

    public void push(E data) {
        // the print operation will not work in sync with the actual push... because there are no locks
        System.out.println(Thread.currentThread().getName()+" push "+data);
        Node pushNode = new Node(data);
        do {
            pushNode.next = stack.get();
        } while (!stack.compareAndSet(pushNode.next,pushNode));
    }

    public E pop() {
        Node<E> popped = stack.get();

        if (popped == null) return null;

        while (!stack.compareAndSet(popped,popped.next)) {
            popped = stack.get();
        }

        try {
            return popped.data;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public void print() {
        E value = this.pop();
        while (value != null) {
            System.out.println(value);
            value = this.pop();
        }
    }

}
