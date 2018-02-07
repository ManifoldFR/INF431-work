public class Stack<E> {
    private class Node{
        E data;
        Node next;

        Node(E el,Node next) {
            data = el;
            this.next = next;
        }
    }

    Node top;

    public Stack(E el) {
        top = new Node(el,null);
    }

    public E pop() {
        E data =  top.data;
        top = top.next;
        return data;
    }

    public void push(E el) {
        Node n = new Node(el,top);
        top = n;
    }

    public String toString() {
        Node n;
        String res = "";
        while (top != null) {
            res = res + pop() + "; ";
        }
        return res;
    }
}
