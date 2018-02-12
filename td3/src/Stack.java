public class Stack<E> {
    class Node{
        E data;
        Node next;

        Node(E el,Node next) {
            data = el;
            this.next = next;
        }
    }

    private Node top;

    public Stack(E el) {
        top = new Node(el,null);
    }

    public Stack() {
        top = null;
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

    /**
     * À ne pas utiliser avec 500000 entrées dans la stack
     * Cette méthode détruit la stack !!
     **/
    public String toString() {
        String res = "";
        while (top != null) {
            String val = this.pop().toString();
            res = res + val + "; ";
        }
        System.out.println("string done...");
        return res;
    }

    public void print() {
        while (top != null) {
            System.out.println(pop() + "; ");
        }
        System.out.println("Printing done.");
    }
}
